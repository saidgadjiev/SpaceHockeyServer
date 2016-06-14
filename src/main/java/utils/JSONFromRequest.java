package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by said on 13.10.15.
 */
public class JSONFromRequest {

    @Nullable
    public static JsonObject getJSONFromRequest(HttpServletRequest request) {
        @SuppressWarnings("StringBufferMayBeStringBuilder") StringBuffer parametersBuffer = new StringBuffer();

        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                parametersBuffer.append(line);
        } catch (IOException e) {
            return null;
        }

        JsonObject jsonObject;
        try {
            jsonObject = new Gson().fromJson(parametersBuffer.toString(), JsonObject.class);
        } catch (JsonSyntaxException e) {
            return null;
        }

        return jsonObject;
    }
}
