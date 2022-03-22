package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import objects.Button;
import objects.Clouds;
import objects.TextBox;
import utils.Constants;

public class StartScreen extends Screen {
    private final Clouds clouds;
    private final TextBox pageTitle;
    private final Button startButton;
    private final Button settingsButton;
    private final Button controlsButton;
    private final Button quitButton;

    public StartScreen(OrthographicCamera camera) {
        super(camera, "startScreen/map");
        this.clouds         = new Clouds(this);
        this.pageTitle = new TextBox(Constants.gameTitleCaps, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2), 'l');
        this.startButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.startButton);
        this.settingsButton = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.settingsButton);
        this.controlsButton = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 80, Constants.controlsButton);
        this.quitButton     = new Button(this, 144, 160, Constants.quitButton);
        this.pageTitle.setY(Boot.bootInstance.getScreenHeight()/2 + 200);
    }

    public StartScreen(OrthographicCamera camera, int bannerY) {
        this(camera);
        this.pageTitle.setY(bannerY);
    }

    @Override
    protected void update() {
        super.update();
        this.clouds.update();
        this.pageTitle.update();
        this.startButton.update();
        this.settingsButton.update();
        this.controlsButton.update();
        this.quitButton.update();

        if(this.quitButton.isPressed())
            Gdx.app.exit();

        if(this.settingsButton.isJustPressed())
            Boot.bootInstance.setScreen(new SettingsScreen(this.camera));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.clouds.render(this.batch);
        this.pageTitle.render(this.batch);
        this.startButton.render(this.batch);
        this.settingsButton.render(this.batch);
        this.controlsButton.render(this.batch);
        this.quitButton.render(this.batch);
        this.batch.end();
    }
}
