package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import objects.Button;
import objects.TextBox;
import objects.User;
import utils.Constants;

public class StoreScreen extends Screen {
    private final Button backButton;
    private final Button heroButton;
    private final Button spellButton;
    private final Button heroPowerButton;
    private final TextBox pageTitle;
    private final TextBox moneyBanner;

    public StoreScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");
        this.pageTitle      = new TextBox(Constants.StoreScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) + 64, Constants.heroesLabel);
        this.spellButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) - 8, Constants.spellsLabel);
        this.heroPowerButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) -80, Constants.powersLabel);
        this.moneyBanner = new TextBox(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
    }

    @Override
    protected void update() {
        super.update();
        this.backButton.update();
        this.heroButton.update();
        this.spellButton.update();
        this.heroPowerButton.update();
        this.pageTitle.update();
        this.moneyBanner.update();

        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));

        if(this.heroButton.isJustPressed())
            Boot.bootInstance.setScreen(new StoreCategoryScreen(this.camera, Constants.heroes));
        if(this.spellButton.isJustPressed())
            Boot.bootInstance.setScreen(new StoreCategoryScreen(this.camera, Constants.spells));
        if(this.heroPowerButton.isJustPressed())
            Boot.bootInstance.setScreen(new StoreCategoryScreen(this.camera, Constants.powers));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.moneyBanner.render(this.batch);
        this.backButton.render(this.batch);
        this.spellButton.render(this.batch);
        this.heroButton.render(this.batch);
        this.heroPowerButton.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }
}
