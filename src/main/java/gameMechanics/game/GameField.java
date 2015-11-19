package gameMechanics.game;

/**
 * Created by said on 18.11.15.
 */
public class GameField {
    private int width = 500;
    private int height = 630;
    private Position position = new Position(40, 40);

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
