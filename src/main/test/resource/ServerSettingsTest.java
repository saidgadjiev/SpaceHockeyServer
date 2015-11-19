package resource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
  Created by said on 30.10.15.
 */

public class ServerSettingsTest {
    private ServerSettings testSettings;

    @Before
    public void setUp() throws Exception {
        ResourceFactory resourceFactory = ResourceFactory.getInstance();
        testSettings = (ServerSettings) resourceFactory.loadResource("cfg/server.properties");
    }

    @Test
    public void testGetPort() throws Exception {
        int testPort = 8080;

        assertEquals(testPort, testSettings.getPort());
    }

}