package main;

import admin.AdminPageServlet;
import dbService.DBService;
import dbService.DBServiceImpl;
import frontend.*;
import frontend.game.WebSocketGameServlet;
import frontend.transport.TransportSystem;
import gameMechanics.GameMechanicsImpl;
import main.accountService.AccountService;
import main.accountService.AccountServiceMySQLImpl;
import main.gameService.GameMechanics;
import main.user.UserProfile;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import resource.DBServerSettings;
import resource.GameMechanicsSettings;
import resource.ResourceFactory;
import resource.ServerSettings;

import javax.servlet.Servlet;

/**
 * @author S. Gadjiev
 */

public class Main {

    @SuppressWarnings({"OverlyBroadThrowsClause", "SpellCheckingInspection"})
    public static void main(String[] args) throws Exception {

        ResourceFactory resourceFactory = ResourceFactory.getInstance();
        resourceFactory.loadAllResources("cfg");
        resourceFactory.loadAllResources("data");

        ServerSettings serverSettings = (ServerSettings) resourceFactory.loadResource("cfg/server.properties");
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) resourceFactory.loadResource("data/gameMechanicsSettings.xml");
        DBServerSettings dbServerSettings = (DBServerSettings) resourceFactory.loadResource("data/dbServerSettings.xml");

        DBService dbService = new DBServiceImpl(dbServerSettings);

        AccountService accountService = new AccountServiceMySQLImpl(dbService);

        TransportSystem transportSystem = new TransportSystem();
        GameMechanics gameMechanics = new GameMechanicsImpl(accountService, transportSystem, gameMechanicsSettings);

        Servlet signin = new SignInServlet(accountService);
        Servlet signUp = new SignUpServlet(accountService);
        Servlet signOut = new SignOutServlet(accountService);
        Servlet admin = new AdminPageServlet(accountService);
        Servlet score = new ScoreServlet(accountService);
        Servlet profile = new ProfileServlet(accountService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(signin), "/auth/signin");
        context.addServlet(new ServletHolder(signUp), "/auth/signup");
        context.addServlet(new ServletHolder(signOut), "/auth/signout");
        context.addServlet(new ServletHolder(admin), "/admin");
        context.addServlet(new ServletHolder(score), "/score");
        context.addServlet(new ServletHolder(profile), "/profile");
        context.addServlet(new ServletHolder(new WebSocketGameServlet(accountService, transportSystem, gameMechanics)), "/gameplay");

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