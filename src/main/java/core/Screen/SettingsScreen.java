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
    private final ButtonObject          dayNightButtonObject;
    private final SettingsController    settingsController;
    private final ButtonObject          screenModeButtonObject;
    private final TextBoxObject         screenModeText;
    private final TextBoxObject         pageTitle;

    public SettingsScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");

        this.pageTitle              = new TextBoxObject(Constants.settingsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject       = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.dayNightText           = new TextBoxObject(Constants.timeLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 144, 'm');
        this.dayNightButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 144, this.getTimeLabel());
        this.screenModeText         = new TextBoxObject(Constants.displayModeLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 64, 'm');
        this.screenModeButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 64, this.getDisplayLabel());
        this.settingsController     = new SettingsController();
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
    }

}