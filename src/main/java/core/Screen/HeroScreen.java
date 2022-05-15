package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.HeroController;
import core.Model.Hero;
import core.Object.ButtonObject;
import core.Object.HeroObject;
import core.Object.TextBoxObject;
import core.Model.User;
import utils.Constants;

public class HeroScreen extends Screen {
    private final ButtonObject  backButtonObject;
    private final ButtonObject  heroButtonObject;
    private final ButtonObject  spellButtonObject;
    private final ButtonObject  heroPowerButtonObject;
    private final TextBoxObject pageTitle;
    private final TextBoxObject moneyBanner;
    private final HeroObject    heroObject;
    private final TextBoxObject heroDescription;
    private final TextBoxObject heroName;

    public HeroScreen(OrthographicCamera camera) {
        super(camera,"storeCategoryScreen/map", true);
        Hero hero                   = new HeroController().getMainHero();
        this.pageTitle              = new TextBoxObject(Constants.HeroScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject       = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButtonObject       = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.heroesLabel);
        this.spellButtonObject      = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.spellsLabel);
        this.heroPowerButtonObject  = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) -80, Constants.powersLabel);
        this.moneyBanner            = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.heroObject             = new HeroObject(this, hero,200, 300);
        this.heroName               = new TextBoxObject(hero.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
        this.heroDescription        = new TextBoxObject(hero.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
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
        this.heroObject.update();
        this.heroDescription.update();
        this.heroName.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.heroObject.render(this.batch);
        this.moneyBanner.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.spellButtonObject.render(this.batch);
        this.heroButtonObject.render(this.batch);
        this.heroDescription.render(this.batch);
        this.heroName.render(this.batch);
        this.heroPowerButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));

        if(this.heroButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreHeroScreen(this.camera, false));

        if(this.spellButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreSpellScreen(this.camera, false));

        if(this.heroPowerButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StorePowerScreen(this.camera, false));
    }

}
