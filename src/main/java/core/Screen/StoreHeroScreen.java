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
    private final ButtonObject backButtonObject;
    private final ButtonObject upButtonObject;
    private final ArrayList<ButtonObject> heroButtonObjects;
    private final ButtonObject downButtonObject;
    private final TextBoxObject pageTitle;
    private final TextBoxObject moneyBanner;
    ArrayList<Hero> heroes;
    private int heroArrayIndex;
    private int selectedHeroIndex;
    private HeroObject selectedHeroObject;
    private Hero selectedHero;
    private TextBoxObject heroDescription;
    private TextBoxObject heroName;
    private TextBoxObject heroPrice;
    private ButtonObject buyButtonObject;



    public StoreHeroScreen(OrthographicCamera camera) {
        super(camera,"storeCategoryScreen/map");
        this.downButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButtonObjects = new ArrayList<>();
        this.upButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle      = new TextBoxObject(Constants.HeroesScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.heroes = (new HeroController().getAllHeroes());
        this.selectedHeroIndex = -1;
        for(int i = 0; i < Math.min(heroes.size(), 5); i++) {
            heroButtonObjects.add(new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
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
        if(this.selectedHero != null) {
            this.heroDescription.update();
            this.heroName.update();
            this.heroPrice.update();
            this.selectedHeroObject.update();
            this.buyButtonObject.update();

            if(this.buyButtonObject.isJustPressed() && User.user.getMoney() >= this.selectedHero.getPrice()) {
                moneyBanner.setText(Constants.moneyBannerLabel + (User.user.getMoney() - this.selectedHero.getPrice()) + " bucks");
                HeroController heroController = new HeroController();
                heroController.buy(this.selectedHero);
            }
        }

        for(ButtonObject buttonObject : heroButtonObjects)
            buttonObject.update();
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));

        if(this.downButtonObject.isJustPressed() && heroes.size() > 5 + heroArrayIndex) {
            this.heroArrayIndex++;
            for(int i = heroArrayIndex; i < 5 + this.heroArrayIndex; i++) {
                heroButtonObjects.get(i- this.heroArrayIndex).changeText(heroes.get(i).getName());
            }
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
                this.selectedHero = this.heroes.get(this.selectedHeroIndex);
                this.heroName = new TextBoxObject(this.selectedHero.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.heroDescription = new TextBoxObject(this.selectedHero.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
                this.heroPrice = new TextBoxObject("Price: " + this.selectedHero.getPrice() + " bucks",256,  (Boot.bootInstance.getScreenHeight()) - 300, 's');
                this.selectedHeroObject = new HeroObject(this, this.selectedHero, 200, 300);
                this.buyButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.moneyBanner.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.downButtonObject.render(this.batch);
        this.upButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);
        if(this.selectedHero != null) {
            this.heroDescription.render(this.batch);
            this.heroPrice.render(this.batch);
            this.heroName.render(this.batch);
            this.selectedHeroObject.render(this.batch);
            this.buyButtonObject.render(this.batch);
        }
        for(ButtonObject buttonObject : heroButtonObjects)
            buttonObject.render(this.batch);
        this.batch.end();
    }
}