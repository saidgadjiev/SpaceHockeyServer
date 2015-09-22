package frontend;

import main.AccountService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by said on 21.09.15.
 */
public class SignOutServlet extends HttpServlet {
    private AccountService accountService;

    public SignOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();

        if (request.getSession().getAttribute("login") != null) {
            accountService.deleteSession(request.getSession().getId());
            request.getSession().invalidate();
            pageVariables.put("loginStatus", "Logout");
        } else {
            pageVariables.put("loginStatus", "You don't log in");
        }
        response.getWriter().println(PageGenerator.getPage("authstatus.html", pageVariables));
    }
}
