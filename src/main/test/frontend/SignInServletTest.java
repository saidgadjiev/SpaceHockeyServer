package frontend;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static templater.PageGenerator.setResponseDataUser;

/**
 * Created by said on 14.10.15.
 */
public class SignInServletTest extends ServletTest {
    private SignInServlet signin;

    private void setRequestReader(String login, String password) throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(
                new StringReader(
                        "{\"login\":\"" + login + "\"," +
                                "\"password\":\"" + password + "\"}"
                                )));
    }

    private void setWrongDataToReader(String login, String password) throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(
                new StringReader(
                        "\"login\":" + login + "\"&" + "\"password\":" + password)
                )
        );
    }

    @Before
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        response = getTestResponse(stringWriter);
        accountService = getTestAccountService();
        signin = new SignInServlet(accountService);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        int status = HttpServletResponse.SC_OK;
        String login = getTestUser().getLogin();
        String password = getTestUser().getPassword();

        request = getTestRequest(null);
        setRequestReader(login, getTestUser().getPassword());
        accountService.addUser(login, getTestUser());
        signin.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, getTestUser().getEmail()), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongPassword() throws IOException, ServletException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = getTestUser().getLogin();
        String password = "";

        request = getTestRequest(null);
        setRequestReader(login, password);
        signin.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, ""), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongLogin() throws IOException, ServletException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = "";
        String password = getTestUser().getPassword();

        request = getTestRequest(null);
        setRequestReader(login, password);
        signin.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, ""), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongPostQuery() throws ServletException, IOException {
        request = getTestRequest(null);
        setWrongDataToReader(getTestUser().getLogin(), getTestUser().getPassword());
        signin.doPost(request, response);
        assertEquals(setResponseDataUser(HttpServletResponse.SC_BAD_REQUEST, "", "", ""), stringWriter.toString());
    }

    @Test
    public void testDoPostUserNotExist() throws IOException, ServletException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = getTestUser().getLogin();

        request = getTestRequest(null);
        setRequestReader(login, getTestUser().getPassword());
        signin.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, "", ""), stringWriter.toString());
    }

    @Test
    public void testDoPostAlreadySignedIn() throws IOException, ServletException {
        request = getTestRequest(getTestUser().getLogin());
        setRequestReader(getTestUser().getLogin(), getTestUser().getPassword());
        signin.doPost(request, response);
        assertEquals(setResponseDataUser(HttpServletResponse.SC_FOUND, "", getTestUser().getPassword(), ""), stringWriter.toString());
    }
}