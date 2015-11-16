package main.gameService;

import com.google.gson.JsonObject;

/**
 * Created by said on 20.10.15.
 */
public interface GameMechanics {
    void addPlayer(Player player);

    void incrementScore(Player player);

    void run();

    void analizeMessage(Player myPlayer, JsonObject message);
}
