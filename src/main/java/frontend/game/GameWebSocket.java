package frontend.game;

import main.gameService.GameMechanics;
import main.gameService.GameUser;
import main.gameService.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;

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

    public void startGame(GameUser user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            jsonStart.put("x", user.getEnemyPlatformPosition().getX());
            jsonStart.put("y", user.getEnemyPlatformPosition().getY());
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(GameUser user, boolean win) {
        try {
            JSONObject jsonFinish = new JSONObject();
            jsonFinish.put("status", "finish");
            jsonFinish.put("win", win);
            session.getRemote().sendString(jsonFinish.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
<<<<<<< HEAD
        System.out.print("Message");
        gameMechanics.incrementScore(myName);
=======
        System.out.print(data + "\n");
        gameMechanics.test(myName);
        //JSONObject message = getJsonFromString(data);
        //gameMechanics.setNewPlatformPosition(myName, new Position(Integer.valueOf(message.get("x").toString()), Integer.valueOf(message.get("y").toString())));
>>>>>>> 5c78e961d052a5a626cb9b5607853af8a77ecb03
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        System.out.print("Open\n");
        setSession(session);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.print("Close\n");
    }

    public void setMyScore(GameUser user) {
        System.out.print("SetMyCsore\n");
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", myName);
        //jsonStart.put("score", user.getMyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEnemyScore(GameUser user) {
        System.out.print("SetEnemyScore\n");
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        //jsonStart.put("name", user.getEnemyName());
        //jsonStart.put("score", user.getEnemyScore());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewMyPlatformPosition(GameUser user) {
        System.out.print("New my platform position");
        JSONObject jsonPosition = new JSONObject();
        jsonPosition.put("status", "Move");
        //jsonPosition.put("status", "UpdatePosition");
        //jsonPosition.put("name", myName);
        //jsonPosition.put("x", user.getMyPlatformPosition().getX());
        //jsonPosition.put("y", user.getMyPlatformPosition().getY());
        try {
            session.getRemote().sendString(jsonPosition.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewEnemyPlatformPosition(GameUser user) {
        System.out.print("New enemy platform position");
        JSONObject jsonPosition = new JSONObject();
        jsonPosition.put("status", "Move");
        //jsonPosition.put("status", "UpdatePosition");
        //jsonPosition.put("name", user.getEnemyName());
        //jsonPosition.put("x", user.getEnemyPlatformPosition().getX());
        //jsonPosition.put("y", user.getEnemyPlatformPosition().getY());
        try {
            session.getRemote().sendString(jsonPosition.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
