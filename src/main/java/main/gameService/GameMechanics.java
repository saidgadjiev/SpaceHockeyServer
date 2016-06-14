package main.gameService;

import gameMechanics.GameSession;
import gameMechanics.game.Direction;

import java.util.List;

/**
 * Created by said on 20.10.15.
 */

public interface GameMechanics {

    void addPlayer(Player player);

    void incrementScore(Player player);

    void run();

    void createGame();

    List<GameSession> getAllSessions();

    void changePlatformDirection(Player player, Direction direction);
}
