package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.SettingsController;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import utils.Config;
import utils.Constants;

public class SettingsScreen extends Screen {
    private final ButtonObject          backButtonObject;
    private final TextBoxObject         dayNightText;
    private final TextBoxObject         inGameMusicText;
    private final TextBoxObject         inGameSoundText;
    private final TextBoxObject         menuMusicText;
    private final ButtonObject          dayNightButtonObject;
    private final ButtonObject          inGameMusicButtonObject;
    private final ButtonObject          inGameSoundButtonObject;
    private final ButtonObject          menuMusicButtonObject;
    private final SettingsController    settingsController;
    private final ButtonObject          screenModeButtonObject;
    private final TextBoxObject         screenModeText;
    private final TextBoxObject         pageTitle;

    public SettingsScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");

        this.pageTitle               = new TextBoxObject(Constants.settingsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject        = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.dayNightText            = new TextBoxObject(Constants.timeLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 144, 'm');
        this.screenModeText          = new TextBoxObject(Constants.displayModeLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 64, 'm');
        this.inGameMusicText         = new TextBoxObject("In game music", (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) - 20, 'm');
        this.inGameSoundText         = new TextBoxObject("In game sound", (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) - 104, 'm');
        this.menuMusicText           = new TextBoxObject("Menu music", (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) - 194, 'm');
        this.dayNightButtonObject    = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 144, this.getTimeLabel());
        this.screenModeButtonObject  = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 64, this.getDisplayLabel());
        this.inGameMusicButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) - 20, (Config.inGameMusic > 0)? "On" : "Off");
        this.inGameSoundButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) - 104, (Config.inGameSound > 0)? "On" : "Off");
        this.menuMusicButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) - 194, (Config.menuMusic > 0)? "On" : "Off");
        this.settingsController      = new SettingsController();
    }

    @Override
    protected void update() {
        super.update();

        this.pageTitle.update();
        this.backButtonObject.update();
        this.dayNightButtonObject.update();
        this.screenModeText.update();
        this.screenModeButtonObject.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.pageTitle.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.dayNightText.render(this.batch);
        this.dayNightButtonObject.render(this.batch);
        this.screenModeButtonObject.render(this.batch);
        this.screenModeText.render(this.batch);
        this.inGameMusicButtonObject.render(this.batch);
        this.inGameSoundButtonObject.render(this.batch);
        this.menuMusicButtonObject.render(this.batch);
        this.menuMusicText.render(this.batch);
        this.inGameMusicText.render(this.batch);
        this.inGameSoundText.render(this.batch);
        this.batch.end();
    }

    private String getTimeLabel() {
        if(Config.time.equals(Constants.dayValue))
            return Constants.dayLabel;
        else return Constants.nightLabel;
    }

    private String getDisplayLabel() {
        if(Config.isWindow)
            return Constants.windowedLabel;
        else return Constants.fullscreenLabel;
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));

        if(this.dayNightButtonObject.isJustPressed()) {
            if(Config.time.equals(Constants.dayValue)) {
                this.dayNightButtonObject.changeText(Constants.nightLabel);
            } else {
                this.dayNightButtonObject.changeText(Constants.dayLabel);
            }

            this.settingsController.toggleTime();
        }

        if(this.screenModeButtonObject.isJustPressed()) {
            this.settingsController.toggleDisplayMode();

            if(Config.isWindow) {
                this.screenModeButtonObject.changeText(Constants.windowedLabel);
            } else {
                this.screenModeButtonObject.changeText(Constants.fullscreenLabel);
            }
        }

        if(this.menuMusicButtonObject.isJustPressed()) {
            if(Config.menuMusic > 0f) {
                Config.menuMusic = 0f;
                Boot.bootInstance.setMenuMusic(0f);
                this.menuMusicButtonObject.changeText("Off");
            }
            else {
                Config.menuMusic = 1f;
                Boot.bootInstance.setMenuMusic(1f);
                this.menuMusicButtonObject.changeText("On");
            }

            this.settingsController.setMenuMusic();

        }

        if(this.inGameSoundButtonObject.isJustPressed()) {
            if(Config.inGameSound > 0f) {
                Config.inGameSound = 0f;
                this.inGameSoundButtonObject.changeText("Off");
            }
            else {
                Config.inGameSound = 1f;
                this.inGameSoundButtonObject.changeText("On");
            }
            this.settingsController.setInGameSound();

        }

        if(this.inGameMusicButtonObject.isJustPressed()) {
            if(Config.inGameMusic > 0f) {
                Config.inGameMusic = 0f;
                this.inGameMusicButtonObject.changeText("Off");
            }
            else {
                Config.inGameMusic = 0.2f;
                this.inGameMusicButtonObject.changeText("On");
            }
            this.settingsController.setInGameMusic();

        }
    }

}