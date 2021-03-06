package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.HeroController;
import core.Controller.SpellController;
import core.Model.Spell;
import core.Object.ButtonObject;
import core.Object.SpellObject;
import core.Object.TextBoxObject;
import core.Model.User;
import utils.Constants;

import java.util.ArrayList;

public class StoreSpellScreen extends Screen {
    private final ButtonObject              backButtonObject;
    private final ButtonObject              upButtonObject;
    private final ArrayList<ButtonObject>   spellButtonObjects;
    private final ButtonObject              downButtonObject;
    private final TextBoxObject             pageTitle;
    private TextBoxObject                   activeSpell;
    private TextBoxObject                   spellCountText;
    private int                             activeSpellCount;
    private final TextBoxObject             moneyBanner;
    private final TextBoxObject             emptyPageTitle;
    private final ArrayList<Spell>          spells;
    private int                             spellArrayIndex;
    private int                             selectedSpellIndex;
    private SpellObject                     selectedSpellObject;
    private Spell                           selectedSpell;
    private TextBoxObject                   spellName;
    private ButtonObject                    buyButtonObject;
    private TextBoxObject                   spellDescription;
    private TextBoxObject                   spellPrice;
    private final boolean                   storeMode;

    public StoreSpellScreen(OrthographicCamera camera, boolean storeMode) {
        super(camera,"storeCategoryScreen/map", true);

        this.downButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.spellButtonObjects = new ArrayList<>();
        this.upButtonObject     = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle          = new TextBoxObject(Constants.SpellsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner        = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.storeMode          = storeMode;

        if(this.storeMode) {
            if(new HeroController().getMainHero() != null)
                this.spells = new SpellController().getAllSpells();
            else
                this.spells = new ArrayList<>();
        } else
            this.spells = new SpellController().getBoughtSpells();

        this.emptyPageTitle     = new TextBoxObject(this.spells.size() > 0 ? "" : "Nothing here", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight())/2, 's');
        this.selectedSpellIndex = -1;

        for(int i = 0; i < Math.min(spells.size(), 5); i++) {
            spellButtonObjects.add(new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
        }

        this.spellArrayIndex = 0;
        for(int i = this.spellArrayIndex; i < (Math.min(spells.size(), 5)); i++)
            spellButtonObjects.get(i).changeText(spells.get(i).getName());
    }

    @Override
    protected void update() {
        super.update();

        this.backButtonObject.update();
        this.upButtonObject.update();
        this.downButtonObject.update();
        this.pageTitle.update();
        this.moneyBanner.update();

        for(ButtonObject buttonObject : spellButtonObjects)
            buttonObject.update();

        if(this.selectedSpell != null) {
            this.spellDescription.update();
            this.spellName.update();
            this.spellPrice.update();
            this.selectedSpellObject.update();
            this.buyButtonObject.update();

            this.buttonsPressedOnSpell();
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
        if(spells.size() > 5) {
            this.downButtonObject.render(this.batch);
            this.upButtonObject.render(this.batch);
        }
        this.pageTitle.render(this.batch);

        for(ButtonObject buttonObject : spellButtonObjects)
            buttonObject.render(this.batch);

        if(this.selectedSpell != null) {
            this.spellPrice.render(this.batch);
            this.activeSpell.render(this.batch);
            this.spellDescription.render(this.batch);
            this.spellName.render(this.batch);
            this.selectedSpellObject.render(this.batch);
            this.buyButtonObject.render(this.batch);
            this.spellCountText.render(this.batch);
        }

        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            if(this.storeMode)
                Boot.bootInstance.setScreen(new StoreScreen(this.camera));
            else
                Boot.bootInstance.setScreen(new HeroScreen(this.camera));

        if(this.downButtonObject.isJustPressed() && spells.size() > 5 + this.spellArrayIndex) {
            this.spellArrayIndex++;
            for(int i = this.spellArrayIndex; i < 5 + this.spellArrayIndex; i++)
                spellButtonObjects.get(i- this.spellArrayIndex).changeText(spells.get(i).getName());
        }

        if(this.upButtonObject.isJustPressed() && this.spellArrayIndex > 0) {
            this.spellArrayIndex--;
            for(int i = spellArrayIndex; i < 5 + this.spellArrayIndex; i++)
                spellButtonObjects.get(i- this.spellArrayIndex).changeText(spells.get(i).getName());
        }

        for(int i = 0; i < Math.min(spells.size(), 5); i++) {
            if(spellButtonObjects.get(i).isJustPressed()) {
                this.selectedSpellIndex  = this.spellArrayIndex + i;
                this.selectedSpell       = this.spells.get(this.selectedSpellIndex);
                this.spellName           = new TextBoxObject(this.selectedSpell.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.spellDescription    = new TextBoxObject(this.selectedSpell.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 274, 's');
                this.selectedSpellObject = new SpellObject(this, this.selectedSpell, 256, 400);

                if(storeMode) {
                    this.buyButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 290, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
                    this.activeSpell     = new TextBoxObject("", 0,  0, 's');
                    this.spellCountText  = new TextBoxObject("", 0,  0, 's');
                    this.spellPrice      = new TextBoxObject("Price: " + this.selectedSpell.getPrice() + " bucks",256,  (Boot.bootInstance.getScreenHeight()) - 320, 's');
                } else {
                    this.activeSpellCount = 0;
                    for (Spell spell: spells)
                        if(spell.isActive() == 1)
                            this.activeSpellCount++;

                    this.activeSpell     = new TextBoxObject((this.selectedSpell.isActive() == 1)? "Active" : "Not active", 256,  (Boot.bootInstance.getScreenHeight()) - 320, 's');
                    this.buyButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 290, (Boot.bootInstance.getScreenHeight()/2), (this.selectedSpell.isActive() == 0)? "Activate" : "Deactivate");
                    this.spellPrice      = new TextBoxObject("", 0,  0, 's');
                    this.spellCountText  = new TextBoxObject(this.activeSpellCount + "/3 active spells", (Boot.bootInstance.getScreenWidth()/2) - 290, (Boot.bootInstance.getScreenHeight()/2) - 80 , 's');
                }
            }
        }
    }

    private void buttonsPressedOnSpell() {
        if(this.buyButtonObject.isJustPressed() && User.user.getMoney() >= this.selectedSpell.getPrice() && storeMode) {
            new SpellController().buy(this.selectedSpell);
            moneyBanner.setText(Constants.moneyBannerLabel + (User.user.getMoney() - this.selectedSpell.getPrice()) + " bucks");
            Boot.bootInstance.setScreen(new StoreSpellScreen(this.camera, true));
        } else if(this.buyButtonObject.isJustPressed() && !storeMode && (this.activeSpellCount < 3 || this.selectedSpell.isActive() == 1)) {
            new SpellController().changeSpellStatus(this.selectedSpell.isActive(), this.selectedSpell.getId());
            Boot.bootInstance.setScreen(new StoreSpellScreen(this.camera, false));
        }
    }

}