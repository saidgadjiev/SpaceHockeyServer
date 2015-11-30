package gameMechanics.game;

/**
 * Created by said on 14.11.15.
 */

public interface Sprite {

    Direction getDirection();

    void setDirection(Direction direction);

    int getVelocity();

    void setVelocity(int velocity);

    Position getPosition();

    void setPosition(Position position);

    void move();
}
