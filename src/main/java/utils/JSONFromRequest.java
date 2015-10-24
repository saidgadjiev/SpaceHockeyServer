package utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by said on 13.10.15.
 */
public class JSONFromRequest {

    @Nullable
    public static HashMap<String, String> getJSONFromRequest(HttpServletRequest request) {
        @SuppressWarnings({"StringBufferMayBeStringBuilder", "MismatchedQueryAndUpdateOfStringBuilder"}) StringBuffer parametersBuffer = new StringBuffer();
        Gson gson = new Gson();

        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                parametersBuffer.append(line);
        } catch (IOException e) {
            return null;
        }

        HashMap<String, String> jsonData;
        try {
            @SuppressWarnings("AnonymousInnerClassMayBeStatic") Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            jsonData = gson.fromJson(parametersBuffer.toString(), type);
        } catch (JsonSyntaxException e) {
            return null;
        }

        return jsonData;
    }

    static public JSONObject getJsonFromString(String request) {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(request);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (JSONObject) obj;
    }
}
