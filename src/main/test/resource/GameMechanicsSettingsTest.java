package resource;

import org.junit.Test;
import resource.sax.ReadXMLFileSAX;
import resource.sax.SettingsFileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by said on 31.10.15.
 */
public class GameMechanicsSettingsTest {

    @Test
    public void testGetGameTime() throws Exception {
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) ReadXMLFileSAX.readXML("data/gameMechanicsSettings.xml");

        //noinspection ConstantConditions
        assertEquals(10000, gameMechanicsSettings.getGameTime());
    }

    @Test
    public void testGetStepTime() throws Exception {
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) ReadXMLFileSAX.readXML("data/gameMechanicsSettings.xml");

        //noinspection ConstantConditions
        assertEquals(100, gameMechanicsSettings.getStepTime());
    }

    @Test
    public void testLoadSettingsFromFile() throws Exception {

        GameMechanicsSettings gameMechanicsSettings = new GameMechanicsSettings();
        gameMechanicsSettings.loadSettingsFromFile("data/gameMechanicsSettings.xml");

        //noinspection ConstantConditions
        assertEquals(100, gameMechanicsSettings.getStepTime());
        assertEquals(10000, gameMechanicsSettings.getGameTime());
    }

    @Test
    public void testSettingsFileNotFound() throws Exception {
        GameMechanicsSettings gameMechanicsSettings = new GameMechanicsSettings();
        boolean success;

        try {
            gameMechanicsSettings.loadSettingsFromFile("gameMechanicsSettings.xml");
            success = true;
        } catch (SettingsFileNotFoundException e) {
            success = false;
        }

        assertFalse(success);
        assertEquals(100, gameMechanicsSettings.getStepTime());
        assertEquals(1000, gameMechanicsSettings.getGameTime());
    }

}