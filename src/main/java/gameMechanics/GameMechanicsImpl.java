package gameMechanics;

import com.google.gson.JsonObject;
import gameMechanics.game.Direction;
import main.accountService.AccountService;
import main.gameService.GameMechanics;
import main.gameService.GamePosition;
import main.gameService.Player;
import main.gameService.WebSocketService;
import main.user.UserProfile;
import resource.GameMechanicsSettings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    private Map<Player, GameSession> playerToGame = new HashMap<>();
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
        GameSession myGameSession = playerToGame.get(myPlayer);
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
        if (message.get("status").getAsString().equals("movePlatform")) {
            myPlayer.getPlatform().setDirection(getDirectionFromMessage(message));
        }
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        int fpsUpdate = 60;
        double timePerTickUpdate = 1000000000 / fpsUpdate;
        double deltaUpdate = 0;

        int fpsSync = 20;
        double timePerTickSync = 1000000000 / fpsSync;
        double deltaSync = 0;
        long now;
        long lastTime = System.nanoTime();

        while (true) {
            now = System.nanoTime();
            deltaUpdate += (now - lastTime) / timePerTickUpdate;
            deltaSync += (now - lastTime) / timePerTickSync;
            lastTime = now;
            createGame();
            if (deltaUpdate >= 1) {
                makeStep();
                gmStep();
                deltaUpdate--;
            }
            //if (deltaSync >= 1) {
            //    deltaSync--;
            //}
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
                session.makeStep();
                if (session.getSessionStep() == 2) {
                    webSocketService.syncGameWorld(session, session.getFirstPlayer());
                    webSocketService.syncGameWorld(session, session.getSecondPlayer());
                    session.setStepCountZero();
                }
                //if (session.getSessionTime() > gameTime) {
                //    finishGame(session);
                //}
            }
        }
    }

    private void makeStep() {
        for (GameSession session : allSessions) {
            Player firstPlayer = session.getFirstPlayer();
            Player secondPlayer = session.getSecondPlayer();
            if (!session.isFinished()) {
                if (!session.isCollisionWithWall(firstPlayer)) {
                    firstPlayer.getPlatform().move();
                }
                if (!session.isCollisionWithWall(secondPlayer)) {
                    secondPlayer.getPlatform().move();
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
        playerToGame.remove(firstPlayer);
        playerToGame.remove(secondPlayer);
        allSessions.remove(session);
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

    public List<GameSession> getAllSessions() {
        return allSessions;
    }

    private void starGame(Player firstPlayer, Player secondPlayer) {
        GameSession gameSession = new GameSession(firstPlayer, secondPlayer);
        allSessions.add(gameSession);
        playerToGame.put(firstPlayer, gameSession);
        playerToGame.put(secondPlayer, gameSession);

        webSocketService.notifyStartGame(gameSession, firstPlayer);
        webSocketService.notifyStartGame(gameSession, secondPlayer);
    }
}
