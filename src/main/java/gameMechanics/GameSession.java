package gameMechanics;

import gameMechanics.game.*;
import main.gameService.Player;

import java.util.Date;

/**
 * Created by said on 20.10.15.
 */

public class GameSession {
    private final long startTime;
    private final Player first;
    private final Player second;
    private State sessionState = State.PLAY;
    private Ball ball = new Ball(new Position(100, 100), 5, 5);
    private GameField gameField = new GameField();
    private int gameState = 0;
    private enum State { PLAY, FINISH }

    public GameSession(Player player1, Player player2) {
        startTime = new Date().getTime();
        player1.setPlatform(new Platform(new Position(235, 80), Direction.STOP, 4));
        player2.setPlatform(new Platform(new Position(235, 610), Direction.STOP, 4));

        this.first = player1;
        this.second = player2;
    }

    public Player getEnemyPlayer(int myPos) {
        if (myPos == 1) {
            return second;
        } else {
            return first;
        }
    }

    public Player getSelfPlayer(int myPos) {
        if (myPos == 1) {
            return first;
        } else {
            return second;
        }
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public Player getFirstPlayer() {
        return first;
    }

    public void determineWinner() {
    /*    if (first.getMyScore() > second.getMyScore()) {
            first.setGameState(1);
            second.setGameState(2);
        }  else if (first.getMyScore() < second.getMyScore()) {
            first.setGameState(2);
            second.setGameState(1);
        } else {
            first.setGameState(0);
            second.setGameState(0);
        }
        sessionState = State.FINISH;*/
    }

    public boolean isCollisionWithWall(Player player) {
        int x = player.getPlatform().getPosition().getX();

        if (x <= gameField.getPosition().getX() && player.getPlatform().getDirection() == Direction.LEFT) {
            return true;
        }
        if (x >= gameField.getPosition().getX() + gameField.getWidth() - 100
                && player.getPlatform().getDirection() == Direction.RIGHT) {
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return sessionState == State.FINISH;
    }

    public Player getSecondPlayer() {
        return second;
    }
}
