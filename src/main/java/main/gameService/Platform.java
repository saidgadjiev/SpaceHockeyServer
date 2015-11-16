package main.gameService;

import utils.Position;

/**
 * Created by said on 23.10.15.
 */
public class Platform {
    private Position position;
    private int velocity;

    public Platform(Position position, int velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void moveLeft() {
        position.setX(position.getX() - velocity);
    }
}
