package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.HeroController;
import core.Model.Hero;
import core.Model.User;
import core.Object.ButtonObject;
import core.Object.HeroObject;
import core.Object.TextBoxObject;
import utils.Constants;

import java.util.ArrayList;

public class StoreHeroScreen extends Screen {
    private final ButtonObject              backButtonObject;
    private final ButtonObject              upButtonObject;
    private final ArrayList<ButtonObject>   heroButtonObjects;
    private final ButtonObject              downButtonObject;
    private final TextBoxObject             pageTitle;
    private final TextBoxObject             emptyPageTitle;
    private final TextBoxObject             moneyBanner;
    private final ArrayList<Hero>           heroes;
    private int                             heroArrayIndex;
    private int                             selectedHeroIndex;
    private HeroObject                      selectedHeroObject;
    private Hero                            selectedHero;
    private TextBoxObject                   heroDescription;
    private TextBoxObject                   heroName;
    private TextBoxObject                   heroPrice;
    private ButtonObject                    buyButtonObject;
    private final boolean                   storeMode;

    public StoreHeroScreen(OrthographicCamera camera, boolean storeMode) {
        super(camera,"storeCategoryScreen/map", true);

        this.storeMode          = storeMode;
        this.downButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButtonObjects  = new ArrayList<>();
        this.upButtonObject     = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle          = new TextBoxObject(Constants.HeroesScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner        = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');

        if(this.storeMode)
            this.heroes = new HeroController().getAllHeroes();
        else
            this.heroes = new HeroController().getBoughtHeroes();

        this.emptyPageTitle    = new TextBoxObject(this.heroes.size() > 0 ? "" : "Nothing here", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight())/2, 's');
        this.selectedHeroIndex = -1;

        for(int i = 0; i < Math.min(heroes.size(), 5); i++) {
            heroButtonObjects.add(new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
        }

        this.heroArrayIndex = 0;
        for(int i = this.heroArrayIndex; i < (Math.min(heroes.size(), 5)); i++) {
            heroButtonObjects.get(i).changeText(heroes.get(i).getName());
        }
    }

    @Override
    protected void update() {
        super.update();

        this.backButtonObject.update();
        this.upButtonObject.update();
        this.downButtonObject.update();
        this.pageTitle.update();
        this.moneyBanner.update();

        for(ButtonObject buttonObject : heroButtonObjects)
            buttonObject.update();

        if(this.selectedHero != null) {
            this.heroDescription.update();
            this.heroName.update();
            this.heroPrice.update();
            this.selectedHeroObject.update();
            this.buyButtonObject.update();

            this.buttonsPressedOnHero();
        }

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.emptyPageTitle.render(this.batch);
        this.moneyBanner.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);

        if(heroes.size() > 5) {
            this.downButtonObject.render(this.batch);
            this.upButtonObject.render(this.batch);
        }

        for(ButtonObject buttonObject : heroButtonObjects)
            buttonObject.render(this.batch);

        if(this.selectedHero != null) {
            this.heroDescription.render(this.batch);
            this.heroPrice.render(this.batch);
            this.heroName.render(this.batch);
            this.selectedHeroObject.render(this.batch);
            this.buyButtonObject.render(this.batch);
        }

        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            if(storeMode)
                Boot.bootInstance.setScreen(new StoreScreen(this.camera));
            else
                Boot.bootInstance.setScreen(new HeroScreen(this.camera));

        if(this.downButtonObject.isJustPressed() && heroes.size() > 5 + heroArrayIndex) {
            this.heroArrayIndex++;

            for(int i = heroArrayIndex; i < 5 + this.heroArrayIndex; i++)
                heroButtonObjects.get(i- this.heroArrayIndex).changeText(heroes.get(i).getName());
        }

        if(this.upButtonObject.isJustPressed() && this.heroArrayIndex > 0){
            this.heroArrayIndex--;

            for(int i = heroArrayIndex; i < 5 + this.heroArrayIndex; i++) {
                heroButtonObjects.get(i- this.heroArrayIndex).changeText(heroes.get(i).getName());
            }
        }

        for(int i = 0; i < Math.min(heroes.size(), 5); i++) {
            if(heroButtonObjects.get(i).isJustPressed()) {
                this.selectedHeroIndex = this.heroArrayIndex + i;

                if(this.selectedHero != null) {
                    this.selectedHeroObject = null;
                }

                this.selectedHero        = this.heroes.get(this.selectedHeroIndex);
                this.heroName            = new TextBoxObject(this.selectedHero.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.heroDescription     = new TextBoxObject(this.selectedHero.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
                this.selectedHeroObject  = new HeroObject(this, this.selectedHero, 200, 300);

                if(storeMode) {
                    this.heroPrice       = new TextBoxObject("Price: " + this.selectedHero.getPrice() + " bucks",256,  (Boot.bootInstance.getScreenHeight()) - 300, 's');
                    this.buyButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
                } else {
                    this.heroPrice       = new TextBoxObject("",0,  0, 's');
                    this.buyButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.selectButton);
                }
            }
        }
    }

    private void buttonsPressedOnHero() {
        if(this.buyButtonObject.isJustPressed() && User.user.getMoney() >= this.selectedHero.getPrice() && storeMode) {
            moneyBanner.setText(Constants.moneyBannerLabel + (User.user.getMoney() - this.selectedHero.getPrice()) + " bucks");
            new HeroController().buy(this.selectedHero);
            Boot.bootInstance.setScreen(new StoreHeroScreen(this.camera, true));
        } else if (this.buyButtonObject.isJustPressed() && !storeMode) {
            new HeroController().makeHeroPrimary(this.selectedHero);
            Boot.bootInstance.setScreen(new HeroScreen(this.camera));
        }
    }

}