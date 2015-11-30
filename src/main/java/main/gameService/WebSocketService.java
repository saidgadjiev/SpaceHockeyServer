package main.gameService;

import frontend.game.GameWebSocket;
import gameMechanics.GameSession;

/**
 * Created by said on 20.10.15.
 */
public interface WebSocketService {

    void addPlayer(GameWebSocket user);

    void notifyStartGame(GameSession session, Player player);

    void notifySyncPlatformDirection(GameSession session, Player player);

    void notifySyncScore(GameSession session, Player player);

    void syncGameWorld(GameSession session, Player player);

    void notifyGameOver(GameSession session, Player player);

    void removeWebSocket(Player player);
}
