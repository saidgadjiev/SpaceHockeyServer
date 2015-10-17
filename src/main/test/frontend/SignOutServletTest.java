package frontend;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static templater.PageGenerator.setResponseDataUser;

/**
 * Created by said on 15.10.15.
 */
public class SignOutServletTest extends ServletTest {
    SignOutServlet signout;

    @Before
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        response = getTestResponse(stringWriter);
        accountService = getTestAccountService();
        signout = new SignOutServlet(accountService);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        String login = getTestUser().getLogin();
        String password = getTestUser().getPassword();
        String email = getTestUser().getEmail();

        request = getTestRequest(getTestUser().getLogin());
        accountService.addSessions(login, getTestUser());
        signout.doGet(request, response);
        assertEquals(setResponseDataUser(HttpServletResponse.SC_OK, login, password, email), stringWriter.toString());
    }

    @Test
    public void testDoGetUnauthorized() throws ServletException, IOException {
        request = getTestRequest(null);
        signout.doGet(request, response);
        assertEquals(setResponseDataUser(HttpServletResponse.SC_UNAUTHORIZED, "", "", ""), stringWriter.toString());
    }
}