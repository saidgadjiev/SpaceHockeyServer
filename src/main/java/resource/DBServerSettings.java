package resource;

import java.io.Serializable;

/**
 * Created by said on 26.11.15.
 */
public class DBServerSettings implements Serializable, Resource {
    private String dialect;
    private String driverClass;
    private String connectionUrl;
    private String username;
    private String password;
    private String showSql;
    private String mode;

    public DBServerSettings() {
    }

    public String getDialect() {
        return dialect;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getShowSql() {
        return showSql;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public void setCorrectState() {
    }
}
