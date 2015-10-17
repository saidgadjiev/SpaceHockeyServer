package frontend;

import main.accountService.AccountServiceImpl;
import main.user.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static main.user.Validator.*;
import static templater.PageGenerator.setResponseDataUser;
import static utilities.JSONFromRequest.getJSONFromRequest;

/**
 * @author v.chibrikov
 */
public class SignInServlet extends HttpServlet {
    private AccountServiceImpl accountService;

    public SignInServlet(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;
        String login = "";
        String password = "";
        String email = "";
        HashMap<String, String> jsonData = getJSONFromRequest(request);

        try {
            //noinspection ConstantConditions
            login = jsonData.get("login");
            password = jsonData.get("password");
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        if (!isValidLogin(login) || !isValidPassword(password)) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            if (!isValidLogin(login)) {
                login = "";
            }
            if (!isValidPassword(password)) {
                password = "";
            }
        }

        if (status == HttpServletResponse.SC_OK) {
            if (request.getSession().getAttribute("login") != null) {
                login = "";
                status = HttpServletResponse.SC_FOUND;
            } else {
                UserProfile profile = accountService.getUser(login);

                if (profile != null && profile.getPassword().equals(password)) {
                    HttpSession currentSession = request.getSession(true);

                    currentSession.setAttribute("login", profile.getLogin());
                    accountService.addSessions(currentSession.getId(), profile);
                    email = profile.getEmail();
                } else {
                    password = "";
                    status = HttpServletResponse.SC_BAD_REQUEST;
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(setResponseDataUser(status, login, password, email));
    }
}
