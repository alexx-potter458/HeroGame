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
    private final ArrayList<Level>        playedLevels;
    private final ButtonObject            upButtonObject;
    private final ButtonObject            upPlayedButtonObject;
    private final ArrayList<ButtonObject> levelButtonObjects;
    private final ArrayList<ButtonObject> playedLevelButtonObjects;
    private final ButtonObject            downButtonObject;
    private final ButtonObject            downPlayedButtonObject;
    private int                           selectedLevelIndex;
    private int                           levelArrayIndex;
    private Level                         selectedLevel;
    private int                           playedLevelArrayIndex;
    private TextBoxObject                 levelName;
    private final TextBoxObject           playedLevel;
    private final TextBoxObject           notPlayedLevel;
    private TextBoxObject                 levelUnlocked;
    private final TextBoxObject           pageTitle;

    public LevelSelectorScreen(OrthographicCamera camera) {
        super(camera,"mapSelectorScreen/map");
        this.pageTitle                = new TextBoxObject(Constants.LevelsScreenTitle, (Boot.bootInstance.getScreenWidth()/2), (Boot.bootInstance.getScreenHeight()) - 200, 'm');
        this.playedLevel              = new TextBoxObject("Not played levels", 320, (Boot.bootInstance.getScreenHeight()) - 248, 'm');
        this.notPlayedLevel           = new TextBoxObject("Played levels", 640, (Boot.bootInstance.getScreenHeight()) - 248, 'm');
        this.backButtonObject         = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, Constants.backButton);
        this.startButtonObject        = new ButtonObject(Boot.bootInstance.getScreenWidth() - 320, Boot.bootInstance.getScreenHeight() / 2, "");
        this.downButtonObject         = new ButtonObject(320, (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.upButtonObject           = new ButtonObject(320, (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.downPlayedButtonObject   = new ButtonObject(640, (Boot.bootInstance.getScreenHeight()) - 820, Constants.downButton);
        this.upPlayedButtonObject     = new ButtonObject(640, (Boot.bootInstance.getScreenHeight()) - 320, Constants.upButton);
        this.levelButtonObjects       = new ArrayList<>();
        this.playedLevelButtonObjects = new ArrayList<>();
        this.levels                   = new LevelController().getAllNotPlayedLevels();
        this.playedLevels             = new LevelController().getAllPlayedLevels();
        this.selectedLevelIndex       = -1;
        this.levelArrayIndex          = 0;
        this.playedLevelArrayIndex    = 0;

        for(int i = 0; i < Math.min(levels.size(), 5); i++)
            levelButtonObjects.add(new ButtonObject(320, (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));

        for(int i = 0; i < Math.min(playedLevels.size(), 5); i++)
            playedLevelButtonObjects.add(new ButtonObject(640, (Boot.bootInstance.getScreenHeight()) - 428 - i * 72, ""));

        for(int i = this.levelArrayIndex; i < (Math.min(levels.size(), 5)); i++)
            levelButtonObjects.get(i).changeText(levels.get(i).getName());

        for(int i = this.levelArrayIndex; i < (Math.min(playedLevels.size(), 5)); i++)
            playedLevelButtonObjects.get(i).changeText(playedLevels.get(i).getName());
    }

    @Override
    public void update() {
        super.update();
        this.backButtonObject.update();
        this.startButtonObject.update();
        this.upButtonObject.update();
        this.downButtonObject.update();
        this.upPlayedButtonObject.update();
        this.downPlayedButtonObject.update();
        this.pageTitle.update();

        for(ButtonObject buttonObject : levelButtonObjects)
            buttonObject.update();

        for(ButtonObject buttonObject : playedLevelButtonObjects)
            buttonObject.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.pageTitle.render(this.batch);
        this.notPlayedLevel.render(this.batch);
        this.playedLevel.render(this.batch);
        this.backButtonObject.render(this.batch);

        if(levels.size() > 5) {
            this.upButtonObject.render(this.batch);
            this.downButtonObject.render(this.batch);
        }

        if(playedLevels.size() > 5) {
            this.upPlayedButtonObject.render(this.batch);
            this.downPlayedButtonObject.render(this.batch);
        }

        for(ButtonObject buttonObject : levelButtonObjects)
            buttonObject.render(this.batch);

        for(ButtonObject buttonObject : playedLevelButtonObjects)
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

        if(this.startButtonObject.isJustPressed() && this.selectedLevel != null && this.selectedLevel.getUnlocked() == 1) {
            Boot.bootInstance.pauseDefaultMusic();
            Boot.bootInstance.setScreen(new GameScreen(this.camera, this.selectedLevel.getId(), this.selectedLevel.getBaseScore()));
        }

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

        if(this.downPlayedButtonObject.isJustPressed() && playedLevels.size() > 5 + this.playedLevelArrayIndex) {
            this.playedLevelArrayIndex++;
            for (int i = this.playedLevelArrayIndex; i < 5 + this.playedLevelArrayIndex; i++)
                playedLevelButtonObjects.get(i - this.playedLevelArrayIndex).changeText(playedLevels.get(i).getName());
        }

        if(this.upPlayedButtonObject.isJustPressed() && this.playedLevelArrayIndex > 0) {
            this.playedLevelArrayIndex--;

            for(int i = playedLevelArrayIndex; i < 5 + this.playedLevelArrayIndex; i++)
                playedLevelButtonObjects.get(i- this.playedLevelArrayIndex).changeText(playedLevels.get(i).getName());
        }

        for(int i = 0; i < Math.min(levels.size(), 5); i++) {
            if(levelButtonObjects.get(i).isJustPressed()) {
                this.selectedLevelIndex  = this.levelArrayIndex + i;
                this.selectedLevel       = this.levels.get(this.selectedLevelIndex);
                this.levelName           = new TextBoxObject(this.selectedLevel.getName(),Boot.bootInstance.getScreenWidth() / 2,  (Boot.bootInstance.getScreenHeight()/2) + 20, 's');
                this.levelUnlocked       = new TextBoxObject((this.selectedLevel.getUnlocked() == 1? "Unlocked": "Locked"),Boot.bootInstance.getScreenWidth() / 2,  (Boot.bootInstance.getScreenHeight()/2) - 20, 's');
            }
        }

        for(int i = 0; i < Math.min(playedLevels.size(), 5); i++) {
            if(playedLevelButtonObjects.get(i).isJustPressed()) {
                this.selectedLevelIndex  = this.playedLevelArrayIndex + i;
                this.selectedLevel       = this.playedLevels.get(this.selectedLevelIndex);
                this.levelName           = new TextBoxObject(this.selectedLevel.getName(),Boot.bootInstance.getScreenWidth() / 2,  (Boot.bootInstance.getScreenHeight()/2) + 20, 's');
                this.levelUnlocked       = new TextBoxObject((this.selectedLevel.getUnlocked() == 1? "Unlocked": "Locked"),Boot.bootInstance.getScreenWidth() / 2,  (Boot.bootInstance.getScreenHeight()/2) - 20, 's');
            }
        }

        this.startButtonObject.changeText(Constants.goButton);
    }

}