package gameMechanics;

import frontend.game.GameWebSocket;
import frontend.game.WebSocketServiceImpl;
import main.gameService.GameMechanics;
import main.gameService.GameUser;
import main.gameService.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import resource.GameMechanicsSettings;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by said on 30.10.15.
 */
public class GameMechanicsImplTest {
    @SuppressWarnings("FieldCanBeLocal")
    private GameMechanics gameMechanics;
    @SuppressWarnings("FieldCanBeLocal")
    private WebSocketService webSocketService;
    @SuppressWarnings("FieldCanBeLocal")
    private GameWebSocket gameWebSocket;
    private FakeRemoteEndPoint fakeRemoteEndPoint1 = new FakeRemoteEndPoint();
    private FakeRemoteEndPoint fakeRemoteEndPoint2 = new FakeRemoteEndPoint();

    @Before
    public void setUp() {
        webSocketService = new WebSocketServiceImpl();
        GameMechanicsSettings gameMechanicsSettings = new GameMechanicsSettings();
        gameMechanicsSettings.loadSettingsFromFile("data/gameMechanicsSettings.xml");
        gameMechanics = new GameMechanicsImpl(webSocketService, gameMechanicsSettings);
        gameWebSocket = new GameWebSocket("myName", gameMechanics, webSocketService);

        Session testSession1 = mock(Session.class);
        Session testSession2 = mock(Session.class);
        when(testSession1.getRemote()).thenReturn(fakeRemoteEndPoint1);
        when(testSession2.getRemote()).thenReturn(fakeRemoteEndPoint2);
        gameWebSocket.onOpen(testSession1);
        gameWebSocket = new GameWebSocket("enemyName", gameMechanics, webSocketService);
        gameWebSocket.onOpen(testSession2);
    }

    @Test
    public void testAddUserAndStartGame() throws Exception {
        String testJson1 = "{\"status\":\"start\",\"enemyName\":\"enemyName\"}";
        String testJson2 = "{\"status\":\"start\",\"enemyName\":\"myName\"}";
        assertEquals(testJson1, fakeRemoteEndPoint1.getData());
        assertEquals(testJson2, fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testGameOver() {
        GameUser testUser1 = new GameUser("myName");
        GameUser testUser2 = new GameUser("enemyName");
        String testJson = "{\"status\":\"finish\",\"gameState\":0}";

        webSocketService.notifyGameOver(testUser1);
        webSocketService.notifyGameOver(testUser2);

        assertEquals(testJson, fakeRemoteEndPoint1.getData());
        assertEquals(testJson, fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testIncrementScore() {
        String testJson2 = "{\"status\":\"increment\",\"name\":\"enemyName\",\"score\":1}";

        gameWebSocket.onMessage("{}");
        assertEquals(testJson2, fakeRemoteEndPoint1.getData());
        assertEquals(testJson2, fakeRemoteEndPoint2.getData());
    }
}