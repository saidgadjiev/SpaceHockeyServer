package templater;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * @author said
 */
public class PageGenerator {
    public static String setResponseDataUser(int status, String login, String password, String email)
    {
        HashMap<String, String> jsonData = new HashMap<>();
        Gson gson = new Gson();

        jsonData.put("status", Integer.toString(status));
        jsonData.put("login", login);
        jsonData.put("password", password);
        jsonData.put("email", email);

        return gson.toJson(jsonData);
    }

    public static String setResponseDataAdmin(int status, int countUsers, int countOnlineUsers)
    {
        HashMap<String, String> jsonData = new HashMap<>();
        Gson gson = new Gson();

        jsonData.put("status", Integer.toString(status));
        jsonData.put("countUsers", Integer.toString(countUsers));
        jsonData.put("countOnlineUsers", Integer.toString(countOnlineUsers));

        return gson.toJson(jsonData);
    }
}
