package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import core.Model.User;
import utils.Constants;

public class StoreScreen extends Screen {
    private final ButtonObject  backButtonObject;
    private final ButtonObject  heroButtonObject;
    private final ButtonObject  spellButtonObject;
    private final ButtonObject  heroPowerButtonObject;
    private final TextBoxObject pageTitle;
    private final TextBoxObject moneyBanner;

    public StoreScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");

        this.pageTitle              = new TextBoxObject(Constants.StoreScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject       = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButtonObject       = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.heroesLabel);
        this.spellButtonObject      = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.spellsLabel);
        this.heroPowerButtonObject  = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) -80, Constants.powersLabel);
        this.moneyBanner            = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
    }

    @Override
    protected void update() {
        super.update();

        this.backButtonObject.update();
        this.heroButtonObject.update();
        this.spellButtonObject.update();
        this.heroPowerButtonObject.update();
        this.pageTitle.update();
        this.moneyBanner.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.moneyBanner.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.spellButtonObject.render(this.batch);
        this.heroButtonObject.render(this.batch);
        this.heroPowerButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));

        if(this.heroButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreHeroScreen(this.camera, true));

        if(this.spellButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreSpellScreen(this.camera, true));

        if(this.heroPowerButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StorePowerScreen(this.camera, true));
    }

}