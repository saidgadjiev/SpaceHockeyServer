package frontend;

import main.accountService.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static templater.PageGenerator.setResponseFromList;

/**
 * Created by said on 26.11.15.
 */
public class ScoreServlet extends HttpServlet {
    private AccountService accountService;

    public ScoreServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        int status = HttpServletResponse.SC_OK;
        String limitFromRequest = request.getParameter("limit");
        int limit = 0;

        if (limitFromRequest == null) {
            status = HttpServletResponse.SC_BAD_REQUEST;
        } else {
            try {
                limit = parseInt(limitFromRequest);
            } catch (Exception ex) {
                status = HttpServletResponse.SC_BAD_REQUEST;
                limit = 0;
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(setResponseFromList(status, accountService.getUsersByScore(limit)));
    }
}
