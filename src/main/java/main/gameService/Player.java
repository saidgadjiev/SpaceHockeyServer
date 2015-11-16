package main.gameService;

import gameMechanics.game.Platform;

/**
 * Created by said on 14.11.15.
 */
public class Player {
    private String name;
    private int score = 0;
    private Platform platform;

    public Player(String name, Platform platform) {
        this.name = name;
        this.platform = platform;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void movePlatform() {
        platform.move();
    }
}
