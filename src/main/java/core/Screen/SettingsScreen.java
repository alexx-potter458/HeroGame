package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.SettingsController;
import objects.Button;
import objects.TextBox;
import utils.Config;
import utils.Constants;

public class SettingsScreen extends Screen {
    private final Button backButton;
    private final TextBox dayNightText;
    private final Button dayNightButton;
    private final SettingsController settingsController;
    private final Button screenModeButton;
    private final TextBox screenModeText;
    private final TextBox pageTitle;


    public SettingsScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");
        this.pageTitle = new TextBox(Constants.settingsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.dayNightText = new TextBox(Constants.timeLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 144, 'm');
        this.dayNightButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 144, this.getTimeLabel());
        this.screenModeText = new TextBox(Constants.displayModeLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 64, 'm');
        this.screenModeButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 64, this.getDisplayLabel());
        this.settingsController = new SettingsController();
    }

    @Override
    protected void update() {
        super.update();
        this.pageTitle.update();
        this.backButton.update();
        this.dayNightButton.update();
        this.screenModeText.update();
        this.screenModeButton.update();

        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));

        if(this.dayNightButton.isJustPressed()) {
            if(Config.time.equals(Constants.dayValue)) {
                this.dayNightButton.changeText(Constants.nightLabel);
            } else {
                this.dayNightButton.changeText(Constants.dayLabel);
            }
            this.settingsController.toggleTime();
        }

        if(this.screenModeButton.isJustPressed()) {
            this.settingsController.toggleDisplayMode();
            if(Config.isWindow) {
                this.screenModeButton.changeText(Constants.windowedLabel);
            } else {
                this.screenModeButton.changeText(Constants.fullscreenLabel);
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.pageTitle.render(this.batch);
        this.backButton.render(this.batch);
        this.dayNightText.render(this.batch);
        this.dayNightButton.render(this.batch);
        this.screenModeButton.render(this.batch);
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

}
