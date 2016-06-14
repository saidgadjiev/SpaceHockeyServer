package frontend.game;

import frontend.transport.TransportSystem;
import main.accountService.AccountService;
import main.gameService.GameMechanics;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author v.chibrikov
 */
public class GameWebSocketCreator implements WebSocketCreator {
    private AccountService accountService;
    private TransportSystem transportSystem;
    private GameMechanics gameMechanics;

    public GameWebSocketCreator(AccountService accountService, TransportSystem transportSystem, GameMechanics gameMechanics) {
        this.accountService = accountService;
        this.transportSystem = transportSystem;
        this.gameMechanics = gameMechanics;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = accountService.getSessions(sessionId).getLogin();
        return new GameWebSocket(name, transportSystem, gameMechanics);
    }
}