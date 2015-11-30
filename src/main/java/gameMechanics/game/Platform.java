package gameMechanics.game;

/**
 * Created by said on 18.11.15.
 */

public class Platform implements Sprite {
    private Position position;
    private Direction direction;
    private int velocity;
    private int width;
    private int height;

    public Platform(Position position, int width, int height, Direction direction, int velocity) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.velocity = velocity;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void move() {
        switch (direction) {
            case LEFT:
                position.setX(position.getX() - velocity);
                break;
            case RIGHT:
                position.setX(position.getX() + velocity);
                break;
            case STOP:
                break;
            default:
                break;
        }
    }
}
