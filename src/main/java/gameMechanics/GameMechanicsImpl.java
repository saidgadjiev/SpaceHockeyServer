package gameMechanics;

import main.gameService.GameMechanics;
import main.gameService.GameUser;
import main.gameService.WebSocketService;
<<<<<<< HEAD
import resource.GameMechanicsSettings;
=======
import utils.Position;
>>>>>>> 08d6801e480158450253391968b78243d28d706c

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

    /*@Override
    public void incrementScore(String userName) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        //myUser.incrementMyScore();
        GameUser enemyUser = myGameSession.getEnemy(userName);
        //enemyUser.incrementEnemyScore();
        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }*/

    @Override
    public void setNewPlatformPosition(String username, Position position) {
        GameSession myGameSession = nameToGame.get(username);
        GameUser myUser = myGameSession.getSelf(username);
        myUser.setMyPlatformPosition(position);
        GameUser enemyUser = myGameSession.getEnemy(username);
        enemyUser.setEnemyPlatformPosition(position);
        webSocketService.notifyNewMyPlatformPosition(myUser);
        webSocketService.notifyNewEnemyPlatformPosition(enemyUser);
    }

    @Override
    public void test(String username) {
        GameSession myGameSession = nameToGame.get(username);
        GameUser myUser = myGameSession.getSelf(username);
        GameUser enemyUser = myGameSession.getEnemy(username);
        webSocketService.notifyNewMyPlatformPosition(myUser);
        webSocketService.notifyNewEnemyPlatformPosition(enemyUser);
    }

    @Override
    public void run() {
<<<<<<< HEAD
        //noinspection InfiniteLoopStatement
        while (true) {
            gmStep();
            TimeHelper.sleep(stepTime);
        }
=======
        //while (true) {
        //    gmStep();
        //    TimeHelper.sleep(STEP_TIME);
        //}
>>>>>>> 08d6801e480158450253391968b78243d28d706c
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
