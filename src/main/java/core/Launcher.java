package core;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.Database.Settings;
import utils.Constants;

public class Launcher {

    public static void main(String[] args) {
        Settings set = new Settings();
        set.loadSettings();

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(Constants.FPS);
        configuration.useVsync(Constants.vSyncOn);
        configuration.setTitle(Constants.gameName);


        if(Constants.isWindow)
            configuration.setWindowedMode(Constants.resolutionWidth, Constants.resolutionHeight);
        else
            configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        new Lwjgl3Application(new Boot(), configuration);
    }
}
