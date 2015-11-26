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
 * Created by said on 09.10.15.
 */
public class SignUpServletTest extends ServletTest {
    private SignUpServlet signup;

    private void setRequestReader(String login, String password, String email) throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(
                new StringReader(
                        "{\"login\":\"" + login + "\"," +
                                "\"password\":\"" + password + "\"," +
                                "\"email\":\"" + email+  "\"}"
                )));
    }

    private void setWrongDataToRequestReader(String login, String password, String email) throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(
                        new StringReader(
                                "\"login\":" + login + "\"&" + "\"password\":" + password + "\"&email\":" + email)
                )
        );
    }

    @Before
    public void setUp() throws IOException {
        accountService = getTestAccountService();
        signup = new SignUpServlet(accountService);
        stringWriter = new StringWriter();
        response = getTestResponse(stringWriter);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        int status = HttpServletResponse.SC_OK;
        String login = getTestUser().getLogin();
        String password = getTestUser().getPassword();
        String email = getTestUser().getEmail();

        request = getTestRequest(null);
        setRequestReader(login, password, email);
        signup.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, email), stringWriter.toString());
    }

    @Test
    public void testDoPostUserAlreadyExist() throws IOException, ServletException {
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        String login = getTestUser().getLogin();
        String password = getTestUser().getPassword();
        String email = getTestUser().getEmail();

        request = getTestRequest(null);
        setRequestReader(login, password, email);
        accountService.addUser(getTestUser().getLogin(), getTestUser());
        signup.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, email), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongLogin() throws IOException, ServletException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = "";
        String password = getTestUser().getPassword();
        String email = getTestUser().getEmail();

        request = getTestRequest(null);
        setRequestReader(login, password, email);
        signup.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, email), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongPassword() throws IOException, ServletException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = getTestUser().getLogin();
        String password = "";
        String email = getTestUser().getEmail();

        request = getTestRequest(null);
        setRequestReader(login, password, email);
        signup.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, email), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongPostQuery() throws ServletException, IOException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = getTestUser().getLogin();
        String password = getTestUser().getPassword();
        String email = getTestUser().getEmail();

        request = getTestRequest(null);
        setWrongDataToRequestReader(login, password, email);
        signup.doPost(request, response);
        assertEquals(setResponseDataUser(status, "", "", ""), stringWriter.toString());
    }

    @Test
    public void testDoPostWrongEmail() throws IOException, ServletException {
        int status = HttpServletResponse.SC_BAD_REQUEST;
        String login = getTestUser().getLogin();
        String password = getTestUser().getPassword();
        String email = "test_email";

        request = getTestRequest(null);
        setRequestReader(login, password, email);
        email = "";
        signup.doPost(request, response);
        assertEquals(setResponseDataUser(status, login, password, email), stringWriter.toString());
    }
}