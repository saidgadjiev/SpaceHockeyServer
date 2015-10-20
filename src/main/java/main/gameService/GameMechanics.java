package main.gameService;

/**
 * Created by said on 20.10.15.
 */
public interface GameMechanics {
    public void addUser(String user);

    public void incrementScore(String username);

    public void run();
}
