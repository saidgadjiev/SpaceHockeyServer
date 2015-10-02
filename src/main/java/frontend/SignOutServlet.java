package frontend;

import com.google.gson.Gson;
import main.AccountService;
import org.jetbrains.annotations.NotNull;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by said on 21.09.15.
 */
public class SignOutServlet extends HttpServlet {
    private AccountService accountService;

    public SignOutServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(@NotNull HttpServletRequest request,
                      @NotNull HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;

        if (request.getSession().getAttribute("login") != null) {
            if (accountService != null) {
                accountService.deleteSession(request.getSession().getId());
            }
            request.getSession().invalidate();
        } else {
            status = HttpServletResponse.SC_UNAUTHORIZED;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        //noinspection ConstantConditions
        response.getWriter().println(new Gson().toJson(PageGenerator.setResponseDataUser(status, "", "")));
    }
}
