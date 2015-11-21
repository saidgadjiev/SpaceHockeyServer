package frontend;

import com.google.gson.JsonObject;
import main.accountService.AccountService;
import main.user.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.user.Validator.*;
import static templater.PageGenerator.setResponseDataUser;
import static utils.JSONFromRequest.getJSONFromRequest;

/**
  Created by g.said on 13.09.2014.
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
        JsonObject jsonObject = getJSONFromRequest(request);
        String login = "";
        String password = "";
        String email = "";

        try {
            //noinspection ConstantConditions
            login = jsonObject.get("login").getAsString();
            password = jsonObject.get("password").getAsString();
            email = jsonObject.get("email").getAsString();
        } catch (NullPointerException e) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        if (!isValidLogin(login) || !isValidPassword(password) || !isValidEmail(email)) {
            status = HttpServletResponse.SC_BAD_REQUEST;
            if (!isValidLogin(login)) {
                login = "";
            }
            if (!isValidPassword(password)) {
                password = "";
            }
            if (!isValidEmail(email)) {
                email = "";
            }
        }

        if (status == HttpServletResponse.SC_OK) {
            if (!accountService.addUser(login, new UserProfile(login, password, email))) {
                status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(setResponseDataUser(status, login, password, email));
    }
}
