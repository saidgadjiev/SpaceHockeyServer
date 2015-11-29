package frontend;

import main.accountService.AccountService;
import main.user.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static templater.PageGenerator.setResponseDataUser;

/**
 * Created by said on 26.11.15.
 */
public class ProfileServlet extends HttpServlet {
    private AccountService accountService;

    public ProfileServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        UserProfile userProfile = accountService.getSessions(session.getId());
        int status = HttpServletResponse.SC_OK;
        String login = "";
        String password = "";
        String email = "";

        if (userProfile == null) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
        } else {
            login = userProfile.getLogin();
            password = userProfile.getPassword();
            email = userProfile.getEmail();
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(setResponseDataUser(status, login, password, email));
    }
}
