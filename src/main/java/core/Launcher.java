package core;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Launcher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setIdleFPS(60);
        configuration.useVsync(true);
        configuration.setTitle("Hero Game ⚡");
        if(true) {
            configuration.setWindowedMode(1920, 1080);
        } else {
            configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        }

        new Lwjgl3Application(new Boot(), configuration);
    }
}
