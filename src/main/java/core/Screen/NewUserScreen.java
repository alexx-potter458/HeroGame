package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import core.Boot;
import objects.Button;
import objects.TextBox;
import objects.TextField;
import utils.Constants;

public class NewUserScreen extends Screen {
    private final Button backButton;
    private final Button createUserButton;
    private final TextBox pageTitle;
    private final  TextBox nicknameLabel;
    private TextField nicknameField;

    public NewUserScreen(OrthographicCamera camera) {
        super(camera,"startScreen/map");
        this.pageTitle = new TextBox(Constants.newUserScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.createUserButton    = new Button(this, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()/2) , Constants.newUserButton);
        this.nicknameLabel = new TextBox(Constants.nicknameLabel, (Boot.bootInstance.getScreenWidth()/2) - 144, (Boot.bootInstance.getScreenHeight()/2) + 144, 'm');
        this.nicknameField = new TextField(this, (Boot.bootInstance.getScreenWidth()/2) + 144, (Boot.bootInstance.getScreenHeight()/2) + 144);

    }

    @Override
    protected void update() {
        super.update();
        this.backButton.update();
        this.createUserButton.update();
        this.nicknameLabel.update();
        this.pageTitle.update();
        this.nicknameField.update();

        if(this.backButton.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));

        if(this.createUserButton.isJustPressed())
            Boot.bootInstance.setScreen(new StartScreen(this.camera));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.createUserButton.render(this.batch);
        this.backButton.render(this.batch);
        this.nicknameLabel.render(this.batch);
        this.pageTitle.render(this.batch);
        this.nicknameField.render(this.batch);
        this.batch.end();
    }

}
