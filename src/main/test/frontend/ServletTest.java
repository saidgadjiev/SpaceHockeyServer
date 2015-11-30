package frontend;

import main.accountService.AccountServiceImpl;
import main.user.UserProfile;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by said on 13.10.15.
 */
public class ServletTest {
    protected AccountServiceImpl accountService;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected StringWriter stringWriter;

    public AccountServiceImpl getTestAccountService() {
        return new AccountServiceImpl();
    }

    public HttpServletRequest getTestRequest(@Nullable String sessionID) {
        HttpSession sessionMock = mock(HttpSession.class);
        HttpServletRequest requestMock = mock(HttpServletRequest.class);

        when(sessionMock.getAttribute("login")).thenReturn(sessionID);
        when(sessionMock.getId()).thenReturn(sessionID);
        when(requestMock.getSession(true)).thenReturn(sessionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(requestMock.getParameter("name")).thenReturn("test_login");

        return requestMock;
    }

    public HttpServletResponse getTestResponse(StringWriter responseWriter) throws IOException {
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        PrintWriter printWriter = new PrintWriter(responseWriter);

        when(responseMock.getWriter()).thenReturn(printWriter);

        return responseMock;
    }

    public UserProfile getTestUser() {
        return new UserProfile("test_login", "test_password", "test_email@mail.ru");
    }
}