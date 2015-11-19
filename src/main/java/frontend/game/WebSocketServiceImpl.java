package frontend.game;

import gameMechanics.GameSession;
import main.gameService.Player;
import main.gameService.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by said on 20.10.15.
 */

public class WebSocketServiceImpl implements WebSocketService {
    private Map<Player, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addPlayer(GameWebSocket user) {
        userSockets.put(user.getMyPlayer(), user);
    }

    @Override
    public void notifyMyNewScore(Player user) {
    }

    @Override
    public void notifyEnemyNewScore(Player user) {
    }

    @Override
    public void notifyMyPlatformNewDirection(Player user) {
    }

    @Override
    public void notifyEnemyPlatformNewDirection(Player user) {
    }

    @Override
    public void notifyMyBallNewMootion(Player user) {

    }

    @Override
    public void notifyEnemyBallNewMotion(Player user) {
    }

    @Override
    public void notifyStartGame(Player user) {
        userSockets.get(user).startGame(user);
    }

    @Override
    public void notifyGameOver(Player user) {
        userSockets.get(user).gameOver(0);
    }

    @Override
    public void notifySyncPlatformDirection(GameSession session, Player player) {
        userSockets.get(player).syncPlatformDirection(session);
    }

}