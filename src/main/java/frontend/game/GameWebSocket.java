package frontend.game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gameMechanics.GameSession;
import main.gameService.GameMechanics;
import main.gameService.Player;
import main.gameService.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

/**
  Created by said on 20.10.15.
 */

@WebSocket
public class GameWebSocket {
    private Player myPlayer;
    private Session session;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myPlayer = new Player(myName);
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public Player getMyPlayer() {
        return myPlayer;
    }

    public void sendJSON(JsonObject jsonData) {
        try {
            session.getRemote().sendString(jsonData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        JsonObject jsonStart = new JsonObject();
        jsonStart.addProperty("status", "start");

        JsonObject jsonFirst = new JsonObject();
        jsonFirst.addProperty("position", firstPlayer.getMyPosition());
        jsonFirst.addProperty("name", firstPlayer.getName());

        JsonObject jsonSecond = new JsonObject();
        jsonSecond.addProperty("position", secondPlayer.getMyPosition());
        jsonSecond.addProperty("name", secondPlayer.getName());

        jsonStart.add("first", jsonFirst);
        jsonStart.add("second", jsonSecond);
        sendJSON(jsonStart);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        JsonObject jsonObject  = new Gson().fromJson(data, JsonObject.class);
        gameMechanics.analizeMessage(myPlayer, jsonObject);
        gameMechanics.incrementScore(myPlayer);
    }

    @OnWebSocketConnect
    public void onOpen(Session userSession) {
        //noinspection CallToSimpleSetterFromWithinClass
        setSession(userSession);
        webSocketService.addPlayer(this);
        gameMechanics.addPlayer(myPlayer);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.print("Close\n");
    }

    public void syncPlatformDirection(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        JsonObject jsonSync = new JsonObject();
        jsonSync.addProperty("status", "movePlatform");

        JsonObject jsonFirst = new JsonObject();
        jsonFirst.addProperty("position", firstPlayer.getMyPosition());
        jsonFirst.addProperty("direction", firstPlayer.getPlatform().getDirection().ordinal());

        JsonObject jsonSecond = new JsonObject();
        jsonSecond.addProperty("position", secondPlayer.getMyPosition());
        jsonSecond.addProperty("direction", secondPlayer.getPlatform().getDirection().ordinal());

        jsonSync.add("first", jsonFirst);
        jsonSync.add("second", jsonSecond);
        sendJSON(jsonSync);
    }

    public void syncScore(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        JsonObject jsonSync = new JsonObject();
        jsonSync.addProperty("status", "incrementScore");

        JsonObject jsonFirst = new JsonObject();
        jsonFirst.addProperty("position", firstPlayer.getMyPosition());
        jsonFirst.addProperty("score", firstPlayer.getScore());

        JsonObject jsonSecond = new JsonObject();
        jsonSecond.addProperty("position", secondPlayer.getMyPosition());
        jsonSecond.addProperty("score", secondPlayer.getScore());

        jsonSync.add("first", jsonFirst);
        jsonSync.add("second", jsonSecond);
        sendJSON(jsonSync);
        System.out.println(jsonSync.toString());
    }

    public void gameOver(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        int gameState = 0;

        if (firstPlayer.getResultStatus() == 1) {
            gameState = 1;
        } else if (firstPlayer.getResultStatus() == 2) {
            gameState = 2;
        }

        JsonObject jsonFinish = new JsonObject();

        jsonFinish.addProperty("status", "finish");
        jsonFinish.addProperty("gameState", gameState);
        sendJSON(jsonFinish);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
