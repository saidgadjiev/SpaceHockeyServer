package frontend;

import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 */

public class SignInServlet extends HttpServlet {
    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        if (request.getSession().getAttribute("login") != null) {
            pageVariables.put("loginStatus", "Loged in");
        } else {
            UserProfile profile = accountService.getUser(name);

            if (profile != null && profile.getPassword().equals(password)) {
                HttpSession currentSession = request.getSession(true);

                currentSession.setAttribute("login", profile.getPassword());
                accountService.addSessions(currentSession.getId(), profile);
                pageVariables.put("loginStatus", "Login passed");
            } else {
                pageVariables.put("loginStatus", "Wrong login/password");
            }
        }
        response.getWriter().println(PageGenerator.getPage("authstatus.html", pageVariables));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String username = "";
        String email = "";
        String password = "";
        String line = "";
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            System.out.print(line);

        response.setStatus(HttpServletResponse.SC_OK);

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("email", email == null ? "" : email);
        pageVariables.put("password", password == null ? "" : password);
        pageVariables.put("username", password == null ? "" : username);

        response.getWriter().println(PageGenerator.getPage("authresponse.txt", pageVariables));
    }
}
