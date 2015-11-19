package resource;

import java.io.Serializable;

/**
 * Created by said on 30.10.15.
 */
public class ServerSettings implements Serializable, Resource {
    private int port;

    public int getPort() {
        return port;
    }

    @Override
    public void setCorrectState() {

    }
}