package frontend;

import com.google.gson.Gson;
import main.accountService.AccountServiceImpl;
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
    private AccountServiceImpl accountService;

    public SignUpServlet(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
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
