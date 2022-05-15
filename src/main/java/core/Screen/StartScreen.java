package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Object.ButtonObject;
import core.Object.CloudsObject;
import core.Object.TextBoxObject;
import core.Model.User;
import utils.Constants;

public class StartScreen extends Screen {
    private final CloudsObject  cloudsObject;
    private final TextBoxObject pageTitle;
    private final TextBoxObject greetingsBanner;
    private final ButtonObject  startButtonObject;
    private final ButtonObject  settingsButtonObject;
    private final ButtonObject  controlsButtonObject;
    private final ButtonObject  quitButtonObject;

    public StartScreen(OrthographicCamera camera) {
        super(camera, "startScreen/map");

        this.cloudsObject           = new CloudsObject(this);
        this.pageTitle              = new TextBoxObject(Constants.gameTitleCaps, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2), 'l');
        this.startButtonObject      = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.startButton);
        this.settingsButtonObject   = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.settingsButton);
        this.controlsButtonObject   = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 80, Constants.controlsButton);
        this.quitButtonObject       = new ButtonObject(this, 144, 160, Constants.quitButton);
        this.pageTitle.setY(Boot.bootInstance.getScreenHeight()/2 + 200);

        String greetingMessage = "";

        if(!User.user.isFirstTime())
            greetingMessage    = "Hello, " + User.user.getNickname();

        this.greetingsBanner   = new TextBoxObject(greetingMessage, 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
    }

    public StartScreen(OrthographicCamera camera, int bannerY) {
        this(camera);
        this.pageTitle.setY(bannerY);
    }

    @Override
    protected void update() {
        super.update();

        this.cloudsObject.update();
        this.greetingsBanner.update();
        this.pageTitle.update();
        this.startButtonObject.update();
        this.settingsButtonObject.update();
        this.controlsButtonObject.update();
        this.quitButtonObject.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.cloudsObject.render(this.batch);
        this.greetingsBanner.render(this.batch);
        this.pageTitle.render(this.batch);
        this.startButtonObject.render(this.batch);
        this.settingsButtonObject.render(this.batch);
        this.controlsButtonObject.render(this.batch);
        this.quitButtonObject.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.quitButtonObject.isPressed())
            Gdx.app.exit();

        if(this.settingsButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new SettingsScreen(this.camera));

        if(this.controlsButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new ControlsScreen(this.camera));

        if(this.startButtonObject.isJustPressed())
            if(User.user.isFirstTime())
                Boot.bootInstance.setScreen(new NewUserScreen(this.camera));
            else
                Boot.bootInstance.setScreen(new LobbyScreen(this.camera));
    }

}