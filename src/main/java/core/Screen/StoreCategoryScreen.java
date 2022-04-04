package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import objects.Button;
import objects.TextBox;
import objects.User;
import utils.Constants;

public class StoreCategoryScreen extends Screen {
    private final String category;
    private final Button backButton;
    private final TextBox pageTitle;
    private final TextBox moneyBanner;

    public StoreCategoryScreen(OrthographicCamera camera, String category) {
        super(camera,"startScreen/map");
        this.category = category;
        this.backButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        if(this.category.equals(Constants.heroes))
            this.pageTitle      = new TextBox(Constants.HeroesScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        else if(this.category.equals(Constants.spells))
            this.pageTitle      = new TextBox(Constants.SpellsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        else
            this.pageTitle      = new TextBox(Constants.PowersScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');

        this.moneyBanner = new TextBox(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
    }

    @Override
    protected void update() {
        super.update();
        this.backButton.update();
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
        this.pageTitle.render(this.batch);
        this.batch.end();
    }
}