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
 * Created by said on 20.10.15.
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

    public void startGame(Player user) {
        System.out.print("StartGame " + user.getMyPosition());
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "start");
        jsonStart.addProperty("enemyName", user.getMyPosition());
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
        JsonObject jsonObject  = new Gson().fromJson(data, JsonObject.class);
        gameMechanics.analizeMessage(myPlayer, jsonObject);
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
/*
    public void setMyScore(Player user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "increment");
        jsonStart.addProperty("name", myPlayer.getName());
        jsonStart.addProperty("score", user.getMyScore());
        sendJSON(jsonStart);
    }

    public void setEnemyScore(Game user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "increment");
        jsonStart.addProperty("name", user.getEnemyPlayerName());
        jsonStart.addProperty("score", user.getEnemyScore());
        sendJSON(jsonStart);
    }

    public void sendMyPlatformDirection(Game user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "movePlatform");
        jsonStart.addProperty("name", myPlayer.getName());
        jsonStart.addProperty("platformDirection", user.getMyPlatform().getDirection().toString());
        sendJSON(jsonStart);
    }

    public void sendEnemyPlatformDirection(Game user) {
        JsonObject jsonStart = new JsonObject();

        jsonStart.addProperty("status", "movePlatform");
        jsonStart.addProperty("name", user.getEnemyPlayerName());
        jsonStart.addProperty("platformDirection", user.getEnemyPlatform().getDirection().toString());
        sendJSON(jsonStart);
    }

    public void sendMyBallMotion(Game user) {
        JsonObject jsonBallMotion = new JsonObject();

        jsonBallMotion.addProperty("status", "moveBall");
        jsonBallMotion.addProperty("name", myPlayer.getName());
        jsonBallMotion.addProperty("velocityX", user.getBall().getVelocityX());
        jsonBallMotion.addProperty("velocityY", user.getBall().getVelocityY());
        sendJSON(jsonBallMotion);
    }

    public void sendEnemyBallMotion(Game user) {
        JsonObject jsonBallMotion = new JsonObject();

        jsonBallMotion.addProperty("status", "moveBall");
        jsonBallMotion.addProperty("name", user.getEnemyPlayerName());
        jsonBallMotion.addProperty("velocityX", user.getBall().getVelocityX());
        jsonBallMotion.addProperty("velocityY", user.getBall().getVelocityY());
        sendJSON(jsonBallMotion);
    }
*/
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
