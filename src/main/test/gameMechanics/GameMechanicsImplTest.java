package gameMechanics;

import frontend.game.GameWebSocket;
import frontend.game.WebSocketServiceImpl;
import main.accountService.AccountService;
import main.accountService.AccountServiceImpl;
import main.gameService.GameMechanics;
import main.gameService.Player;
import main.gameService.WebSocketService;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import resource.GameMechanicsSettings;
import resource.ResourceFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
  Created by said on 30.10.15.
 */

public class GameMechanicsImplTest {
    @SuppressWarnings("FieldCanBeLocal")
    private GameMechanics gameMechanics;
    @SuppressWarnings("FieldCanBeLocal")
    private WebSocketService webSocketService;
    @SuppressWarnings("FieldCanBeLocal")
    private GameWebSocket gameWebSocket;
    private final RemoteEndpoint remoteEndPointMock1 = mock(RemoteEndpoint.class);
    private final RemoteEndpoint remoteEndPointMock2 = mock(RemoteEndpoint.class);
    private final ArgumentCaptor<String> argumentCaptor1 = ArgumentCaptor.forClass(String.class);
    private final ArgumentCaptor<String> argumentCaptor2 = ArgumentCaptor.forClass(String.class);

    @Before
    public void setUp() {
        AccountService accountService = new AccountServiceImpl();
        try {
            doNothing().when(remoteEndPointMock1).sendString(argumentCaptor1.capture());
            doNothing().when(remoteEndPointMock2).sendString(argumentCaptor2.capture());
        } catch (IOException e) {
            e.printStackTrace();
        }
        webSocketService = new WebSocketServiceImpl();
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) ResourceFactory.getInstance().loadResource("data/testSettings.xml");
        gameMechanics = new GameMechanicsImpl(accountService, webSocketService, gameMechanicsSettings);
        gameWebSocket = new GameWebSocket("myName", gameMechanics, webSocketService);

        Session testSession1 = mock(Session.class);
        Session testSession2 = mock(Session.class);
        when(testSession1.getRemote()).thenReturn(remoteEndPointMock1);
        when(testSession2.getRemote()).thenReturn(remoteEndPointMock2);
        gameWebSocket.onOpen(testSession1);
        gameWebSocket = new GameWebSocket("enemyName", gameMechanics, webSocketService);
        gameWebSocket.onOpen(testSession2);
        gameMechanics.createGame();
    }

    @Test
    public void testAddUserAndStartGame() throws Exception {
        String testJson1 = "{\"status\":\"start\",\"first\":{\"position\":1,\"name\":\"myName\"},\"second\":{\"position\":2,\"name\":\"enemyName\"}}";
        String testJson2 = "{\"status\":\"start\",\"first\":{\"position\":1,\"name\":\"myName\"},\"second\":{\"position\":2,\"name\":\"enemyName\"}}";
        assertEquals(testJson1, argumentCaptor1.getValue());
        assertEquals(testJson2, argumentCaptor1.getValue());
    }

    @Test
    public void testGameOver() {
        List<GameSession> allSessions = gameMechanics.getAllSessions();
        String testJson = "{\"status\":\"finish\",\"gameState\":0}";

        for (GameSession session: allSessions) {
            Player testPlayer1 = session.getFirstPlayer();
            Player testPlayer2 = session.getSecondPlayer();

            webSocketService.notifyGameOver(session, testPlayer1);
            webSocketService.notifyGameOver(session, testPlayer2);
        }

        assertEquals(testJson, argumentCaptor1.getValue());
        assertEquals(testJson, argumentCaptor2.getValue());
    }

    @Test
    public void testIncrementScore() {
        String message = "{\"status\":\"movePlatform\",\"direction\":\"STOP\"}";
        String testJson = "{\"status\":\"incrementScore\",\"first\":{\"position\":1,\"score\":0},\"second\":{\"position\":2,\"score\":1}}";

        gameWebSocket.onMessage(message);
        assertEquals(testJson, argumentCaptor1.getValue());
        assertEquals(testJson, argumentCaptor2.getValue());
    }
}