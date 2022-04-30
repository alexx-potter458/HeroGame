package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import utils.Constants;

public class ControlsScreen extends Screen {
    private final ButtonObject  backButton;
    private final TextBoxObject jumpText;
    private final TextBoxObject downText;
    private final TextBoxObject forwardText;
    private final TextBoxObject backwardText;
    private final TextBoxObject spells;
    private final TextBoxObject power;
    private final TextBoxObject pageTitle;
    private final ButtonObject jump;
    private final ButtonObject front;
    private final ButtonObject down;
    private final ButtonObject back;
    private final ButtonObject powersBtn;
    private final ButtonObject spellsBtn;

    public ControlsScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");

        this.pageTitle      = new TextBoxObject(Constants.controlsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButton     = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.jumpText       = new TextBoxObject(Constants.jumpText, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()) - 270, 'm');
        this.downText       = new TextBoxObject(Constants.downText, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()) - 370, 'm');
        this.forwardText    = new TextBoxObject(Constants.forwardText, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()) - 470, 'm');
        this.backwardText   = new TextBoxObject(Constants.backwardText, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()) - 570, 'm');
        this.spells         = new TextBoxObject(Constants.spellsLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight())  - 670, 'm');
        this.power          = new TextBoxObject(Constants.powersLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()) - 770, 'm');
        this.jump           = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()) - 270, Constants.jumpBtn);
        this.down           = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()) - 370, Constants.downBtn);
        this.front          = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()) - 470, Constants.forwardBtn);
        this.back           = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()) - 570, Constants.backwardBtn);
        this.spellsBtn      = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()) - 670, Constants.spellsBtn);
        this.powersBtn      = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()) - 770, Constants.powersBtn);
    }

    @Override
    protected void update() {
        super.update();

        this.pageTitle.update();
        this.backButton.update();
        this.backwardText.update();
        this.forwardText.update();
        this.spells.update();
        this.jumpText.update();
        this.power.update();
        this.downText.update();
        this.back.update();
        this.front.update();
        this.jump.update();
        this.down.update();
        this.powersBtn.update();
        this.spellsBtn.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.pageTitle.render(this.batch);
        this.backButton.render(this.batch);
        this.backwardText.render(this.batch);
        this.forwardText.render(this.batch);
        this.spells.render(this.batch);
        this.jumpText.render(this.batch);
        this.power.render(this.batch);
        this.downText.render(this.batch);
        this.back.render(this.batch);
        this.front.render(this.batch);
        this.jump.render(this.batch);
        this.down.render(this.batch);
        this.powersBtn.render(this.batch);
        this.spellsBtn.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
    }

}
