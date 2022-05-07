package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Object.ButtonObject;
import utils.Constants;

public class LevelSelectorScreen extends Screen {
    private final ButtonObject backButtonObject;
    private final ButtonObject levelButtonObject;

    public LevelSelectorScreen(OrthographicCamera camera) {
        super(camera,"mapSelectorScreen/map");
        this.backButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.levelButtonObject = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 355, "level1");
    }

    @Override
    public void update() {
        super.update();
        this.backButtonObject.update();
        this.levelButtonObject.update();

        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));

        if(this.levelButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new GameScreen(this.camera, 2));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.backButtonObject.render(this.batch);
        this.levelButtonObject.render(this.batch);
        this.batch.end();
    }
}
