package main.gameService;

import frontend.game.GameWebSocket;
import gameMechanics.GameSession;

/**
 * Created by said on 20.10.15.
 */
public interface WebSocketService {

    void addPlayer(GameWebSocket user);

    void notifyMyNewScore(Player user);

    void notifyEnemyNewScore(Player user);

    void notifyMyPlatformNewDirection(Player user);

    void notifyEnemyPlatformNewDirection(Player user);

    void notifyMyBallNewMootion(Player user);

    void notifyEnemyBallNewMotion(Player user);

    void notifyStartGame(GameSession session, Player player);

    void notifySyncPlatformDirection(GameSession session, Player player);

    void notifySyncScore(GameSession session, Player player);

    void notifyGameOver(GameSession session, Player player);
}
