package frontend.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import frontend.transport.TransportSystem;
import main.gameService.GameMechanics;
import main.gameService.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
  Created by said on 20.10.15.
 */

@WebSocket
public class GameWebSocket {
    private Player myPlayer;
    private Session session;
    private TransportSystem transportSystem;
    private GameMechanics gameMechanics;

    public GameWebSocket(String myName, TransportSystem transportSystem, GameMechanics gameMechanics) {
        this.myPlayer = new Player(myName);
        this.transportSystem = transportSystem;
        this.gameMechanics = gameMechanics;
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        JsonObject jsonObject  = new Gson().fromJson(data, JsonObject.class);
        transportSystem.analizeMessage(myPlayer, jsonObject);
    }

    @OnWebSocketConnect
    public void onOpen(Session userSession) {
        //noinspection CallToSimpleSetterFromWithinClass
        setSession(userSession);
        transportSystem.addWebSocket(this);
        gameMechanics.addPlayer(myPlayer);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.print("Close\n");
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
