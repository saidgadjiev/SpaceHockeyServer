package templater;

import java.util.HashMap;

/**
 * @author v.chibrikov
 */
public class PageGenerator {
    public static HashMap<String, String> setResponseDataUser(int status, String login, String password)
    {
        HashMap<String, String> jsonData = new HashMap<>();

        jsonData.put("status", Integer.toString(status));
        jsonData.put("login", login);
        jsonData.put("password", password);

        return jsonData;
    }

    public static HashMap<String, String> setResponseDataAdmin(int status, int countUsers, int countOnlineUsers)
    {
        HashMap<String, String> jsonData = new HashMap<>();

        jsonData.put("status", Integer.toString(status));
        jsonData.put("countUsers", Integer.toString(countUsers));
        jsonData.put("countOnlineUsers", Integer.toString(countOnlineUsers));

        return jsonData;
    }
}
