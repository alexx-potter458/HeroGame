package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.PowerController;
import core.Model.Power;
import core.Model.User;
import core.Object.ButtonObject;
import core.Object.PowerObject;
import core.Object.TextBoxObject;
import utils.Constants;
import java.util.ArrayList;

public class StorePowerScreen extends Screen {
    private final ButtonObject              backButtonObject;
    private final ButtonObject              upButtonObject;
    private final ArrayList<ButtonObject>   powerButtonObjects;
    private final ButtonObject              downButtonObject;
    private final TextBoxObject             pageTitle;
    private final TextBoxObject             emptyPageTitle;
    private final TextBoxObject             moneyBanner;
    private final ArrayList<Power>          powers;
    private int                             powerArrayIndex;
    private int                             selectedPowerIndex;
    private PowerObject                     selectedPowerObject;
    private Power                           selectedPower;
    private TextBoxObject                   powerName;
    private ButtonObject                    buyButtonObject;
    private TextBoxObject                   powerDescription;
    private TextBoxObject                   powerPrice;
    private final boolean                   storeMode;
    private TextBoxObject                   activePower;
    private TextBoxObject                   powerCountText;
    private int                             activePowerCount;

    public StorePowerScreen(OrthographicCamera camera, boolean storeMode) {
        super(camera,"storeCategoryScreen/map", true);

        this.downButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButtonObject   = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.powerButtonObjects = new ArrayList<>();
        this.upButtonObject     = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle          = new TextBoxObject(Constants.PowersScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner        = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.storeMode          = storeMode;
        this.activePowerCount   = 0;

        if (this.storeMode)
            this.powers = new PowerController().getAllPowers();
        else
            this.powers = new PowerController().getBoughtPowers();

        this.emptyPageTitle     = new TextBoxObject(this.powers.size() > 0 ? "" : "Nothing here", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight())/2, 's');
        this.selectedPowerIndex = -1;

        for(int i = 0; i < Math.min(powers.size(), 5); i++) {
            powerButtonObjects.add(new ButtonObject((Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
        }

        this.powerArrayIndex = 0;
        for(int i = this.powerArrayIndex; i < (Math.min(powers.size(), 5)); i++)
            powerButtonObjects.get(i).changeText(powers.get(i).getName());
    }

    @Override
    protected void update() {
        super.update();

        this.backButtonObject.update();
        this.upButtonObject.update();
        this.downButtonObject.update();
        this.pageTitle.update();
        this.moneyBanner.update();

        if(this.selectedPower != null) {
            this.powerDescription.update();
            this.powerName.update();
            this.powerPrice.update();
            this.selectedPowerObject.update();
            this.buyButtonObject.update();

            this.buttonsPressedOnPower();
        }

        for(ButtonObject buttonObject : powerButtonObjects)
            buttonObject.update();

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

        if(powers.size() > 5) {
            this.downButtonObject.render(this.batch);
            this.upButtonObject.render(this.batch);
        }

        for(ButtonObject buttonObject : powerButtonObjects)
            buttonObject.render(this.batch);

        if(this.selectedPower != null) {
            this.powerPrice.render(this.batch);
            this.powerDescription.render(this.batch);
            this.powerName.render(this.batch);
            this.selectedPowerObject.render(this.batch);
            this.buyButtonObject.render(this.batch);
            this.activePower.render(this.batch);
            this.powerCountText.render(this.batch);
        }

        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            if(this.storeMode)
                Boot.bootInstance.setScreen(new StoreScreen(this.camera));
            else
                Boot.bootInstance.setScreen(new HeroScreen(this.camera));

        if(this.downButtonObject.isJustPressed() && powers.size() > 5 + this.powerArrayIndex) {
            this.powerArrayIndex++;
            for(int i = this.powerArrayIndex; i < 5 + this.powerArrayIndex; i++) {
                powerButtonObjects.get(i- this.powerArrayIndex).changeText(powers.get(i).getName());
            }
        }

        if(this.upButtonObject.isJustPressed() && this.powerArrayIndex > 0) {
            this.powerArrayIndex--;

            for(int i = powerArrayIndex; i < 5 + this.powerArrayIndex; i++) {
                powerButtonObjects.get(i- this.powerArrayIndex).changeText(powers.get(i).getName());
            }
        }

        for(int i = 0; i < Math.min(powers.size(), 5); i++) {
            if(powerButtonObjects.get(i).isJustPressed()) {
                this.selectedPowerIndex  = this.powerArrayIndex + i;
                this.selectedPower       = this.powers.get(this.selectedPowerIndex);
                this.powerName           = new TextBoxObject(this.selectedPower.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.powerDescription    = new TextBoxObject(this.selectedPower.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
                this.selectedPowerObject = new PowerObject(this, this.selectedPower, 256, 400);

                if (this.storeMode) {
                    this.powerPrice      = new TextBoxObject("Price: " + this.selectedPower.getPrice() + " bucks",256,  (Boot.bootInstance.getScreenHeight()) - 300, 's');
                    this.buyButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
                    this.activePower     = new TextBoxObject("", 0,  0, 's');
                    this.powerCountText  = new TextBoxObject("", 0,  0, 's');

                } else {
                    this.activePowerCount = 0;
                    for (Power power: powers)
                        if(power.isActive() == 1)
                            this.activePowerCount++;

                    this.activePower     = new TextBoxObject((this.selectedPower.isActive() == 1)? "Active" : "Not active", 256,  (Boot.bootInstance.getScreenHeight()) - 320, 's');
                    this.buyButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), (this.selectedPower.isActive() == 0)? "Activate" : "Deactivate");
                    this.powerPrice      = new TextBoxObject("",0,  0, 's');
                    this.powerCountText  = new TextBoxObject(this.activePowerCount + "/1 active powers", (Boot.bootInstance.getScreenWidth()/2) - 290, (Boot.bootInstance.getScreenHeight()/2) - 80 , 's');
                }
            }
        }
    }

    private void buttonsPressedOnPower() {
        if(this.buyButtonObject.isJustPressed() && User.user.getMoney() >= this.selectedPower.getPrice() && storeMode) {
            new PowerController().buy(this.selectedPower);
            moneyBanner.setText(Constants.moneyBannerLabel + (User.user.getMoney() - this.selectedPower.getPrice()) + " bucks");
            Boot.bootInstance.setScreen(new StorePowerScreen(this.camera, true));
        } else if(this.buyButtonObject.isJustPressed() && !storeMode) {
            new PowerController().changePowerStatus(this.selectedPower.isActive(), this.selectedPower.getId());
            Boot.bootInstance.setScreen(new StorePowerScreen(this.camera, false));
        }
    }

}