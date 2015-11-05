package main;

import admin.AdminPageServlet;
import frontend.SignInServlet;
import frontend.SignOutServlet;
import frontend.SignUpServlet;
import frontend.game.GameServlet;
import frontend.game.WebSocketGameServlet;
import frontend.game.WebSocketServiceImpl;
import gameMechanics.GameMechanicsImpl;
import main.accountService.AccountService;
import main.accountService.AccountServiceImpl;
import main.gameService.GameMechanics;
import main.gameService.WebSocketService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resource.GameMechanicsSettings;
import resource.ServerSettings;

import javax.servlet.Servlet;

/**
 * @author S. Gadjiev
 */
public class Main {

    @SuppressWarnings({"OverlyBroadThrowsClause", "SpellCheckingInspection"})
    public static void main(String[] args) throws Exception {

        ServerSettings serverSettings = new ServerSettings();
        serverSettings.loadSettingsFromFile("cfg/server.properties");

        GameMechanicsSettings gameMechanicsSettings = new GameMechanicsSettings();
        gameMechanicsSettings.loadSettingsFromFile("data/gameMechanicsSettings.xml");

        AccountService accountService = new AccountServiceImpl();

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService, gameMechanicsSettings);

        Servlet signin = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet signOut = new SignOutServlet(accountService);
        Servlet admin = new AdminPageServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signin), "/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/auth/signup");
        context.addServlet(new ServletHolder(signOut), "/auth/signout");
        context.addServlet(new ServletHolder(admin), "/admin");
        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, gameMechanics, webSocketService)), "/gameplay");
        context.addServlet(new ServletHolder(new GameServlet(accountService)), "/game.html");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(serverSettings.getPort());
        server.setHandler(handlers);

        server.start();

        gameMechanics.run();

    }
}