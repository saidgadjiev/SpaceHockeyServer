package gameMechanics.game;

/**
 * Created by said on 28.11.15.
 */
public class Ball {
    private int velocityX;
    private int velocityY;
    private int radius;
    private Position position;

    public Ball(Position position, int radius, int velocityX, int velocityY) {
        this.position = position;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
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
        position.setX(position.getX() + velocityX);
        position.setY(position.getY() + velocityY);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
