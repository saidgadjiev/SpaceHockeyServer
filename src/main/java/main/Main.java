package main;

import DBService.DBService;
import DBService.DBServiceImpl;
import main.user.UserProfile;

/**
 * @author S. Gadjiev
 */

public class Main {

    @SuppressWarnings({"OverlyBroadThrowsClause", "SpellCheckingInspection"})
    public static void main(String[] args) throws Exception {
        hibernateTest();

        /*ResourceFactory resourceFactory = ResourceFactory.getInstance();
        resourceFactory.loadAllResources("cfg");
        resourceFactory.loadAllResources("data");

        ServerSettings serverSettings = (ServerSettings) resourceFactory.loadResource("cfg/server.properties");
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) resourceFactory.loadResource("data/gameMechanicsSettings.xml");

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

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(serverSettings.getPort());
        server.setHandler(handlers);

        server.start();

        gameMechanics.run();*/

    }

    public static void hibernateTest() {
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        dbService.save(new UserProfile("said", "said1995", "said@mail.ru"));
        dbService.save(new UserProfile("igor", "igor1992", "igor@mail.ru"));

        //UserProfile dataSet = dbService.readByName("said");
        //System.out.println(dataSet);

        //List<UserDataSet> dataSets = dbService.readAll();
        //dataSets.forEach(System.out::println);

        dbService.shutdown();
    }
}