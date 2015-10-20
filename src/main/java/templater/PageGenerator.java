package templater;

import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author said
 */
public class PageGenerator {

    private static final String HTML_DIR = "templates";
    private static final Configuration CFG = new Configuration();

    public static String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(HTML_DIR + File.separator + filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

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
