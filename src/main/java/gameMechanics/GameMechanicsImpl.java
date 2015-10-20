package gameMechanics;

import main.TimeHelper;
import main.gameService.GameMechanics;
import main.gameService.GameUser;
import main.gameService.WebSocketService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by said on 20.10.15.
 */

public class GameMechanicsImpl implements GameMechanics {
    private static final int STEP_TIME = 100;

    private static final int gameTime = 60 * 1000;

    private WebSocketService webSocketService;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private String waiter;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void addUser(String user) {
        if (waiter != null) {
            starGame(user);
            waiter = null;
            System.out.print("addUserIf\n");
        } else {
            System.out.print("addUserElse\n");
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
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > gameTime) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
            }
        }
    }

    private void starGame(String first) {
        System.out.print("StartGame\n");
        String second = waiter;
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));
    }
}
