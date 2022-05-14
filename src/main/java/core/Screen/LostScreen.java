package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import utils.Constants;

public class LostScreen extends Screen {
    private final ButtonObject  backButtonObject;
    private final TextBoxObject pageTitle;

    public LostScreen(OrthographicCamera camera) {
        super(camera, "startScreen/map");
        this.backButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, "Levels");
        this.pageTitle        = new TextBoxObject(Constants.Lost, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 300, 'l');
    }

    @Override
    protected void update() {
        super.update();
        this.pageTitle.update();
        this.backButtonObject.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.backButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LevelSelectorScreen(this.camera));
    }

}