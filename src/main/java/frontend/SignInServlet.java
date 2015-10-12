package frontend;

import com.google.gson.Gson;
import main.AccountService;
import main.UserProfile;
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
    private AccountService accountService;

    public SignInServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String login =  request.getParameter("login");
        String password = request.getParameter("password");
        int status = HttpServletResponse.SC_OK;
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
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        /*int status = HttpServletResponse.SC_OK;
        @SuppressWarnings("StringBufferMayBeStringBuilder") StringBuffer parametersBuffer = new StringBuffer();
        Gson gson = new Gson();

        try {
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null)
                parametersBuffer.append(line);
        } catch (IOException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        HashMap<String, String> jsonData = null;

        try {
            @SuppressWarnings("AnonymousInnerClassMayBeStatic") Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            jsonData = gson.fromJson(parametersBuffer.toString(), type);
        } catch (JsonSyntaxException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        String login = "";
        String password = "";
        try {
            //noinspection ConstantConditions
            login = jsonData.get("login");
            password = jsonData.get("password");
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }*/
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        int status = HttpServletResponse.SC_OK;
        Gson gson = new Gson();

        if (login == null || password == null) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            login = "";
            password = "";
        }
        System.out.print(login + password);
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
