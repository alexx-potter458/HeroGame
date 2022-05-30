package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Controller.LevelController;
import core.Controller.UserController;
import core.Model.Level;
import core.Object.ButtonObject;
import core.Object.IconObject;
import core.Object.TextBoxObject;
import utils.Constants;

public class WonScreen extends Screen {
    private final TextBoxObject pageTitle;
    private final TextBoxObject rewardsFromHealth;
    private final TextBoxObject rewards;
    private final TextBoxObject rewardsFromLevelPass;
    private final TextBoxObject allMoney;
    private final TextBoxObject allExperience;
    private final int           moneyFromHealth;
    private final int           experienceFromHealth;
    private final int           experienceFromLevel;
    private final int           moneyFormLevel;
    private final int           money;
    private final int           experience;
    private final IconObject    moneyIcon;
    private final IconObject    ribbonIcon;
    private final IconObject    xpIcon;
    private final ButtonObject  backButtonObject;
    private final ButtonObject  nextLvlButtonObject;
    private final ButtonObject  sameLvlButtonObject;
    private final int           level;

    public WonScreen(OrthographicCamera camera, int xp, int money, int heroHealth, int baseScore, int level) {
        super(camera, "startScreen/map");
        this.moneyFromHealth      = heroHealth / 10;
        this.experienceFromHealth = heroHealth >> 1;
        this.experienceFromLevel  = baseScore;
        this.moneyFormLevel       = baseScore >> 2;
        this.money                = money;
        this.level                = level;
        this.experience           = xp;
        this.ribbonIcon           = new IconObject("bigRibbon", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 298, 900, 80);
        this.pageTitle            = new TextBoxObject(Constants.Won, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 300, 'l');
        this.allMoney             = new TextBoxObject("+" + (this.money + this.moneyFormLevel + this.moneyFromHealth), (Boot.bootInstance.getScreenWidth()/2) + 200,  (Boot.bootInstance.getScreenHeight()/2) - 144, 'm');
        this.allExperience        = new TextBoxObject("+" + (this.experience + this.experienceFromLevel + this.experienceFromHealth), (Boot.bootInstance.getScreenWidth()/2) - 110,  (Boot.bootInstance.getScreenHeight()/2) - 144, 'm');
        this.rewards              = new TextBoxObject(" You got: +" + this.money + " bucks \n                +" + this.experience + " pts", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 170, 's');
        this.rewardsFromLevelPass = new TextBoxObject("Level pass: +" + this.moneyFormLevel + " bucks \n                      +" + this.experienceFromLevel + " pts", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) + 70, 's');
        this.rewardsFromHealth    = new TextBoxObject("     Health: +" + this.moneyFromHealth + " bucks \n                   +" + this.experienceFromHealth + " pts", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2) - 20, 's');
        this.moneyIcon            = new IconObject("coin", (Boot.bootInstance.getScreenWidth()/2) + 110, Boot.bootInstance.getScreenHeight() / 2 - 144, 48, 48);
        this.xpIcon               = new IconObject("star", (Boot.bootInstance.getScreenWidth()/2) - 200, Boot.bootInstance.getScreenHeight() / 2 - 144, 48, 48);
        this.backButtonObject     = new ButtonObject((Boot.bootInstance.getScreenWidth()/2), 160, "Levels");
        this.nextLvlButtonObject  = new ButtonObject((Boot.bootInstance.getScreenWidth()/2 + 300), 160, "Next level");
        this.sameLvlButtonObject  = new ButtonObject((Boot.bootInstance.getScreenWidth()/2 - 300), 160, "Restart");

        this.saveProgress();
    }

    @Override
    protected void update() {
        super.update();
        this.pageTitle.update();
        this.rewards.update();
        this.rewardsFromHealth.update();
        this.rewardsFromHealth.update();
        this.allExperience.update();
        this.allMoney.update();
        this.xpIcon.update();
        this.moneyIcon.update();
        this.backButtonObject.update();
        this.nextLvlButtonObject.update();
        this.sameLvlButtonObject.update();

        this.buttonsPressed();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.batch.begin();
        this.ribbonIcon.render(this.batch);
        this.backButtonObject.render(this.batch);
        this.pageTitle.render(this.batch);
        this.rewards.render(this.batch);
        this.rewardsFromHealth.render(this.batch);
        this.rewardsFromLevelPass.render(this.batch);
        this.allMoney.render(this.batch);
        this.allExperience.render(this.batch);
        this.xpIcon.render(this.batch);
        this.moneyIcon.render(this.batch);
        this.sameLvlButtonObject.render(this.batch);
        this.nextLvlButtonObject.render(this.batch);
        this.batch.end();
    }

    private void saveProgress() {
        int moneyToSave      = this.money + this.moneyFormLevel + this.moneyFromHealth;
        int experienceToSave = this.experience + this.experienceFromLevel + this.experienceFromHealth;
        new UserController().addProgress(moneyToSave, experienceToSave);
        new LevelController().unlockNextLevel(this.level);
    }

    private void buttonsPressed() {
        if(this.backButtonObject.isJustPressed())
            Boot.bootInstance.setScreen(new LevelSelectorScreen(this.camera));

        if(this.sameLvlButtonObject.isJustPressed()) {
            Level levelObject = new LevelController().getLevelById(level);
            Boot.bootInstance.pauseDefaultMusic();
            Boot.bootInstance.setScreen(new GameScreen(this.camera, levelObject.getId(), levelObject.getBaseScore()));
        }

        if(this.nextLvlButtonObject.isJustPressed()) {
            Level levelObject = new LevelController().getNextLevelById(level);
            Boot.bootInstance.pauseDefaultMusic();
            Boot.bootInstance.setScreen(new GameScreen(this.camera, levelObject.getId(), levelObject.getBaseScore()));
        }
    }

}