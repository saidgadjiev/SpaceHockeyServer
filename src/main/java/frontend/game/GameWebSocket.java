package frontend.game;

import com.google.gson.JsonObject;
import main.gameService.GameMechanics;
import main.gameService.GameUser;
import main.gameService.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
 * Created by said on 20.10.15.
 */

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void sendJSON(JsonObject jsonData) {
        try {
            session.getRemote().sendString(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(GameUser user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "start");
        jsonStart.addProperty("enemyName", user.getEnemyName());
        sendJSON(jsonStart);
    }

    public void gameOver(int gameState) {
        JsonObject jsonFinish = new JsonObject();

        jsonFinish.addProperty("status", "finish");
        jsonFinish.addProperty("gameState", gameState);
        sendJSON(jsonFinish);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.print("Message");
        gameMechanics.incrementScore(myName);
    }

    @OnWebSocketConnect
    public void onOpen(Session userSession) {
        //noinspection CallToSimpleSetterFromWithinClass
        setSession(userSession);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.print("Close\n");
    }

    public void setMyScore(GameUser user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "increment");
        jsonStart.addProperty("name", myName);
        jsonStart.addProperty("gameState", user.getGameState());
        jsonStart.addProperty("score", user.getMyScore());
        sendJSON(jsonStart);
    }

    public void setEnemyScore(GameUser user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "increment");
        jsonStart.addProperty("name", user.getEnemyName());
        jsonStart.addProperty("gameState", user.getGameState());
        jsonStart.addProperty("score", user.getEnemyScore());
        sendJSON(jsonStart);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
