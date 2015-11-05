package resource.sax;

/**
 * Created by said on 30.10.15.
 */
public class SettingsFileNotFoundException extends RuntimeException {
    public SettingsFileNotFoundException(Exception e) {
        super(e);
    }
}
