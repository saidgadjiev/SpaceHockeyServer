package main.gameService;

import gameMechanics.game.Platform;
import org.jetbrains.annotations.NotNull;

/**
  Created by said on 14.11.15.
 */

public class Player implements Comparable {
    private String name;
    private int score = 0;
    private Platform platform;
    private GamePosition myPosition = GamePosition.NONE;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void incrementScore() {
        score++;
    }

    public void setMyPosition(GamePosition myPosition) {
        this.myPosition = myPosition;
    }

    public GamePosition getMyPosition() {
        return myPosition;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Player obj = (Player) o;

        return name.compareTo(obj.getName());
    }
}
