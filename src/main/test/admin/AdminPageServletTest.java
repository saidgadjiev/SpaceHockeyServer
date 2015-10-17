package admin;

import frontend.ServletTest;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static templater.PageGenerator.setResponseDataAdmin;

/**
 * Created by said on 15.10.15.
 */
public class AdminPageServletTest extends ServletTest {
    private AdminPageServlet admin;

    @Before
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        response = getTestResponse(stringWriter);
        accountService = getTestAccountService();
        admin = new AdminPageServlet(accountService);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        request = getTestRequest(null);
        when(request.getParameter("shutdown")).thenReturn(String.valueOf(1000));
        admin.doGet(request, response);
        assertEquals(setResponseDataAdmin(HttpServletResponse.SC_OK, 0, 0), stringWriter.toString());
    }

    @Test
    public void testDoGetWrongQuery() throws ServletException, IOException {
        request = getTestRequest(null);
        admin.doGet(request, response);
        assertEquals(setResponseDataAdmin(HttpServletResponse.SC_BAD_REQUEST, 0, 0), stringWriter.toString());
    }
}