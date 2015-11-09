package main.gameService;

import gameMechanics.game.Direction;
import gameMechanics.game.Platform;
import gameMechanics.game.Position;

/**
 * Created by said on 20.10.15.
 */
public class GameUser {
    private final String myName;
    private int myID = 1;
    private int enemyID = 2;
    private String enemyName;
    private int gameState = 0;
    private int myScore =  0;
    private int enemyScore = 0;
    private Platform myPlatform = new Platform(new Position(235, 80), Direction.STOP, 4);
    private Platform enemyPlatform = new Platform(new Position(235, 610), Direction.STOP, 4);

    public GameUser(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getEnemyScore() {
        return enemyScore;
    }

    public Platform getMyPlatform() {
        return myPlatform;
    }

    public Platform getEnemyPlatform() {
        return enemyPlatform;
    }

    public void incrementMyScore() {
        myScore++;
    }

    public void moveMyPlatform() {
        myPlatform.move();
    }

    public void moveEnemyPlatform() {
        enemyPlatform.move();
    }

    public int getMyID() {
        return myID;
    }

    public int getEnemyID() {
        return enemyID;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
}
