package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.UserController;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import utils.Constants;

public class ResetScreen extends Screen {
    private final TextBoxObject alert;
    private final ButtonObject  backButton;
    private final ButtonObject  confirm;
    private final ButtonObject  refuse;

    public ResetScreen(OrthographicCamera camera) {
        super(camera, "startScreen/map");

        this.alert      = new TextBoxObject(Constants.resetQuestion, Boot.bootInstance.getScreenWidth()/2, Boot.bootInstance.getScreenHeight()/2 + 80, 'm');
        this.backButton = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.confirm    = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) - 130, Boot.bootInstance.getScreenHeight()/2, Constants.confirm);
        this.refuse     = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 130, Boot.bootInstance.getScreenHeight()/2, Constants.refuse);
    }

    @Override
    protected void update() {
        super.update();

        this.alert.update();
        this.backButton.update();
        this.confirm.update();
        this.refuse.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.refuse.render(this.batch);
        this.confirm.render(this.batch);
        this.backButton.render(this.batch);
        this.alert.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.confirm.isJustPressed()) {
            new UserController().deleteUser();
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
        }

        if(this.refuse.isJustPressed() || this.backButton.isJustPressed()) {
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));
        }
    }

}
