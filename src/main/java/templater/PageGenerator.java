package templater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.user.UserProfile;

import java.util.List;

/**
 * @author said
 */
public class PageGenerator {

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

    public static String setResponseDataAdmin(int status, long countUsers, long countOnlineUsers)
    {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonBody = new JsonObject();

        jsonObject.addProperty("status", Integer.toString(status));
        jsonBody.addProperty("countUsers", Long.toString(countUsers));
        jsonBody.addProperty("countOnlineUsers", Long.toString(countOnlineUsers));
        jsonObject.add("body", jsonBody);

        return jsonObject.toString();
    }

    public static String setResponseFromList(int status, List<UserProfile> userProfileList) {

        JsonObject jsonObject = new JsonObject();
        JsonObject jsonBody = new JsonObject();
        JsonArray scoreList = new JsonArray();

        jsonObject.addProperty("status", Integer.toString(status));

        for (UserProfile profile: userProfileList) {
            JsonObject scoreItem = new JsonObject();
            scoreItem.addProperty("login", profile.getLogin());
            scoreItem.addProperty("score", Integer.toString(profile.getScore()));
            scoreList.add(scoreItem);
            System.out.print(scoreList.toString());
        }
        jsonBody.add("scoreList", scoreList);
        jsonObject.add("body", jsonBody);

        return jsonObject.toString();
    }
}
