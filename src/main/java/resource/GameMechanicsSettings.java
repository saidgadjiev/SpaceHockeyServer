package resource;

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

    @Override
    public void setCorrectState() {

    }
}
