package resource.properties;

import resource.reflection.ReflectionHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by said on 10.11.15.
 */
public class ReadPropertiesFile {
    private Object object = null;

    public void parse(String filename) {
        try (final FileInputStream settingsFile = new FileInputStream(filename)) {
            final Properties properties = new Properties();

            properties.load(settingsFile);

            object = ReflectionHelper.createInstance(properties.getProperty("class"));
            ReflectionHelper.setFieldValue(object, "port", properties.getProperty("port"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getObject() {
        return object;
    }
}
