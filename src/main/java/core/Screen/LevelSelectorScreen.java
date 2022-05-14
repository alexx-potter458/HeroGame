package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.LevelController;
import core.Model.Level;
import core.Object.ButtonObject;
import core.Object.TextBoxObject;
import utils.Constants;

import java.util.ArrayList;

public class LevelSelectorScreen extends Screen {
    private final ButtonObject            backButtonObject;
    private final ButtonObject            startButtonObject;
    private final ArrayList<Level>        levels;
    private final ButtonObject            upButtonObject;
    private final ArrayList<ButtonObject> levelButtonObjects;
    private final ButtonObject            downButtonObject;
    private int                           selectedLevelIndex;
    private int                           levelArrayIndex;
    private Level                         selectedLevel;
    private TextBoxObject                 levelName;
    private TextBoxObject                 levelUnlocked;
    private final TextBoxObject           pageTitle;


    public LevelSelectorScreen(OrthographicCamera camera) {
        super(camera,"mapSelectorScreen/map");
        this.pageTitle           = new TextBoxObject(Constants.LevelsScreenTitle, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.backButtonObject   = new ButtonObject(this, (Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.startButtonObject  = new ButtonObject(this, Boot.bootInstance.getScreenWidth() - 320, Boot.bootInstance.getScreenHeight() / 2, "");
        this.downButtonObject   = new ButtonObject(this, 320, (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.upButtonObject     = new ButtonObject(this, 320, (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.levelButtonObjects = new ArrayList<>();
        this.levels             = new LevelController().getAllLevels();
        this.selectedLevelIndex = -1;
        this.levelArrayIndex    = 0;

        for(int i = 0; i < Math.min(levels.size(), 5); i++)
            levelButtonObjects.add(new ButtonObject(this, 320, (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));

        for(int i = this.levelArrayIndex; i < (Math.min(levels.size(), 5)); i++)
            levelButtonObjects.get(i).changeText(levels.get(i).getName());
    }

    @Override
    public void update() {
        super.update();
        this.backButtonObject.update();
        this.startButtonObject.update();
        this.upButtonObject.update();
        this.downButtonObject.update();
        this.pageTitle.update();

        for(ButtonObject buttonObject : levelButtonObjects)
            buttonObject.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.pageTitle.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.upButtonObject.render(this.batch);
        this.downButtonObject.render(this.batch);

        for(ButtonObject buttonObject : levelButtonObjects)
            buttonObject.render(this.batch);

        if(this.selectedLevel != null) {
            this.levelUnlocked.render(this.batch);
            this.levelName.render(this.batch);
            if(this.selectedLevel.getUnlocked() == 1)
                this.startButtonObject.render(this.batch);
        }

        this.batch.end();
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));

        if(this.startButtonObject.isJustPressed() && this.selectedLevel != null && this.selectedLevel.getUnlocked() == 1)
            Boot.bootInstance.setScreen(new GameScreen(this.camera, this.selectedLevelIndex + 1, this.selectedLevel.getBaseScore()));

        if(this.downButtonObject.isJustPressed() && levels.size() > 5 + this.levelArrayIndex) {
            this.levelArrayIndex++;
            for(int i = this.levelArrayIndex; i < 5 + this.levelArrayIndex; i++) {
                levelButtonObjects.get(i- this.levelArrayIndex).changeText(levels.get(i).getName());
            }
        }

        if(this.upButtonObject.isJustPressed() && this.levelArrayIndex > 0) {
            this.levelArrayIndex--;

            for(int i = levelArrayIndex; i < 5 + this.levelArrayIndex; i++) {
                levelButtonObjects.get(i- this.levelArrayIndex).changeText(levels.get(i).getName());
            }
        }

        for(int i = 0; i < Math.min(levels.size(), 5); i++) {
            if(levelButtonObjects.get(i).isJustPressed()) {
                this.selectedLevelIndex  = this.levelArrayIndex + i;
                this.selectedLevel       = this.levels.get(this.selectedLevelIndex);
                this.levelName           = new TextBoxObject(this.selectedLevel.getName(),620,  (Boot.bootInstance.getScreenHeight()/2) + 20, 's');
                this.levelUnlocked       = new TextBoxObject((this.selectedLevel.getUnlocked() == 1? "Unlocked": "Locked"),620,  (Boot.bootInstance.getScreenHeight()/2) - 20, 's');
            }
        }

        this.startButtonObject.changeText(Constants.goButton);
    }
}
