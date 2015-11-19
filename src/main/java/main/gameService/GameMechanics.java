package main.gameService;

import com.google.gson.JsonObject;
import gameMechanics.GameSession;

import java.util.List;

/**
  Created by said on 20.10.15.
 */

public interface GameMechanics {

    void addPlayer(Player player);

    void incrementScore(Player player);

    void run();

    @SuppressWarnings("SpellCheckingInspection")
    void analizeMessage(Player myPlayer, JsonObject message);

    void createGame();

    List<GameSession> getAllSessions();
}
