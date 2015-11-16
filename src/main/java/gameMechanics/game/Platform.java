package gameMechanics.game;

public class Platform implements Sprite {
    private Position position;
    private Direction direction;
    private int velocity;

    public Platform(Position position, Direction direction, int velocity) {
        this.position = position;
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

    public void move() {
        switch (direction) {
            case LEFT:
                position.setX(position.getX() - velocity);
                break;
            case RIGHT:
                position.setX(position.getX() + velocity);
                break;
            default:
                break;
        }
    }
}
