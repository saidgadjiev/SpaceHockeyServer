package frontend.game;

import gameMechanics.GameSession;
import main.gameService.Player;
import main.gameService.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
  Created by said on 20.10.15.
 */

public class WebSocketServiceImpl implements WebSocketService {
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addPlayer(GameWebSocket user) {
        userSockets.put(user.getMyPlayer().getName(), user);
    }

    @Override
    public void notifyStartGame(GameSession session, Player player) {
        userSockets.get(player.getName()).startGame(session);
    }

    @Override
    public void notifyGameOver(GameSession session, Player player) {
        userSockets.get(player.getName()).gameOver(session);
    }

    @Override
    public void notifySyncPlatformDirection(GameSession session, Player player) {
        userSockets.get(player.getName()).syncPlatformDirection(session);
    }

    @Override
    public void notifySyncScore(GameSession session, Player player) {
        userSockets.get(player.getName()).syncScore(session);
    }

    @Override
    public void syncGameWorld(GameSession session, Player player) {
        userSockets.get(player.getName()).syncGameWorld(session);
    }

    public void removeWebSocket(Player player) {
        userSockets.remove(player.getName());
    }
}