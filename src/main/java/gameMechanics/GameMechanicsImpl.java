package gameMechanics;

import com.google.gson.JsonObject;
import gameMechanics.game.Direction;
import main.TimeHelper;
import main.accountService.AccountService;
import main.gameService.GameMechanics;
import main.gameService.GamePosition;
import main.gameService.Player;
import main.gameService.WebSocketService;
import main.user.UserProfile;
import org.jetbrains.annotations.TestOnly;
import resource.GameMechanicsSettings;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
  Created by said on 20.10.15.
 */

@SuppressWarnings("SpellCheckingInspection")
public class GameMechanicsImpl implements GameMechanics {
    private final int stepTime;
    private final int gameTime;
    private AccountService accountService;
    private WebSocketService webSocketService;
    private Map<String, GameSession> playerToGame = new HashMap<>();
    private List<Player> allPlayers = new LinkedList<>();
    private List<GameSession> allSessions = new LinkedList<>();
    private ConcurrentLinkedQueue<Player> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(AccountService accountService, WebSocketService webSocketService, GameMechanicsSettings gameMechanicsSettings) {
        this.accountService = accountService;
        this.webSocketService = webSocketService;
        this.stepTime = gameMechanicsSettings.getStepTime() / 60;
        this.gameTime = gameMechanicsSettings.getGameTime();
    }

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
        this.stepTime = 1000 / 60;
        this.gameTime = 10000;
    }

    public void addPlayer(Player player) {
        waiters.add(player);
    }

    public void incrementScore(Player myPlayer) {
        GameSession myGameSession = playerToGame.get(myPlayer.getName());
        myPlayer.incrementScore();
        Player enemyPlayer = myGameSession.getEnemyPlayer(myPlayer.getMyPosition());
        webSocketService.notifySyncScore(myGameSession, myPlayer);
        webSocketService.notifySyncScore(myGameSession, enemyPlayer);
    }

    private void syncPlatformDirection(GameSession session, Player myPlayer, Player enemyPlayer) {
        webSocketService.notifySyncPlatformDirection(session, myPlayer);
        webSocketService.notifySyncPlatformDirection(session, enemyPlayer);
    }

    private Direction getDirectionFromMessage(JsonObject message) {
        switch (message.get("direction").getAsString()) {
            case "LEFT":
                return Direction.LEFT;
            case "RIGHT":
                return Direction.RIGHT;
            default:
                return Direction.STOP;
        }
    }

    public void analizeMessage(Player myPlayer, JsonObject message) {
        GameSession myGameSession = playerToGame.get(myPlayer.getName());
        Player enemyPlayer = myGameSession.getEnemyPlayer(myPlayer.getMyPosition());
        if (message.get("status").getAsString().equals("movePlatform")) {
            myPlayer.getPlatform().setDirection(getDirectionFromMessage(message));
            if (myGameSession.isCollisionWithWall(myPlayer)) {
                myPlayer.getPlatform().setDirection(Direction.STOP);
            }
            syncPlatformDirection(myGameSession, myPlayer, enemyPlayer);
        }
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            createGame();
            gmStep();
            makeStep();
            TimeHelper.sleep(stepTime);
        }
    }

    public void createGame() {
        while (waiters.size() > 1) {
            Player first = waiters.poll();
            Player second = waiters.poll();

            final GamePosition myPosition = GamePosition.UPPER;
            final GamePosition enemyPosition = GamePosition.LOWER;
            first.setMyPosition(myPosition);
            second.setMyPosition(enemyPosition);

            starGame(first, second);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (!session.isFinished()) {
                if (session.getSessionTime() > gameTime) {
                    finishGame(session);
                }
            }
        }
    }

    private void makeStep() {
        for (Player player: allPlayers) {
            GameSession session = playerToGame.get(player.getName());
            if (!session.isFinished()) {
                if (!session.isCollisionWithWall(player)) {
                    player.getPlatform().move();
                } else {
                    player.getPlatform().setDirection(Direction.STOP);
                    syncPlatformDirection(session, player, session.getEnemyPlayer(player.getMyPosition()));
                }
            }
        }
    }

    private void finishGame(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        session.determineWinner();
        webSocketService.notifyGameOver(session, firstPlayer);
        webSocketService.notifyGameOver(session, secondPlayer);
        playerToGame.remove(firstPlayer.getName());
        playerToGame.remove(secondPlayer.getName());
        allSessions.remove(session);
        allPlayers.remove(firstPlayer);
        allPlayers.remove(secondPlayer);
        webSocketService.removeWebSocket(firstPlayer);
        webSocketService.removeWebSocket(secondPlayer);

        switch (session.getResultState()) {
            case FIRST_WIN:
                UserProfile firstProfile = accountService.getUser(firstPlayer.getName());
                firstProfile.incrementScore();
                accountService.updateUser(firstProfile);
                break;
            case SECOND_WIN:
                UserProfile secondProfile = accountService.getUser(secondPlayer.getName());
                secondProfile.incrementScore();
                accountService.updateUser(secondProfile);
                break;
            default:
                break;
        }
    }

    @TestOnly
    public List<GameSession> getAllSessions() {
        return allSessions;
    }

    private void starGame(Player firstPlayer, Player secondPlayer) {
        GameSession gameSession = new GameSession(firstPlayer, secondPlayer);
        allSessions.add(gameSession);
        playerToGame.put(firstPlayer.getName(), gameSession);
        playerToGame.put(secondPlayer.getName(), gameSession);
        allPlayers.add(firstPlayer);
        allPlayers.add(secondPlayer);

        webSocketService.notifyStartGame(gameSession, firstPlayer);
        webSocketService.notifyStartGame(gameSession, secondPlayer);
    }
}
