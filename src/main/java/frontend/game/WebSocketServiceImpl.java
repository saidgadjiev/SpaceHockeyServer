package frontend.game;

import main.gameService.Game;
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
    public void notifyMyNewScore(Game user) {
        userSockets.get(user.getMyPlayer()).setMyScore(user);
    }

    @Override
    public void notifyEnemyNewScore(Game user) {
        userSockets.get(user.getMyPlayer()).setEnemyScore(user);
    }

    @Override
    public void notifyMyPlatformNewDirection(Game user) {
        userSockets.get(user.getMyPlayer()).sendMyPlatformDirection(user);
    }

    @Override
    public void notifyEnemyPlatformNewDirection(Game user) {
        userSockets.get(user.getMyPlayer()).sendEnemyPlatformDirection(user);
    }

    @Override
    public void notifyMyBallNewMootion(Game user) {
        userSockets.get(user.getMyPlayer()).sendMyBallMotion(user);
    }

    @Override
    public void notifyEnemyBallNewMotion(Game user) {
        userSockets.get(user.getEnemyPlayer()).sendEnemyBallMotion(user);
    }

    @Override
    public void notifyStartGame(Game user) {
        userSockets.get(user.getMyPlayer()).startGame(user);
    }

    @Override
    public void notifyGameOver(Game user) {
        userSockets.get(user.getMyPlayer()).gameOver(user.getGameState());
    }
}