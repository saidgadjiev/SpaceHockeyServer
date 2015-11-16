package frontend.game;

import gameMechanics.GameMechanicsImpl;
import main.accountService.AccountService;
import main.accountService.AccountServiceImpl;
import main.gameService.GameMechanics;
import main.gameService.WebSocketService;
import main.user.UserProfile;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by said on 30.10.15.
 */
public class GameWebSocketCreatorTest {
    private AccountService accountService;
    private ServletUpgradeRequest servletReqMock;
    private ServletUpgradeResponse servletRespMock;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountServiceImpl();
        servletReqMock = mock(ServletUpgradeRequest.class);
        servletRespMock = mock(ServletUpgradeResponse.class);
    }

    @Test
    public void testCreateWebSocket() throws Exception {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpSession sessionMock = mock(HttpSession.class);

        String sessionID = "testSessionID";
        when(sessionMock.getId()).thenReturn(sessionID);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(servletReqMock.getHttpServletRequest()).thenReturn(requestMock);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        UserProfile testProfile = new UserProfile("test_login1", "test_password", "test@mail.ru");

        accountService.addSessions(sessionID, testProfile);
        GameWebSocketCreator testCreator = new GameWebSocketCreator(accountService, gameMechanics, webSocketService);
        Object object =  testCreator.createWebSocket(servletReqMock, servletRespMock);

        assertEquals(true, object instanceof GameWebSocket);
        //noinspection ConstantConditions
        assertEquals(new GameWebSocket(testProfile.getLogin(), gameMechanics, webSocketService).getMyPlayer().getName(),
                ((GameWebSocket) object).getMyPlayer().getName());
    }
}