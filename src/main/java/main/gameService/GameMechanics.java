package main.gameService;

import com.google.gson.JsonObject;

/**
 * Created by said on 20.10.15.
 */
public interface GameMechanics {
    public void addUser(String user);

    public void incrementScore(String username);

    public void movePlatfom(String username, JsonObject data);

    public void run();
}
