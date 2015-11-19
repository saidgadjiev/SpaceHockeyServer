package templater;

import com.google.gson.JsonObject;
import freemarker.template.Configuration;

/**
 * @author said
 */
public class PageGenerator {

    private static final String HTML_DIR = "templates";
    private static final Configuration CFG = new Configuration();

    public static String setResponseDataUser(int status, String login, String password, String email)
    {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonBody = new JsonObject();

        jsonObject.addProperty("status", Integer.toString(status));
        jsonBody.addProperty("login", login);
        jsonBody.addProperty("password", password);
        jsonBody.addProperty("email", email);
        jsonObject.add("body", jsonBody);

        return jsonObject.toString();
    }

    public static String setResponseDataAdmin(int status, int countUsers, int countOnlineUsers)
    {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonBody = new JsonObject();

        jsonObject.addProperty("status", Integer.toString(status));
        jsonBody.addProperty("countUsers", Integer.toString(countUsers));
        jsonBody.addProperty("countOnlineUsers", Integer.toString(countOnlineUsers));
        jsonObject.add("body", jsonBody);

        return jsonObject.toString();
    }
}
