package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.HeroController;
import core.Model.Hero;
import core.Model.User;
import core.Object.Button;
import core.Object.Character;
import core.Object.TextBox;
import utils.Constants;

import java.util.ArrayList;

public class StorePowerScreen extends Screen {
    private final Button backButton;
    private final Button upButton;
    private final ArrayList heroButtons;
    private final Button downButton;
    private final TextBox pageTitle;
    private final TextBox moneyBanner;
    ArrayList<Hero> heroes;

    public StorePowerScreen(OrthographicCamera camera) {
        super(camera,"storeCategoryScreen/map");
        this.downButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 256, Constants.downButton);
        this.backButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButtons    = new ArrayList<>();
        this.upButton       = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle      = new TextBox(Constants.PowersScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner = new TextBox(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.heroes = (new HeroController().getAllHeroes());
    }

    @Override
    protected void update() {
        super.update();
        this.backButton.update();
        this.upButton.update();
        this.downButton.update();
        this.pageTitle.update();
        this.moneyBanner.update();

        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.moneyBanner.render(this.batch);
        this.backButton.render(this.batch);
        this.downButton.render(this.batch);
        this.upButton.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }
}