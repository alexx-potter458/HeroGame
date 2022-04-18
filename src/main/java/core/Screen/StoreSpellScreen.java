package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.SpellController;
import core.Model.Spell;
import core.Object.ButtonObject;
import core.Object.SpellObject;
import core.Object.TextBoxObject;
import core.Model.User;
import utils.Constants;

import java.util.ArrayList;

public class StoreSpellScreen extends Screen {
    private final ButtonObject backButtonObject;
    private final ButtonObject upButtonObject;
    private final ArrayList<ButtonObject> spellButtonObjects;
    private final ButtonObject downButtonObject;
    private final TextBoxObject pageTitle;
    private final TextBoxObject moneyBanner;
    ArrayList<Spell> spells;
    private int spellArrayIndex;
    private int selectedSpellIndex;
    private SpellObject selectedSpellObject;
    private Spell selectedSpell;
    private TextBoxObject spellName;
    private ButtonObject buyButtonObject;
    private TextBoxObject spellDescription;
    private TextBoxObject spellPrice;

    public StoreSpellScreen(OrthographicCamera camera) {
        super(camera,"storeCategoryScreen/map");
        this.downButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.spellButtonObjects = new ArrayList<>();
        this.upButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle      = new TextBoxObject(Constants.SpellsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.spells = (new SpellController().getAllSpells());
        this.selectedSpellIndex = -1;
        for(int i = 0; i < Math.min(spells.size(), 5); i++) {
            spellButtonObjects.add(new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
        }

        this.spellArrayIndex = 0;
        for(int i = this.spellArrayIndex; i < (Math.min(spells.size(), 5)); i++) {
            spellButtonObjects.get(i).changeText(spells.get(i).getName());
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
        if(this.selectedSpell != null) {
            this.spellDescription.update();
            this.spellName.update();
            this.spellPrice.update();
            this.selectedSpellObject.update();
            this.buyButtonObject.update();

            if(this.buyButtonObject.isJustPressed() && User.user.getMoney() >= this.selectedSpell.getPrice()) {
                SpellController spellController = new SpellController();
                spellController.buy(this.selectedSpell);
                moneyBanner.setText(Constants.moneyBannerLabel + (User.user.getMoney() - this.selectedSpell.getPrice()) + " bucks");
            }
        }

        for(ButtonObject buttonObject : spellButtonObjects)
            buttonObject.update();
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));

        if(this.downButtonObject.isJustPressed() && spells.size() > 5 + this.spellArrayIndex) {
            this.spellArrayIndex++;
            for(int i = this.spellArrayIndex; i < 5 + this.spellArrayIndex; i++) {
                spellButtonObjects.get(i- this.spellArrayIndex).changeText(spells.get(i).getName());
            }
        }

        if(this.upButtonObject.isJustPressed() && this.spellArrayIndex > 0){
            this.spellArrayIndex--;
            for(int i = spellArrayIndex; i < 5 + this.spellArrayIndex; i++) {
                spellButtonObjects.get(i- this.spellArrayIndex).changeText(spells.get(i).getName());
            }
        }

        for(int i = 0; i < Math.min(spells.size(), 5); i++) {
            if(spellButtonObjects.get(i).isJustPressed()) {
                this.selectedSpellIndex = this.spellArrayIndex + i;
                this.selectedSpell = this.spells.get(this.selectedSpellIndex);
                this.spellName = new TextBoxObject(this.selectedSpell.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.spellDescription = new TextBoxObject(this.selectedSpell.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
                this.selectedSpellObject = new SpellObject(this, this.selectedSpell, 200, 400);
                this.buyButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
                this.spellPrice = new TextBoxObject("Price: " + this.selectedSpell.getPrice() + " bucks",256,  (Boot.bootInstance.getScreenHeight()) - 300, 's');

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
        if(this.selectedSpell != null) {
            this.spellPrice.render(this.batch);
            this.spellDescription.render(this.batch);
            this.spellName.render(this.batch);
            this.selectedSpellObject.render(this.batch);
            this.buyButtonObject.render(this.batch);
        }
        for(ButtonObject buttonObject : spellButtonObjects)
            buttonObject.render(this.batch);
        this.batch.end();
    }
}