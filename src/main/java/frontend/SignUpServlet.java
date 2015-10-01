package frontend;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import main.AccountService;
import main.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

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
        int status = HttpServletResponse.SC_OK;
        Gson gson = new Gson();

        if (!accountService.addUser(login, new UserProfile(login, password, ""))) {
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
        int status = HttpServletResponse.SC_OK;
        StringBuffer parametrsBuffer = new StringBuffer();
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
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            jsonData = gson.fromJson(parametrsBuffer.toString(), type);
        } catch (JsonSyntaxException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }
        String password = "";
        String login = "";

        try {
            login = jsonData.get("login");
            password = jsonData.get("password");
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        if (status == HttpServletResponse.SC_OK) {
            if (!accountService.addUser(login, new UserProfile(login, password, ""))) {
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                login = "";
                password = "";
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(gson.toJson(PageGenerator.setResponseDataUser(status, login, password)));
    }
}
