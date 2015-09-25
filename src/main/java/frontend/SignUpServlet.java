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
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class SignUpServlet extends HttpServlet {
    private AccountService accountService;

    public SignUpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;
        StringBuffer parametrsBuffer = new StringBuffer();
        String password = "";
        String login = "";
        String line = "";
        JSONObject jsonData = null;

        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                parametrsBuffer.append(line);
        } catch (IOException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;;
        }
        try {
            jsonData = (JSONObject) new JSONParser().parse(parametrsBuffer.toString());
        } catch (ParseException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;;
        }
        try {
            login = jsonData.get("login").toString();
            password = jsonData.get("password").toString();
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
        response.getWriter().println(PageGenerator.setResponseDataUser(status, login, password));
    }
}
