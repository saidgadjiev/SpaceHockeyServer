package frontend;

import com.google.gson.Gson;
import main.UserProfile;
import main.accountService.AccountServiceImpl;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Gson gson = new Gson();

        if (login == null || password == null) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            login = "";
            password = "";
        }
        if (status == HttpServletResponse.SC_OK) {
            if (request.getSession().getAttribute("login") != null) {
                login = "";
                password = "";
                status = HttpServletResponse.SC_FOUND;
            } else {
                UserProfile profile = accountService.getUser(login);

                if (profile != null && profile.getPassword().equals(password)) {
                    HttpSession currentSession = request.getSession(true);

                    currentSession.setAttribute("login", profile.getLogin());
                    accountService.addSessions(currentSession.getId(), profile);
                } else {
                    login = "";
                    password = "";
                    status = HttpServletResponse.SC_BAD_REQUEST;
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(gson.toJson(PageGenerator.setResponseDataUser(status, login, password)));
    }
}
