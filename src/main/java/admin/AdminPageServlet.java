package admin;

import main.AccountService;
import main.TimeHelper;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by said on 23.09.15.
 */
public class AdminPageServlet extends HttpServlet {
    private AccountService accountService;

    public AdminPageServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String timeShutdown = request.getParameter("shutdown");
        int status = HttpServletResponse.SC_OK;

        if (timeShutdown != null) {
            int timeMS = Integer.valueOf(timeShutdown);

            System.out.print("Server will be down after: "+ timeMS + " ms");
            TimeHelper.sleep(timeMS);
            System.out.print("\nShutdown");
            System.exit(0);
        } else {
            status = HttpServletResponse.SC_BAD_REQUEST;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(PageGenerator.setResponseDataAdmin(status,
                    accountService.getCountUsers(), accountService.getCountOnlineUsers()));
    }
}
