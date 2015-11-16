package main.gameService;

import frontend.game.GameWebSocket;

/**
 * Created by said on 20.10.15.
 */
public interface WebSocketService {

    void addPlayer(GameWebSocket user);

    void notifyMyNewScore(Game user);

    void notifyEnemyNewScore(Game user);

    void notifyMyPlatformNewDirection(Game user);

    void notifyEnemyPlatformNewDirection(Game user);

    void notifyMyBallNewMootion(Game user);

    void notifyEnemyBallNewMotion(Game user);

    void notifyStartGame(Game user);

    void notifyGameOver(Game user);
}
