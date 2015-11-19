package gameMechanics.game;

/**
  Created by said on 18.11.15.
 */

public class GameField {
    private int width;
    private int height;
    private Position position = new Position(40, 40);

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getPosition() {
        return position;
    }
}
