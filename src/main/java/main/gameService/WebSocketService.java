package main.gameService;

import frontend.game.GameWebSocket;

/**
 * Created by said on 20.10.15.
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void notifyMyNewScore(GameUser user);

    void notifyEnemyNewScore(GameUser user);

    void notifyNewMyPlatformPosition(GameUser user);

    void notifyNewEnemyPlatformPosition(GameUser user);

    void notifyStartGame(GameUser user);

    void notifyGameOver(GameUser user, boolean win);
}
