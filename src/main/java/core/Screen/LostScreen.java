package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.LevelController;
import core.Model.Level;
import core.Object.ButtonObject;
import core.Object.IconObject;
import core.Object.TextBoxObject;
import utils.Constants;

public class LostScreen extends Screen {
    private final int           level;
    private final IconObject    ripIcon;
    private final TextBoxObject pageTitle;
    private final IconObject    ribbonIcon;
    private final ButtonObject  backButtonObject;
    private final ButtonObject  sameLvlButtonObject;


    public LostScreen(OrthographicCamera camera, int level) {
        super(camera, "startScreen/map");
        this.level               = level;
        this.ripIcon             = new IconObject("RIP", (Boot.bootInstance.getScreenWidth()/2), 240, 250, 250);
        this.pageTitle           = new TextBoxObject(Constants.Lost, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 300, 'l');
        this.backButtonObject    = new ButtonObject((Boot.bootInstance.getScreenWidth()/2) + 300, 160, "Levels");
        this.sameLvlButtonObject = new ButtonObject((Boot.bootInstance.getScreenWidth()/2 - 300), 160, "Restart");
        this.ribbonIcon          = new IconObject("bigBadRibbon", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 298, 900, 80);
    }

    @Override
    protected void update() {
        super.update();
        this.pageTitle.update();
        this.backButtonObject.update();
        this.sameLvlButtonObject.update();
        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.ribbonIcon.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.sameLvlButtonObject.render(this.batch);
        this.ripIcon.render(this.batch);
        this.pageTitle.render(this.batch);
        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LevelSelectorScreen(this.camera));

        if(this.sameLvlButtonObject.isJustPressed()) {
            Level levelObject = new LevelController().getLevelById(level);
            Boot.bootInstance.setScreen(new GameScreen(this.camera, levelObject.getId(), levelObject.getBaseScore()));
        }
    }

}