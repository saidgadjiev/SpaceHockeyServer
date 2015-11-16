package main.gameService;

import gameMechanics.game.Ball;
import gameMechanics.game.Platform;
import gameMechanics.game.Position;

/**
 * Created by said on 20.10.15.
 */

public class Game {
    private Player myPlayer;
    private Player enemyPlayer;
    private Ball ball = new Ball(new Position(100, 100), 5, 5);
    private int gameState = 0;

    public Game(Player player) {
        this.myPlayer = player;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public Player getEnemyPlayer() {
        return enemyPlayer;
    }

    public String getMyPlayerName() {
        return myPlayer.getName();
    }

    public Ball getBall() {
        return ball;
    }

    public String getEnemyPlayerName() {
        return enemyPlayer.getName();
    }

    public void setEnemyPlayer(Player enemyPlayer) {
        this.enemyPlayer = enemyPlayer;
    }

    public int getGameState() {
        return gameState;
    }


    public int getMyScore() {
        return myPlayer.getScore();
    }

    public int getEnemyScore() {
        return enemyPlayer.getScore();
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public Platform getMyPlatform() {
        return myPlayer.getPlatform();
    }

    public Platform getEnemyPlatform() {
        return enemyPlayer.getPlatform();
    }

    public void moveMyPlatform() {
        myPlayer.movePlatform();
    }

    public void moveEnemyPlatform() {
        enemyPlayer.movePlatform();
    }

    public void incrementMyScore() {
        myPlayer.incrementScore();
    }

    public void incrementEnemyScore() {
        enemyPlayer.incrementScore();
    }
}
