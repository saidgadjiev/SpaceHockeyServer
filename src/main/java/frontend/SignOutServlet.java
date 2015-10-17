package frontend;

import main.user.UserProfile;
import main.accountService.AccountServiceImpl;
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
    private AccountServiceImpl accountService;

    public SignOutServlet(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;
        String login = "";
        String password = "";
        String email = "";

        if (request.getSession().getAttribute("login") != null) {
            UserProfile profile = accountService.getSessions(request.getSession().getId());

            login = profile.getLogin();
            password = profile.getPassword();
            email = profile.getEmail();
            accountService.deleteSession(request.getSession().getId());
            request.getSession().invalidate();
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(PageGenerator.setResponseDataUser(status, login, password, email));
    }
}
