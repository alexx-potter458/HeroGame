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
    private final ButtonObject backButtonObject;
    private final ButtonObject upButtonObject;
    private final ArrayList<ButtonObject> powerButtonObjects;
    private final ButtonObject downButtonObject;
    private final TextBoxObject pageTitle;
    private final TextBoxObject moneyBanner;
    ArrayList<Power> powers;
    private int powerArrayIndex;
    private int selectedPowerIndex;
    private PowerObject selectedPowerObject;
    private Power selectedPower;
    private TextBoxObject powerName;
    private ButtonObject buyButtonObject;
    private TextBoxObject powerDescription;
    private TextBoxObject powerPrice;




    public StorePowerScreen(OrthographicCamera camera) {
        super(camera,"storeCategoryScreen/map");
        this.downButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.backButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.powerButtonObjects = new ArrayList<>();
        this.upButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.pageTitle      = new TextBoxObject(Constants.HeroesScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.moneyBanner = new TextBoxObject(Constants.moneyBannerLabel + User.user.getMoney() + " bucks", 256,  (Boot.bootInstance.getScreenHeight()) - 160, 'm');
        this.powers = (new PowerController().getAllPowers());
        this.selectedPowerIndex = -1;
        for(int i = 0; i < Math.min(powers.size(), 5); i++) {
            powerButtonObjects.add(new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));
        }

        this.powerArrayIndex = 0;
        for(int i = this.powerArrayIndex; i < (Math.min(powers.size(), 5)); i++) {
            powerButtonObjects.get(i).changeText(powers.get(i).getName());
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
        if(this.selectedPower != null) {
            this.powerDescription.update();
            this.powerName.update();
            this.powerPrice.update();
            this.selectedPowerObject.update();
            this.buyButtonObject.update();

            if(this.buyButtonObject.isJustPressed() && User.user.getMoney() >= this.selectedPower.getPrice()) {
                PowerController powerController = new PowerController();
                powerController.buy(this.selectedPower);
                moneyBanner.setText(Constants.moneyBannerLabel + (User.user.getMoney() - this.selectedPower.getPrice()) + " bucks");
            }
        }

        for(ButtonObject buttonObject : powerButtonObjects)
            buttonObject.update();
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StoreScreen(this.camera));

        if(this.downButtonObject.isJustPressed() && powers.size() > 5 + this.powerArrayIndex) {
            this.powerArrayIndex++;
            for(int i = this.powerArrayIndex; i < 5 + this.powerArrayIndex; i++) {
                powerButtonObjects.get(i- this.powerArrayIndex).changeText(powers.get(i).getName());
            }
        }

        if(this.upButtonObject.isJustPressed() && this.powerArrayIndex > 0){
            this.powerArrayIndex--;
            for(int i = powerArrayIndex; i < 5 + this.powerArrayIndex; i++) {
                powerButtonObjects.get(i- this.powerArrayIndex).changeText(powers.get(i).getName());
            }
        }

        for(int i = 0; i < Math.min(powers.size(), 5); i++) {
            if(powerButtonObjects.get(i).isJustPressed()) {
                this.selectedPowerIndex = this.powerArrayIndex + i;
                this.selectedPower = this.powers.get(this.selectedPowerIndex);
                this.powerName = new TextBoxObject(this.selectedPower.getName(),256,  (Boot.bootInstance.getScreenHeight()) - 220, 's');
                this.powerDescription = new TextBoxObject(this.selectedPower.getDescription(),256,  (Boot.bootInstance.getScreenHeight()) - 264, 's');
                this.selectedPowerObject = new PowerObject(this, this.selectedPower, 200, 300);
                this.buyButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) - 280, (Boot.bootInstance.getScreenHeight()/2), Constants.buyButton);
                this.powerPrice = new TextBoxObject("Price: " + this.selectedPower.getPrice() + " bucks",256,  (Boot.bootInstance.getScreenHeight()) - 300, 's');

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
        if(this.selectedPower != null) {
            this.powerPrice.render(this.batch);
            this.powerDescription.render(this.batch);
            this.powerName.render(this.batch);
            this.selectedPowerObject.render(this.batch);
            this.buyButtonObject.render(this.batch);
        }
        for(ButtonObject buttonObject : powerButtonObjects)
            buttonObject.render(this.batch);
        this.batch.end();
    }
}