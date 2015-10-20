package main;

/**
 * @author S. Gadjiev
 */
/*public class Main {
    public static final int DEFAULT_PORT = 8080;

    @SuppressWarnings("OverlyBroadThrowsClause")
    public static void main(String[] args) throws Exception {
        int port = DEFAULT_PORT;
        if (args.length == 1) {
            String portString = args[0];
            port = Integer.valueOf(portString);
        }

        System.out.append("Starting at port: ").append(String.valueOf(port)).append('\n');

        AccountServiceImpl accountService = new AccountServiceImpl();

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);

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
        context.addServlet(new ServletHolder(new GameServlet(gameMechanics, accountService)), "/game.html");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(port);
        server.setHandler(handlers);

        server.start();
        server.join();

        gameMechanics.run();

    }
}*/

import frontend.game.AuthServiceImpl;
import frontend.game.GameServlet;
import frontend.game.WebSocketGameServlet;
import frontend.game.WebSocketServiceImpl;
import gameMechanics.GameMechanicsImpl;
import main.gameService.AuthService;
import main.gameService.GameMechanics;
import main.gameService.WebSocketService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        WebSocketService webSocketService = new WebSocketServiceImpl();
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService);
        AuthService authService = new AuthServiceImpl();

        context.addServlet(new ServletHolder(new WebSocketGameServlet(authService, gameMechanics, webSocketService)), "/gameplay");
        context.addServlet(new ServletHolder(new GameServlet(gameMechanics, authService)), "/game.html");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.setHandler(handlers);

        server.start();

        //run GM in main thread
        gameMechanics.run();
    }
}