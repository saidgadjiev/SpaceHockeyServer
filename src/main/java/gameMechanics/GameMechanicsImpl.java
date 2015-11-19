package gameMechanics;

import com.google.gson.JsonObject;
import gameMechanics.game.Direction;
import main.TimeHelper;
import main.gameService.GameMechanics;
import main.gameService.Player;
import main.gameService.WebSocketService;
import resource.GameMechanicsSettings;

import java.util.*;

/**
  Created by said on 20.10.15.
 */

@SuppressWarnings("SpellCheckingInspection")
public class GameMechanicsImpl implements GameMechanics {
    private final int stepTime;
    private final int gameTime;
    private WebSocketService webSocketService;
    private Map<Player, GameSession> playerToGame = new HashMap<>();
    private List<GameSession> allSessions = new LinkedList<>();
    private Queue<Player> waiters = new LinkedList<>();

    public GameMechanicsImpl(WebSocketService webSocketService, GameMechanicsSettings gameMechanicsSettings) {
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
        GameSession myGameSession = playerToGame.get(myPlayer);
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
            TimeHelper.sleep(stepTime);
        }
    }

    public void createGame() {
        while (waiters.size() > 1) {
            Player first = waiters.poll();
            Player second = waiters.poll();

            first.setMyPosition(1);
            second.setMyPosition(2);

            starGame(first, second);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (!session.isFinished()) {
                makeStep();
                if (session.getSessionTime() > gameTime) {
                    session.determineWinner();
                    webSocketService.notifyGameOver(session, session.getFirstPlayer());
                    webSocketService.notifyGameOver(session, session.getSecondPlayer());
                }
            }
        }
    }

    private void makeStep() {
        for (Player player: playerToGame.keySet()) {
            GameSession session = playerToGame.get(player);
            if (!session.isCollisionWithWall(player)) {
                player.getPlatform().move();
            } else {
                player.getPlatform().setDirection(Direction.STOP);
                syncPlatformDirection(session, player, session.getEnemyPlayer(player.getMyPosition()));
            }
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
