package frontend.game;

import gameMechanics.GameMechanicsImpl;
import main.gameService.GameMechanics;
import main.gameService.GameUser;
import main.gameService.WebSocketService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by said on 31.10.15.
 */
public class WebSocketServiceImplTest {
    private WebSocketService webSocketService;
    private GameWebSocket gameWebSocket1;
    private GameWebSocket gameWebSocket2;

    @Before
    public void setUp() {
        webSocketService = mock(WebSocketServiceImpl.class);
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        gameWebSocket1 = new GameWebSocket("myName", gameMechanics, webSocketService);
        webSocketService.addUser(gameWebSocket1);
        gameWebSocket2 = new GameWebSocket("enemyName", gameMechanics, webSocketService);
        webSocketService.addUser(gameWebSocket2);
    }

    @Test
    public void testNotifyGameOver() throws Exception {

    }
}