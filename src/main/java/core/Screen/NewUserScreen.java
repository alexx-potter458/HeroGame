package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.UserController;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import core.Object.TextFieldObject;
import utils.Constants;

public class NewUserScreen extends Screen {
    private final ButtonObject      backButtonObject;
    private final ButtonObject      createUserButtonObject;
    private final TextBoxObject     pageTitle;
    private final TextBoxObject     nicknameLabel;
    private final TextFieldObject   nicknameField;

    public NewUserScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");

        this.pageTitle              = new TextBoxObject(Constants.newUserScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject       = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.createUserButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) , Constants.newUserButton);
        this.nicknameLabel          = new TextBoxObject(Constants.nicknameLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 144, 'm');
        this.nicknameField          = new TextFieldObject(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 144);
    }

    @Override
    protected void update() {
        super.update();

        this.backButtonObject.update();
        this.createUserButtonObject.update();
        this.nicknameLabel.update();
        this.pageTitle.update();
        this.nicknameField.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.createUserButtonObject.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.nicknameLabel.render(this.batch);
        this.pageTitle.render(this.batch);
        this.nicknameField.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));

        if(this.createUserButtonObject.isJustPressed()) {
            UserController user = new UserController();

            if(!nicknameField.getText().equals("")) {
                user.createUser(nicknameField.getText());
                Boot.bootInstance.setScreen(new LobbyScreen(this.camera));
            }
        }
    }

}