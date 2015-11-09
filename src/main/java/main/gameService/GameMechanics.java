package main.gameService;

import utils.Position;

/**
 * Created by said on 20.10.15.
 */
public interface GameMechanics {
    public void addUser(String user);

    //public void incrementScore(String username);

    public void test(String username);

    public void setNewPlatformPosition(String username, Position position);

    public void run();
}
