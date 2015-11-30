package frontend.transport;

import com.google.gson.JsonObject;
import frontend.game.GameWebSocket;
import gameMechanics.GameSession;
import gameMechanics.game.Ball;
import gameMechanics.game.Direction;
import main.gameService.GameMechanics;
import main.gameService.Player;
import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by said on 30.11.15.
 */
public class TransportSystem {
    private GameMechanics gameMechanics;
    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    public TransportSystem(GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
    }

    public void addWebSocket(GameWebSocket gameWebSocket) {
        userSockets.put(gameWebSocket.getMyPlayer().getName(), gameWebSocket);
    }

    private void sendJSON(Session session, JsonObject jsonData) {
        if (session.isOpen()) {
            session.getRemote().sendStringByFuture(jsonData.toString());
        }
    }

    private Direction getDirectionFromMessage(JsonObject message) {
        switch (message.get("direction").getAsString()) {
            case "LEFT":
                return Direction.LEFT;
            case "RIGHT":
                return Direction.RIGHT;
            default:
                return Direction.STOP;
        }
    }

    public void analizeMessage(Player myPlayer, JsonObject message) {
        if (message.get("status").getAsString().equals("movePlatform")) {
            gameMechanics.changePlatformDirection(myPlayer, getDirectionFromMessage(message));
        }
    }

    public void syncGameWorld(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();
        Ball ball = session.getBall();

        JsonObject jsonSync = new JsonObject();
        jsonSync.addProperty("status", "worldInfo");

        JsonObject jsonFirst = new JsonObject();
        jsonFirst.addProperty("positionX", firstPlayer.getPlatform().getPosition().getX());
        jsonFirst.addProperty("positionY", firstPlayer.getPlatform().getPosition().getY());

        JsonObject jsonSecond = new JsonObject();
        jsonSecond.addProperty("positionX", secondPlayer.getPlatform().getPosition().getX());
        jsonSecond.addProperty("positionY", secondPlayer.getPlatform().getPosition().getY());

        JsonObject jsonBall = new JsonObject();
        jsonBall.addProperty("positionX", ball.getPosition().getX());
        jsonBall.addProperty("positionY", ball.getPosition().getY());

        jsonSync.add("first", jsonFirst);
        jsonSync.add("second", jsonSecond);
        jsonSync.add("ball", jsonBall);
        sendJSON(userSockets.get(firstPlayer.getName()).getSession(), jsonSync);
        sendJSON(userSockets.get(secondPlayer.getName()).getSession(), jsonSync);
        //System.out.println(jsonSync);
    }


    public void syncScore(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        JsonObject jsonSync = new JsonObject();
        jsonSync.addProperty("status", "incrementScore");

        JsonObject jsonFirst = new JsonObject();
        jsonFirst.addProperty("position", firstPlayer.getMyPosition().ordinal());
        jsonFirst.addProperty("score", firstPlayer.getScore());

        JsonObject jsonSecond = new JsonObject();
        jsonSecond.addProperty("position", secondPlayer.getMyPosition().ordinal());
        jsonSecond.addProperty("score", secondPlayer.getScore());

        jsonSync.add("first", jsonFirst);
        jsonSync.add("second", jsonSecond);
        sendJSON(userSockets.get(firstPlayer.getName()).getSession(), jsonSync);
        sendJSON(userSockets.get(secondPlayer.getName()).getSession(), jsonSync);
    }

    public void startGame(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        JsonObject jsonStart = new JsonObject();
        jsonStart.addProperty("status", "start");

        JsonObject jsonFirst = new JsonObject();
        jsonFirst.addProperty("position", firstPlayer.getMyPosition().ordinal());
        jsonFirst.addProperty("name", firstPlayer.getName());

        JsonObject jsonSecond = new JsonObject();
        jsonSecond.addProperty("position", secondPlayer.getMyPosition().ordinal());
        jsonSecond.addProperty("name", secondPlayer.getName());

        jsonStart.add("first", jsonFirst);
        jsonStart.add("second", jsonSecond);
        sendJSON(userSockets.get(firstPlayer.getName()).getSession(), jsonStart);
        sendJSON(userSockets.get(secondPlayer.getName()).getSession(), jsonStart);
    }

    public void gameOver(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        JsonObject jsonFinish = new JsonObject();
        jsonFinish.addProperty("status", "finish");
        jsonFinish.addProperty("gameState", session.getResultState().ordinal());

        sendJSON(userSockets.get(firstPlayer.getName()).getSession(), jsonFinish);
        sendJSON(userSockets.get(secondPlayer.getName()).getSession(), jsonFinish);
    }

    public void removeWebSocket(GameSession session) {
        userSockets.remove(session.getFirstPlayer().getName());
        userSockets.remove(session.getSecondPlayer().getName());
    }
}
