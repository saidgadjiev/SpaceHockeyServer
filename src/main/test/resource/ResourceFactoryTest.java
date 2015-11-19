package resource;

import org.junit.Before;
import org.junit.Test;
import resource.sax.SettingsFileNotFoundException;

import static junit.framework.Assert.assertEquals;

/**
  Created by said on 10.11.15.
 */

public class ResourceFactoryTest {
    private ResourceFactory resourceFactory;

    @Before
    public void setUp() throws Exception {
        resourceFactory = ResourceFactory.getInstance();
    }


    @Test
    public void testLoadResource() throws Exception {
        ServerSettings serverSettings = (ServerSettings) resourceFactory.loadResource("cfg/server.properties");
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) resourceFactory.loadResource("data/gameMechanicsSettings.xml");

        assertEquals(8080, serverSettings.getPort());
        assertEquals(10000, gameMechanicsSettings.getGameTime());
        assertEquals(1000, gameMechanicsSettings.getStepTime());
    }

    @Test
    public void testResourceNotFound() {
        Resource resource;

        try {
            resource = resourceFactory.loadResource("testResource/test.xml");
        } catch (SettingsFileNotFoundException e) {
            resource = null;
        }

        assertEquals(null, resource);
    }

    @Test
    public void testLoadAllResources() throws Exception {
        resourceFactory.loadAllResources("data");
        resourceFactory.loadAllResources("cfg");

        ServerSettings serverSettings = (ServerSettings) resourceFactory.loadResource("cfg/server.properties");
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) resourceFactory.loadResource("data/gameMechanicsSettings.xml");

        assertEquals(8080, serverSettings.getPort());
        assertEquals(10000, gameMechanicsSettings.getGameTime());
        assertEquals(1000, gameMechanicsSettings.getStepTime());
    }
}