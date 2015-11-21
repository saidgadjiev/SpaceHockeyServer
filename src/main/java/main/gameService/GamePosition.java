package main.gameService;

/**
  Created by said on 21.11.15.
 */
public enum GamePosition {
    NONE,
    UPPER,
    LOWER;

    public GamePosition getOposite() {
        if (this == UPPER) {
            return LOWER;
        }
        return UPPER;
    }
}
