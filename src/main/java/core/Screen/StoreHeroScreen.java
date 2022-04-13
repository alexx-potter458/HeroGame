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

public class StoreHeroScreen extends Screen {
    private final Button backButton;
    private final Button upButton;
    private final ArrayList<Button> heroButtons;
    private final Button downButton;
    private final TextBox pageTitle;
    private TextBox moneyBanner;
    ArrayList<Hero> heroes;
    private int heroArrayIndex;
    private int selectedHeroIndex;
    private Character selectedCharacter;
    private Hero selectedHero;
    private TextBox heroDescription;
    private TextBox heroName;
    private Button buyButton;



    public StoreHeroScreen(OrthographicCamera camera) {
        super(camera,"storeCategoryScreen/map");
        this.downButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButton     = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.heroButtons    = new ArrayList<>();
        this.upButton       = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle      = new TextBox(Constants.HeroesScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner = new TextBox(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.heroes = (new HeroController().getAllHeroes());
        this.selectedHeroIndex = -1;
        for(int i = 0; i < 5; i++) {
            heroButtons.add(new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
        }

        heroArrayIndex = 0;
        for(int i = heroArrayIndex; i < (Math.min(heroes.size(), 5)); i++) {
            heroButtons.get(i).changeText(heroes.get(i).getName());
        }
    }

    @Override
    protected void update() {
        super.update();
        this.backButton.update();
        this.upButton.update();
        this.downButton.update();
        this.pageTitle.update();
        this.moneyBanner.update();
        if(this.selectedHero != null) {
            this.heroDescription.update();
            this.heroName.update();
            this.selectedCharacter.update();
            this.buyButton.update();

            if(this.buyButton.isJustPressed() && User.user.getMoney() >= this.selectedHero.getPrice()) {
                HeroController heroController = new HeroController();
                heroController.buy(this.selectedHero);
                moneyBanner.setText((User.user.getMoney() - this.selectedHero.getPrice())+"");
            }
        }

        for(Button button: heroButtons)
            button.update();
        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));

        if(this.downButton.isJustPressed() && heroes.size() > 5 + heroArrayIndex)
            this.heroArrayIndex++;
            for(int i = heroArrayIndex; i < 5 + this.heroArrayIndex; i++) {
                heroButtons.get(i- this.heroArrayIndex).changeText(heroes.get(i).getName());
            }

        if(this.upButton.isJustPressed() && this.heroArrayIndex > 0)
            this.heroArrayIndex--;
            for(int i = heroArrayIndex; i < 5 + this.heroArrayIndex; i++) {
                heroButtons.get(i- this.heroArrayIndex).changeText(heroes.get(i).getName());
        }

        for(int i = 0; i < 5; i++) {
            if(heroButtons.get(i).isJustPressed()) {
                this.selectedHeroIndex = this.heroArrayIndex + i;
                this.selectedHero = this.heroes.get(this.selectedHeroIndex);
                this.heroName = new TextBox(this.selectedHero.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.heroDescription = new TextBox(this.selectedHero.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
                this.selectedCharacter = new Character(this, this.selectedHero, 200, 300);
                this.buyButton = new Button(this, (Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
            }
        }
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
        if(this.selectedHero != null) {
            this.heroDescription.render(this.batch);
            this.heroName.render(this.batch);
            this.selectedCharacter.render(this.batch);
            this.buyButton.render(this.batch);
        }
        for(Button button: heroButtons)
            button.render(this.batch);
        this.batch.end();
    }
}