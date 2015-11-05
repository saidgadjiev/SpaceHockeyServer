package frontend.game;

import frontend.ServletTest;
import org.junit.Before;
import org.junit.Test;
import templater.PageGenerator;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by said on 31.10.15.
 */
public class GameServletTest extends ServletTest {
    private GameServlet gameServlet;

    private void setRequestReader(String login) throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(
                        new StringReader(
                                "\"name\":" + login)
                )
        );
    }

    @Before
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        response = getTestResponse(stringWriter);
        accountService = getTestAccountService();
        gameServlet = new GameServlet(accountService);
    }

    @Test
    public void testDoPost() throws IOException, ServletException {
        request = getTestRequest(null);
        gameServlet.doPost(request, response);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("myName", getTestUser().getLogin());
        assertEquals(PageGenerator.getPage("game.html", pageVariables), stringWriter.toString());
    }
}