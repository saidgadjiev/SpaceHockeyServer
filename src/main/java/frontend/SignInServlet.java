package frontend;

import com.google.gson.JsonObject;
import main.accountService.AccountService;
import main.user.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static main.user.Validator.isValidLogin;
import static main.user.Validator.isValidPassword;
import static templater.PageGenerator.setResponseDataUser;
import static utils.JSONFromRequest.getJSONFromRequest;

/**
 * @author v.chibrikov
 */
public class SignInServlet extends HttpServlet {
    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;
        String login = "";
        String password = "";
        String email = "";
        JsonObject jsonObject = getJSONFromRequest(request);

        try {
            //noinspection ConstantConditions
            login = jsonObject.get("login").getAsString();
            password = jsonObject.get("password").getAsString();
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
