package resource;

import resource.sax.ReadXMLFileSAX;
import resource.sax.SettingsFileNotFoundException;

import java.io.Serializable;

/**
 * Created by said on 30.10.15.
 */
public class GameMechanicsSettings implements Serializable, Resource {
    private int gameTime;
    private int stepTime;

    public int getGameTime() {
        return gameTime;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void loadSettingsFromFile(String settingsFilePath) {
        try {
            GameMechanicsSettings gameMechanicsSettings = (GameMechanicsSettings) ReadXMLFileSAX.readXML(settingsFilePath);
            //noinspection ConstantConditions
            this.stepTime = gameMechanicsSettings.stepTime;
            this.gameTime = gameMechanicsSettings.gameTime;
        } catch (SettingsFileNotFoundException e){
            this.stepTime = 100;
            this.gameTime = 1000;
            throw e;
        }
    }

    @Override
    public void setCorrectState() {

    }
}
