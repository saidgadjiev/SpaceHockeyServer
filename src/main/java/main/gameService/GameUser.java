package main.gameService;

import utils.Position;

/**
 * Created by said on 20.10.15.
 */
public class GameUser {
    private final String myName;
    private Platform myPlatform = new Platform(new Position(100, 100), 2);
    private String enemyName;
<<<<<<< HEAD
    private int gameState = 0;
    private int myScore =  0;
    private int enemyScore = 0;
=======
    private Platform enemyPlatform = new Platform(new Position(200, 100), 2);
>>>>>>> 08d6801e480158450253391968b78243d28d706c

    public GameUser(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String name) {
        this.enemyName = name;
    }

    public Position getMyPlatformPosition() {
        return myPlatform.getPosition();
    }

    public Position getEnemyPlatformPosition() {
        return enemyPlatform.getPosition();
    }

    public void setMyPlatformPosition(int x, int y) {
        myPlatform.setPosition(x, y);
    }

    public void setMyPlatformPosition(Position position) {
        myPlatform.setPosition(position);
    }

    public void setEnemyPlatformPosition(int x, int y) {
        enemyPlatform.setPosition(x, y);
    }

    public void setEnemyPlatformPosition(Position position) {
        enemyPlatform.setPosition(position);
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }
}
