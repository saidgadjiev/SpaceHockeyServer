package frontend;

import main.AccountService;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by said on 21.09.15.
 */
public class SignOutServlet extends HttpServlet {
    private AccountService accountService;

    public SignOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;

        if (request.getSession().getAttribute("login") != null) {
            accountService.deleteSession(request.getSession().getId());
            request.getSession().invalidate();
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.setResponseDataUser(status, "", ""));
    }
}
