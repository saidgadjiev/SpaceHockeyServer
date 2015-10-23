package frontend.game;

import main.accountService.AccountService;
import main.gameService.GameMechanics;
import main.user.UserProfile;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 */
public class GameServlet extends HttpServlet {

    private GameMechanics gameMechanics;
    private AccountService accountService;

    public GameServlet(GameMechanics gameMechanics, AccountService accountService) {
        this.gameMechanics = gameMechanics;
        this.accountService = accountService;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        String name = request.getParameter("name");
        String safeName = name == null ? "NoName" : name;
        UserProfile profile = new UserProfile(name, "", "");
        accountService.addUser(name, profile);
        accountService.addSessions(request.getSession().getId(), profile);
        pageVariables.put("myName", safeName);

        response.getWriter().println(PageGenerator.getPage("game.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
