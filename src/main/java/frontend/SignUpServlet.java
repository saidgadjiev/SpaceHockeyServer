package frontend;

import com.google.gson.Gson;
import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by g.said on 13.09.2014.
 */
public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Gson gson = new Gson();
        int status = HttpServletResponse.SC_OK;

        if (login == null || password == null) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            login = "";
            password = "";
        }
        if (!accountService.addUser(login, new UserProfile(login, password, email))) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            login = "";
            password = "";
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(gson.toJson(PageGenerator.setResponseDataUser(status, login, password)));
    }
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
       /* int status = HttpServletResponse.SC_OK;
        @SuppressWarnings("StringBufferMayBeStringBuilder") StringBuffer parametrsBuffer = new StringBuffer();
        Gson gson = new Gson();

        try {
            BufferedReader reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null)
                parametrsBuffer.append(line);
        } catch (IOException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        HashMap<String, String> jsonData = null;

        try {
            @SuppressWarnings("AnonymousInnerClassMayBeStatic") Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            jsonData = gson.fromJson(parametrsBuffer.toString(), type);
        } catch (JsonSyntaxException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        String password = "";
        String login = "";

        try {
            //noinspection ConstantConditions
            login = jsonData.get("login");
            password = jsonData.get("password");
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
*/
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Gson gson = new Gson();
        int status = HttpServletResponse.SC_OK;

        if (login == null || password == null || email == null) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            login = "";
            password = "";
        }
        if (status == HttpServletResponse.SC_OK) {
            if (!accountService.addUser(login, new UserProfile(login, password, email))) {
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                login = "";
                password = "";
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(gson.toJson(PageGenerator.setResponseDataUser(status, login, password)));
    }
}
