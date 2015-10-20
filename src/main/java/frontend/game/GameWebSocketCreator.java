package frontend.game;

import main.gameService.AuthService;
import main.gameService.GameMechanics;
import main.gameService.WebSocketService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * @author v.chibrikov
 */
public class GameWebSocketCreator implements WebSocketCreator {
    private AuthService authService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocketCreator(AuthService authService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.authService = authService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        System.out.print("Request");
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = authService.getUserName(sessionId);
        return new GameWebSocket(name, gameMechanics, webSocketService);
    }
}