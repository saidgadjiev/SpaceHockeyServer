package resource;

import org.junit.Before;
import org.junit.Test;
import resource.sax.SettingsFileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by said on 30.10.15.
 */
public class ServerSettingsTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetPort() throws Exception {
        ServerSettings testSettings = new ServerSettings();
        int testPort = 8080;

        testSettings.loadSettingsFromFile("cfg/server.properties");

        assertEquals(testPort, testSettings.getPort());
    }

    @Test
    public void testGetHost() throws Exception {
        ServerSettings testSettings = new ServerSettings();
        String testHost = "127.0.0.1";

        testSettings.loadSettingsFromFile("cfg/server.properties");

        assertEquals(testHost, testSettings.getHost());
    }

    @Test
    public void testLoadSettingsFromFile() throws Exception {
        ServerSettings testSettings = new ServerSettings();
        boolean success;
        try {
            testSettings.loadSettingsFromFile("cfg/server.properties");
            success = true;
        } catch (SettingsFileNotFoundException e) {
            success = false;
        }

        assertTrue(success);
    }

    @Test
    public void testSettingsFileNotFound() throws Exception {
        ServerSettings testSettings = new ServerSettings();
        boolean success;
        try {
            testSettings.loadSettingsFromFile("test.properties");
            success = true;
        } catch (SettingsFileNotFoundException e) {
            success = false;
        }

        assertFalse(success);
    }
}