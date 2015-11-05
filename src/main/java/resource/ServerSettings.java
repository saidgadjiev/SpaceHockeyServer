package resource;

import resource.sax.SettingsFileNotFoundException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by said on 30.10.15.
 */
public class ServerSettings {
    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public void loadSettingsFromFile(String settingsFilePath) {
        try (final FileInputStream settingsFile = new FileInputStream(settingsFilePath)) {
            final Properties properties = new Properties();
            properties.load(settingsFile);

            host = properties.getProperty("host");
            port = Integer.valueOf(properties.getProperty("port"));
        } catch (IOException e) {
            throw new SettingsFileNotFoundException(e);
        }
    }
}
