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
    private Ball ball = new Ball(new Position(100, 100), 10, 5, 5);

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

    public void makeStep() {
        ball.move();
        collisionDetectionBall(ball);
        if (!isCollisionWithWall(firstPlayer)) {
            firstPlayer.getPlatform().move();
        }
        if (!isCollisionWithWall(secondPlayer)) {
            secondPlayer.getPlatform().move();
        }
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

    public boolean isCollisionWithWall(Player player) {
        int x = player.getPlatform().getPosition().getX();

        //noinspection RedundantIfStatement
        if ((x <= gameField.getPosition().getX() && player.getPlatform().getDirection() == Direction.LEFT)
                || (x >= gameField.getPosition().getX() + gameField.getWidth() - 100
                && player.getPlatform().getDirection() == Direction.RIGHT)) {
            return true;
        }
        return false;
    }

    public void collisionDetectionBall(Ball ball) {
        int ballX = ball.getPosition().getX();
        int ballY = ball.getPosition().getY();

        if (ballX + ball.getRadius() >= gameField.getPosition().getX() + gameField.getWidth()) {
            ball.setVelocityX(-ball.getVelocityX());
            System.out.println("ballX" + ballX);
            System.out.println("ballY" + ballY);
        }
        if (ballX - ball.getRadius() <= gameField.getPosition().getX()) {
            ball.setVelocityX(-ball.getVelocityX());
            System.out.println("ballX" + ballX);
            System.out.println("ballY" + ballY);
        }
        if (ballY + ball.getRadius() >= gameField.getPosition().getY() + gameField.getHeight()) {
            ball.setVelocityY(-ball.getVelocityY());
            System.out.println("ballX" + ballX);
            System.out.println("ballY" + ballY);
        }
        if (ballY - ball.getRadius() <= gameField.getPosition().getY()) {
            ball.setVelocityY(-ball.getVelocityY());
            System.out.println("ballX" + ballX);
            System.out.println("ballY" + ballY);
        }
        if (ballX >= secondPlayer.getPlatform().getPosition().getX() &&
                ballX <= secondPlayer.getPlatform().getPosition().getX() + secondPlayer.getPlatform().getWidth() &&
                ballY + ball.getRadius() >= secondPlayer.getPlatform().getPosition().getY()) {
            /*if (secondPlayer.getPlatform().getDirection() != Direction.STOP)
                ball.setVelocityY(-(ball.getVelocityY() - secondPlayer.getPlatform().getVelocity() / 2));
            else*/
                ball.setVelocityY(-ball.getVelocityY());
        }
        if (ballX >= firstPlayer.getPlatform().getPosition().getX() &&
                ballX <= firstPlayer.getPlatform().getPosition().getX() + firstPlayer.getPlatform().getWidth() &&
                ballY - ball.getRadius() <= firstPlayer.getPlatform().getPosition().getY() +
                        firstPlayer.getPlatform().getHeight()) {
            /*if (firstPlayer.getPlatform().getDirection() != Direction.STOP)
                ball.setVelocityY(-(ball.getVelocityY() - firstPlayer.getPlatform().getVelocity() / 2));
            else*/
                ball.setVelocityY(-ball.getVelocityY());
        }
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
}
