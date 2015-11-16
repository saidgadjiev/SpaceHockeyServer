package gameMechanics.game;

/**
 * Created by said on 07.11.15.
 */
public class Ball implements Sprite {
    private int velocityX;
    private int velocityY;
    private Position position;

    public Ball(Position position, int velocityX, int velocityY) {
        this.position = position;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public Direction getDirection() {
        return Direction.STOP;
    }

    public void setDirection(Direction direction) {

    }

    public int getVelocity() {
        return 0;
    }

    public void setVelocity(int velocity) {

    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move() {

    }
}
