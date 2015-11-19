package resource;

import java.io.Serializable;

/**
 * Created by said on 30.10.15.
 */
public class ServerSettings implements Serializable, Resource {
    private int port;
    private String host;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    @Override
    public void setCorrectState() {

    }
}