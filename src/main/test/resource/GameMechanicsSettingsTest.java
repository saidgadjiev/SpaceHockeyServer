package resource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by said on 31.10.15.
 */
public class GameMechanicsSettingsTest {
    private GameMechanicsSettings testSettings;

    @Before
    public void setUp() throws Exception {
        ResourceFactory resourceFactory = ResourceFactory.getInstance();
        testSettings = (GameMechanicsSettings) resourceFactory.loadResource("data/testSettings.xml");
    }

    @Test
    public void testGetGameTime() throws Exception {
        //noinspection ConstantConditions
        assertEquals(10000, testSettings.getGameTime());
    }

    @Test
    public void testGetStepTime() throws Exception {

        //noinspection ConstantConditions
        assertEquals(1000, testSettings.getStepTime());
    }

}