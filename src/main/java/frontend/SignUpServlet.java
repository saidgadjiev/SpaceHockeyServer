package frontend;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import main.AccountService;
import main.UserProfile;
import org.jetbrains.annotations.NotNull;
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
    @NotNull private AccountService accountService;

    public SignUpServlet(@NotNull AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Gson gson = new Gson();

        assert login != null;
        int status = HttpServletResponse.SC_OK;
        if (!accountService.addUser(login, new UserProfile(login, password, ""))) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            login = "";
            password = "";
        }

        response.setStatus(HttpServletResponse.SC_OK);
        //noinspection ConstantConditions
        response.getWriter().println(gson.toJson(PageGenerator.setResponseDataUser(status, login, password)));
    }
    @Override
    public void doPost(@NotNull HttpServletRequest request,
                       @NotNull HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;
        @SuppressWarnings("StringBufferMayBeStringBuilder") StringBuffer parametrsBuffer = new StringBuffer();
        Gson gson = new Gson();

        try {
            BufferedReader reader = request.getReader();

            assert reader != null;
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
            assert jsonData != null;
            login = jsonData.get("login");
            password = jsonData.get("password");
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        if (status == HttpServletResponse.SC_OK) {
            assert login != null;
            if (!accountService.addUser(login, new UserProfile(login, password, ""))) {
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                login = "";
                password = "";
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        //noinspection ConstantConditions
        response.getWriter().println(gson.toJson(PageGenerator.setResponseDataUser(status, login, password)));
    }
}
