package gameMechanics;

import main.gameService.GameUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by said on 20.10.15.
 */

public class GameSession {
    private final long startTime;
    private final GameUser first;
    private final GameUser second;
    private State sessionState = State.PLAY;
    private enum State { PLAY, FINISH }

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(String user1, String user2) {
        startTime = new Date().getTime();
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setEnemyName(user2);

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setEnemyName(user1);

        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;
    }

    public GameUser getEnemy(String user) {
        String enemyName = users.get(user).getEnemyName();
        return users.get(enemyName);
    }

    public GameUser getSelf(String user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getFirst() {
        return first;
    }

    public void determineWinner() {
        if (first.getMyScore() > second.getMyScore()) {
            first.setGameState(1);
            second.setGameState(2);
        }  else if (first.getMyScore() < second.getMyScore()) {
            first.setGameState(2);
            second.setGameState(1);
        } else {
            first.setGameState(0);
            second.setGameState(0);
        }
        sessionState = State.FINISH;
    }

    public boolean isFinished() {
        return sessionState == State.FINISH;
    }

    public GameUser getSecond() {
        return second;
    }
}
