package gameMechanics;

import com.google.gson.JsonObject;
import gameMechanics.game.Direction;
import main.gameService.GameMechanics;
import main.gameService.Game;
import main.gameService.Player;
import main.gameService.WebSocketService;
import resource.GameMechanicsSettings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by said on 20.10.15.
 */

public class GameMechanicsImpl implements GameMechanics {
    private final int stepTime;

    private final int gameTime;

    private WebSocketService webSocketService;

    private Map<Player, GameSession> playerToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private Player waiter;

    public GameMechanicsImpl(WebSocketService webSocketService, GameMechanicsSettings gameMechanicsSettings) {
        this.webSocketService = webSocketService;
        this.stepTime = gameMechanicsSettings.getStepTime();
        this.gameTime = gameMechanicsSettings.getGameTime();
    }

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
        this.stepTime = 100;
        this.gameTime = 10000;
    }

    public void addPlayer(Player player) {
        if (waiter != null) {
            starGame(player);
            waiter = null;
        } else {
            waiter = player;
        }
    }

    public void incrementScore(Player myPlayer) {
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
        Game myGame = myGameSession.getSelfPlayer(myPlayer);
        Game enemyGame = myGameSession.getEnemyPlayer(myPlayer);
        if (message.get("status").getAsString().equals("movePlatform")) {
            myGame.getMyPlatform().setDirection(getDirectionFromMessage(message));
            myGame.moveMyPlatform();
            enemyGame.getEnemyPlatform().setDirection(getDirectionFromMessage(message));
            enemyGame.moveEnemyPlatform();
            webSocketService.notifyMyPlatformNewDirection(myGame);
            webSocketService.notifyEnemyPlatformNewDirection(enemyGame);
        }
        if (message.get("status").getAsString().equals("stopPlatform")) {
            myGame.getMyPlatform().setDirection(Direction.STOP);
            enemyGame.getEnemyPlatform().setDirection(Direction.STOP);
            webSocketService.notifyMyPlatformNewDirection(myGame);
            webSocketService.notifyEnemyPlatformNewDirection(enemyGame);
        }
        if (message.get("status").getAsString().equals("moveBall")) {
            myGame.getBall().setVelocityX(message.get("velocityX").getAsInt());
            myGame.getBall().setVelocityY(message.get("velocityY").getAsInt());
            enemyGame.getBall().setVelocityX(message.get("velocityX").getAsInt());
            enemyGame.getBall().setVelocityY(message.get("velocityY").getAsInt());
            webSocketService.notifyMyBallNewMootion(myGame);
            webSocketService.notifyEnemyBallNewMotion(enemyGame);
        }
    }

    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
          //  gmStep();
           // TimeHelper.sleep(stepTime);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (!session.isFinished()) {
                if (session.getSessionTime() > gameTime) {
                    session.determineWinner();
                    webSocketService.notifyGameOver(session.getFirstPlayer());
                    webSocketService.notifyGameOver(session.getSecond());
                }
            }
        }
    }

    private void starGame(Player firstPlayer) {
        Player secondPlayer = waiter;
        GameSession gameSession = new GameSession(firstPlayer, secondPlayer);
        allSessions.add(gameSession);
        playerToGame.put(firstPlayer, gameSession);
        playerToGame.put(secondPlayer, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelfPlayer(firstPlayer));
        webSocketService.notifyStartGame(gameSession.getSelfPlayer(secondPlayer));
    }
}
