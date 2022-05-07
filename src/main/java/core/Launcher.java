package core;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.Controller.SettingsController;
import utils.Constants;
import utils.Config;


public class Launcher {

    public static void main(String[] args) {
        new SettingsController().loadSettings();

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(Config.FPS);
        configuration.useVsync(Config.vSyncOn);
        configuration.setTitle(Constants.gameName);

        if(Config.isWindow)
            configuration.setWindowedMode(Config.resolutionWidth, Config.resolutionHeight);
        else
            configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

        new Lwjgl3Application(new Boot(), configuration);
    }
}
