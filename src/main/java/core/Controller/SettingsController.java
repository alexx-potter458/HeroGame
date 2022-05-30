package core.Controller;

import core.Database.SettingsDatabase;
import utils.Config;
import utils.Constants;

public class SettingsController {
    public void loadSettings() {
        new SettingsDatabase().loadSettings();
        new UserController().loadUser();

        this.changeBackground();
    }

    private void changeBackground() {
        if(Config.time.equals(Constants.dayValue))
            Config.bgColor = Config.bgDayColor;
         else
            Config.bgColor = Config.bgNightColor;
    }

    public void toggleDisplayMode() {
        SettingsDatabase settings = new SettingsDatabase();

        if(Config.isWindow){
            settings.toggleDisplayMode(0);
        } else {
            settings.toggleDisplayMode(1);
        }

        Config.isWindow = !Config.isWindow;
    }

    public void toggleTime() {
        SettingsDatabase settings = new SettingsDatabase();

        if(Config.time.equals(Constants.dayValue)) {
            settings.toggleTime(Constants.nightValue);
            Config.time = Constants.nightValue;
        } else {
            settings.toggleTime(Constants.dayValue);
            Config.time = Constants.dayValue;
        }

        this.changeBackground();
    }

    public void setMenuMusic() {
        new SettingsDatabase().setMenuMusic(Config.menuMusic);
    }

    public void setInGameMusic() {
        new SettingsDatabase().setInGameMusic(Config.inGameMusic);
    }

    public void setInGameSound() {
        new SettingsDatabase().setInGameSound(Config.inGameSound);
    }

}