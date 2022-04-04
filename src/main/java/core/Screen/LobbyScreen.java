package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.UserController;
import objects.Button;
import objects.Character;
import objects.TextBox;
import objects.User;
import utils.Constants;

public class LobbyScreen extends Screen {
    private final Button backButton;
    private final Button resetButton;
    private final TextBox moneyBanner;
    private final TextBox levelBanner;
    private final TextBox scoreBanner;
    private final Button levelsButton;
    private final TextBox pageTitle;
    private final Button charButton;
    private final Button storeButton;
    private final Character hero;


    public LobbyScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");
        this.pageTitle      = new TextBox(Constants.LobbyScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.resetButton    = new Button(this, 144, 160, Constants.resetButtonLabel);
        this.levelsButton   = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2 - 80) , Constants.newUserButton);
        this.charButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.charButtonLabel);
        this.storeButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.storeButtonLabel);
        this.hero           = new Character(this, Boot.bootInstance.getScreenWidth()/2 - 80, Boot.bootInstance.getScreenHeight()/2 + 86);
        this.moneyBanner = new TextBox(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.levelBanner = new TextBox(Constants.levelBannerLabel + User.user.getLevel() , 256,  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.scoreBanner = new TextBox(Constants.scoreBannerLabel + User.user.getScore(), 256,  (Boot.bootInstance.getScreenHeight()) - 240, 'm');
    }

    @Override
    protected void update() {
        super.update();
        this.backButton.update();
        this.levelBanner.update();
        this.scoreBanner.update();
        this.moneyBanner.update();
        this.resetButton.update();
        this.levelsButton.update();
        this.pageTitle.update();
        this.charButton.update();
        this.storeButton.update();
        this.hero.update();

        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));

        if(this.storeButton.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));

        if(this.levelsButton.isJustPressed()) {
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
        }

        if(this.resetButton.isJustPressed()) {
            UserController userController = new UserController();
            userController.deleteUser();
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.levelsButton.render(this.batch);
        this.hero.render(this.batch);
        this.moneyBanner.render(this.batch);
        this.levelBanner.render(this.batch);
        this.scoreBanner.render(this.batch);
        this.backButton.render(this.batch);
        this.resetButton.render(this.batch);
        this.charButton.render(this.batch);
        this.storeButton.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }
}
