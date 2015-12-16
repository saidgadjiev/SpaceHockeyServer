package gameMechanics;

import gameMechanics.game.*;
import main.gameService.GamePosition;
import main.gameService.GameResultState;
import main.gameService.Player;

import java.util.Date;

/**
 * Created by said on 20.10.15.
 */

public class GameSession {
    private int sessionStep = 0;
    private final long startTime;
    private final Player firstPlayer;
    private final Player secondPlayer;
    private State sessionState = State.PLAY;
    private GameResultState resultState = GameResultState.DEAD_HEAT;
    private GameField gameField = new GameField(500, 630);
    private Ball ball = new Ball(new Position(250, 310), 10, 2, 2);

    private enum State {PLAY, FINISH}

    public GameSession(Player player1, Player player2) {
        startTime = new Date().getTime();
        player1.setPlatform(new Platform(new Position(236, 80), 100, 20, Direction.STOP, 4));
        player2.setPlatform(new Platform(new Position(236, 610), 100, 20, Direction.STOP, 4));

        this.firstPlayer = player1;
        this.secondPlayer = player2;
    }

    public Player getEnemyPlayer(GamePosition myPos) {
        if (myPos == GamePosition.UPPER) {
            return secondPlayer;
        } else {
            return firstPlayer;
        }
    }

    public void sessionStep() {
        sessionStep++;
    }

    public int getSessionStep() {
        return sessionStep;
    }

    public void setStepCountZero() {
        sessionStep = 0;
    }

    public long getSessionTime() {
        return new Date().getTime() - startTime;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void determineWinner() {
        if (firstPlayer.getScore() > secondPlayer.getScore()) {
            resultState = GameResultState.FIRST_WIN;
        } else if (firstPlayer.getScore() < secondPlayer.getScore()) {
            resultState = GameResultState.SECOND_WIN;
        }

        sessionState = State.FINISH;
    }

    public GameResultState getResultState() {
        return resultState;
    }

    public Ball getBall() {
        return ball;
    }

    public boolean isFinished() {
        return sessionState == State.FINISH;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public GameField getGameField() {
        return gameField;
    }
}
