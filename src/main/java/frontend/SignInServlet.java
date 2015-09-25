package frontend;

import main.AccountService;
import main.UserProfile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.BufferedReader;

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
        String line = "";
        StringBuffer parametrsBuffer = new StringBuffer();
        JSONObject jsonData = null;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                parametrsBuffer.append(line);
        } catch (IOException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        try {
            jsonData = (JSONObject) new JSONParser().parse(parametrsBuffer.toString());
        } catch (ParseException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        try {
            login = jsonData.get("login").toString();
            password = jsonData.get("password").toString();
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
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
        response.getWriter().println(PageGenerator.setResponseDataUser(status, login, password));
    }
}
