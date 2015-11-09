package gameMechanics;

import com.google.gson.JsonObject;
import gameMechanics.game.Direction;
import main.gameService.GameMechanics;
import main.gameService.GameUser;
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

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

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

    @Override
    public void addUser(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    @Override
    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        myUser.incrementMyScore();
        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }

    @Override
    public void movePlatfom(String userName, JsonObject message) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        myUser.getMyPlatform().setDirection(analizeMessage(message));
        myUser.moveMyPlatform();
        GameUser enemyUser = myGameSession.getEnemy(userName);
        enemyUser.getEnemyPlatform().setDirection(analizeMessage(message));
        enemyUser.moveEnemyPlatform();
        webSocketService.notifyMyPlatformNewPosition(myUser);
        webSocketService.notifyEnemyPlatformNewPosition(enemyUser);
    }

    public Direction analizeMessage(JsonObject message) {
        switch (message.get("direction").getAsString()) {
            case "LEFT":
                return Direction.LEFT;
            case "RIGHT":
                return Direction.RIGHT;
            default:
                return Direction.STOP;
        }
    }

    @Override
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
                    webSocketService.notifyGameOver(session.getFirst());
                    webSocketService.notifyGameOver(session.getSecond());
                }
            }
        }
    }

    private void starGame(String first) {
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}
