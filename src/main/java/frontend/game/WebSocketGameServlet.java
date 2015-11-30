package frontend.game;

import frontend.transport.TransportSystem;
import main.accountService.AccountService;
import main.gameService.GameMechanics;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
  Created by said on 20.10.15.
 */

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/gameplay"})
public class WebSocketGameServlet extends WebSocketServlet {
    private static final int IDLE_TIME = 60 * 1000;
    private AccountService accountService;
    private TransportSystem transportSystem;
    private GameMechanics gameMechanics;

    public WebSocketGameServlet(AccountService accountService, TransportSystem transportSystem, GameMechanics gameMechanics) {
        this.accountService = accountService;
        this.transportSystem = transportSystem;
        this.gameMechanics = gameMechanics;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new GameWebSocketCreator(accountService, transportSystem, gameMechanics));
    }
}
