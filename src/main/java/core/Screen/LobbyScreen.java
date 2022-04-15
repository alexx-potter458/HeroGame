package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.UserController;
import core.Object.ButtonObject;
import core.Object.HeroObject;
import core.Object.TextBoxObject;
import core.Model.User;
import utils.Constants;

public class LobbyScreen extends Screen {
    private final ButtonObject backButtonObject;
    private final ButtonObject resetButtonObject;
    private final TextBoxObject moneyBanner;
    private final TextBoxObject levelBanner;
    private final TextBoxObject scoreBanner;
    private final ButtonObject levelsButtonObject;
    private final TextBoxObject pageTitle;
    private final ButtonObject charButtonObject;
    private final ButtonObject storeButtonObject;
    private final HeroObject heroObject;


    public LobbyScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");
        this.pageTitle      = new TextBoxObject(Constants.LobbyScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.resetButtonObject = new ButtonObject(this, 144, 160, Constants.resetButtonLabel);
        this.levelsButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2 - 80) , Constants.newUserButton);
        this.charButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.charButtonLabel);
        this.storeButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.storeButtonLabel);
        this.heroObject = new HeroObject(this, Boot.bootInstance.getScreenWidth()/2 - 80, Boot.bootInstance.getScreenHeight()/2 + 86);
        this.moneyBanner = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.levelBanner = new TextBoxObject(Constants.levelBannerLabel + User.user.getLevel() , 256,  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.scoreBanner = new TextBoxObject(Constants.scoreBannerLabel + User.user.getScore(), 256,  (Boot.bootInstance.getScreenHeight()) - 240, 'm');
    }

    @Override
    protected void update() {
        super.update();
        this.backButtonObject.update();
        this.levelBanner.update();
        this.scoreBanner.update();
        this.moneyBanner.update();
        this.resetButtonObject.update();
        this.levelsButtonObject.update();
        this.pageTitle.update();
        this.charButtonObject.update();
        this.storeButtonObject.update();
        this.heroObject.update();

        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));

        if(this.storeButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));

        if(this.levelsButtonObject.isJustPressed()) {
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
        }

        if(this.resetButtonObject.isJustPressed()) {
            UserController userController = new UserController();
            userController.deleteUser();
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.levelsButtonObject.render(this.batch);
        this.heroObject.render(this.batch);
        this.moneyBanner.render(this.batch);
        this.levelBanner.render(this.batch);
        this.scoreBanner.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.resetButtonObject.render(this.batch);
        this.charButtonObject.render(this.batch);
        this.storeButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }
}
