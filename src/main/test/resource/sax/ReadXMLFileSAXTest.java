package resource.sax;

import org.junit.Test;
import resource.GameMechanicsSettings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by said on 30.10.15.
 */
public class ReadXMLFileSAXTest {

    @Test
    public void testReadXML() throws Exception {
        GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) ReadXMLFileSAX.readXML("data/gameMechanicsSettings.xml");

        //noinspection ConstantConditions
        assertEquals(5000, gameMechanicsSettings.getGameTime());
    }

    @Test
    public void testSettingsFileNotFound() {
        boolean success = true;
        try {
            GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) ReadXMLFileSAX.readXML("testSettings.xml");
            success = true;
        } catch (SettingsFileNotFoundException e) {
            success = false;
        }

        assertFalse(success);
    }
}