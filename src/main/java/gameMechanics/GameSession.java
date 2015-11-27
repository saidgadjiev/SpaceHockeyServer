package gameMechanics;

import gameMechanics.game.*;
import main.gameService.GamePosition;
import main.gameService.GameResultState;
import main.gameService.Player;

import java.util.Date;

/**
  Created by said on 20.10.15.
 */

public class GameSession {
    private int sessionStep = 0;
    private final long startTime;
    private final Player first;
    private final Player second;
    private State sessionState = State.PLAY;
    private GameResultState resultState = GameResultState.DEAD_HEAT;
    private GameField gameField = new GameField(500, 630);
    private enum State { PLAY, FINISH }

    public GameSession(Player player1, Player player2) {
        startTime = new Date().getTime();
        player1.setPlatform(new Platform(new Position(236, 80), 100, 20, Direction.STOP, 4));
        player2.setPlatform(new Platform(new Position(236, 610), 100, 20, Direction.STOP, 4));

        this.first = player1;
        this.second = player2;
    }

    public Player getEnemyPlayer(GamePosition myPos) {
        if (myPos == GamePosition.UPPER) {
            return second;
        } else {
            return first;
        }
    }

    public void makeStep() {
        sessionStep++;
    }

    public int getSessionStep() {
        return sessionStep;
    }

    public void setStepCountZero() {
        sessionStep = 0;
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public Player getFirstPlayer() {
        return first;
    }

    public void determineWinner() {
        if (first.getScore() > second.getScore()) {
            resultState = GameResultState.FIRST_WIN;
        }  else if (first.getScore() < second.getScore()) {
            resultState = GameResultState.SECOND_WIN;
        }

        sessionState = State.FINISH;
    }

    public GameResultState getResultState() {
        return resultState;
    }

    public boolean isCollisionWithWall(Player player) {
        int x = player.getPlatform().getPosition().getX();

        //noinspection RedundantIfStatement
        if ((x <= gameField.getPosition().getX() && player.getPlatform().getDirection() == Direction.LEFT)
                ||(x >= gameField.getPosition().getX() + gameField.getWidth() - 100
                && player.getPlatform().getDirection() == Direction.RIGHT)) {
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
