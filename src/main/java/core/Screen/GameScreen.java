package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Boot;
import core.Controller.HeroController;
import core.Controller.PowerController;
import core.Controller.SpellController;
import core.Model.Hero;
import core.Model.Power;
import core.Model.Spell;
import core.Object.*;
import utils.Config;
import utils.ContactListenerHelper;
import utils.ObjectType;
import java.util.ArrayList;

public class GameScreen extends Screen {
    private final ArrayList<BulletObject> heroBullets;
    private final ArrayList<BulletObject> enemyBullets;
    private final ArrayList<Integer>      bulletCounters;
    private final ArrayList<Spell>        spells;
    private final TextBoxObject           experienceTextBox;
    private final TextBoxObject           moneyTextBox;
    private final TextBoxObject           rewardInfo;
    private final TextBoxObject           selectedSpellText;
    private final SpriteBatch             contentBatch;
    private final IconObject              moneyIcon;
    private final IconObject              heartIcon;
    private final IconObject              xpIcon;
    private final IconObject              bannerSpell;
    private final IconObject              chestsIcon;
    private final IconObject              enemyIcon;
    private final IconObject              spellIcon;
    private final IconObject              powerIcon;
    private final IconObject              bannerPower;
    private final IconObject              pauseBanner;
    private final TextBoxObject           powerTextBox;
    private final IconObject              ribbonIcon;
    private final TextBoxObject           openedChestsTextBox;
    private final TextBoxObject           enemiesKilledText;
    private final ButtonObject            resume;
    private final ButtonObject            restart;
    private final ButtonObject            quit;
    private final TextBoxObject           onPause;
    private final Power                   power;
    private final Music                   music;
    private Sound                         sound;
    private final int                     baseScore;
    private final int                     powerActiveTime;
    private final int                     powerRefuelTime;
    private final int                     level;
    private ArrayList<HealthBoxObject>    healthBoxes;
    private ArrayList<MoneyBoxObject>     moneyBoxes;
    private ArrayList<EnemyObject>        enemies;
    private HeroObject                    heroObject;
    private Hero                          hero;
    private boolean                       activeSpell;
    private boolean                       pause;
    private int                           heroMoney;
    private int                           heroExperience;
    private int                           openedChests;
    private int                           enemiesKilled;
    private int                           totalChests;
    private int                           totalEnemies;
    private int                           timer;
    private int                           powerTimer;
    private int                           enemyTimer;
    private int                           activeSpellIndex;
    private int                           powerStatus;

    public GameScreen(OrthographicCamera camera, int level, int baseScore) {
        super(camera,"levels/level" + level, true, level);
        this.getWorld().setContactListener( new ContactListenerHelper(this));

        this.contentBatch        = new SpriteBatch();
        this.heroMoney           = 0;
        this.heroExperience      = 0;
        this.openedChests        = 0;
        this.enemiesKilled       = 0;
        this.totalChests         = 0;
        this.totalEnemies        = 0;
        this.timer               = 0;
        this.powerTimer          = 0;
        this.enemyTimer          = 0;
        this.powerStatus         = 0;
        this.activeSpell         = false;
        this.pause               = false;
        this.activeSpellIndex    = -1;
        this.baseScore           = baseScore;
        this.level               = level;
        this.heroBullets         = new ArrayList<>();
        this.enemyBullets        = new ArrayList<>();
        this.bulletCounters      = new ArrayList<>();
        this.spells              = new SpellController().getActiveSpells();
        this.music               = Gdx.audio.newMusic(Gdx.files.internal("audio/" + (int)(Math.random() * Config.songsNumber + 1) + ".mp3"));
        this.quit                = new ButtonObject(Boot.bootInstance.getScreenWidth()/2, Boot.bootInstance.getScreenHeight()/2 - 128, "Quit");
        this.resume              = new ButtonObject(Boot.bootInstance.getScreenWidth()/2, Boot.bootInstance.getScreenHeight()/2 + 68, "Resume");
        this.restart             = new ButtonObject(Boot.bootInstance.getScreenWidth()/2, Boot.bootInstance.getScreenHeight()/2 - 28, "Restart");
        this.onPause             = new TextBoxObject("PAUSED", Boot.bootInstance.getScreenWidth()/2, Boot.bootInstance.getScreenHeight()/2 + 150, 'm');
        this.moneyTextBox        = new TextBoxObject( this.heroMoney + " bucks", 172, Boot.bootInstance.getScreenHeight() - 100, 's');
        this.moneyIcon           = new IconObject("coin", 48, Boot.bootInstance.getScreenHeight() - 100, 38, 38);
        this.ribbonIcon          = new IconObject("bigRibbon", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 205, 900, 64);
        this.experienceTextBox   = new TextBoxObject( this.heroExperience + " pts", 142, Boot.bootInstance.getScreenHeight() - 154, 's');
        this.heartIcon           = new IconObject("life100", 164, Boot.bootInstance.getScreenHeight() - 48, 273, 38);
        this.pauseBanner         = new IconObject("pauseBanner", Boot.bootInstance.getScreenWidth()/2, Boot.bootInstance.getScreenHeight()/2, 602, 602);
        this.rewardInfo          = new TextBoxObject("", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 's');
        this.xpIcon              = new IconObject("star", 48, Boot.bootInstance.getScreenHeight() - 154, 38, 38);
        this.chestsIcon          = new IconObject("chest", Boot.bootInstance.getScreenWidth() - 180, Boot.bootInstance.getScreenHeight() - 48, 38, 38);
        this.enemyIcon           = new IconObject("orangeEnemy", Boot.bootInstance.getScreenWidth() - 180, Boot.bootInstance.getScreenHeight() - 96, 38, 38);
        this.bannerSpell         = new IconObject("banner", Boot.bootInstance.getScreenWidth() - 220,  32, 400, 48);
        this.bannerPower         = new IconObject("banner", 230,  32, 400, 48);
        this.spellIcon           = new IconObject("defaultSpell", Boot.bootInstance.getScreenWidth() - 420,  32, 50, 50);
        this.powerIcon           = new IconObject("noPower", 32,  32, 50, 50);
        this.selectedSpellText   = new TextBoxObject("No spell selected", Boot.bootInstance.getScreenWidth() - 220,  32, 's');
        this.powerTextBox        = new TextBoxObject("No power", 230,  32, 's');
        this.power               = new PowerController().getActivePower();

        this.ribbonIcon.changeVisibility(false);
        this.music.play();
        this.music.setVolume(Config.inGameMusic);
        this.music.isLooping();

        if(power != null) {
            powerActiveTime = power.getActiveTime();
            powerRefuelTime = power.getRefuelTime();
            this.heroObject.setPower(power.getValue(), power.getType());
            this.powerIcon.setPowerIcon(this.power.getNameSlug());
            this.powerTextBox.setText(this.power.getName() + " ready");
        } else {
            powerActiveTime = 0;
            powerRefuelTime = 0;
        }

        for(Spell object: spells)
            this.bulletCounters.add(object.getBullets());

        if(this.enemies != null)
            this.totalEnemies += this.enemies.size();
        if(this.healthBoxes != null)
            this.totalChests  += this.healthBoxes.size();
        if(this.moneyBoxes != null)
            this.totalChests  += this.moneyBoxes.size();

        this.openedChestsTextBox = new TextBoxObject( this.openedChests + " / " + this.totalChests, Boot.bootInstance.getScreenWidth() - 80, Boot.bootInstance.getScreenHeight() - 48, 's');
        this.enemiesKilledText   = new TextBoxObject( this.enemiesKilled + " / " + this.totalEnemies, Boot.bootInstance.getScreenWidth() - 80, Boot.bootInstance.getScreenHeight() - 96, 's');
    }

    @Override
    public void update() {
        this.beforeWorldStepUpdate();
        this.gameTiming();

        this.getWorld().step(1/60f, 6, 2);
        this.cameraUpdate();

        this.batch.setProjectionMatrix(camera.combined);
        this.contentBatch.getProjectionMatrix();

        if(this.orthogonalTiledMapRenderer != null)
            this.orthogonalTiledMapRenderer.setView(camera);

        this.heroObject.update();

        if(this.pause) {
            this.pauseBanner.update();
            this.pressedOnPauseButtons();
        } else {
            this.pressedButtons();
            this.enemiesShooting();
            this.checkHealthStatus();
            this.passTheFinishLine();

            this.heroObject.checkUserInput();

            if(enemies != null)
                for(EnemyObject object: enemies)
                    object.update(this.heroObject.getX(), this.heroObject.getY());

            if(heroBullets != null)
                for(BulletObject object: heroBullets)
                    object.update();

            if(enemyBullets != null)
                for(BulletObject object: enemyBullets)
                    object.update();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        this.mainContentRender();
        batch.end();

        contentBatch.begin();
        this.auxiliaryContentRender();
        contentBatch.end();
    }

    @Override
    public void cameraUpdate() {
        Vector3 position = camera.position;
        float posX       = Boot.bootInstance.getScreenWidth() >> 1;
        float posY       = Boot.bootInstance.getScreenHeight() >> 1;
        float posHeroY   = heroObject.getBody().getPosition().y * Config.PPM * 1f;
        float posHeroX   = heroObject.getBody().getPosition().x * Config.PPM * 1f;
        float mapWidth   = this.getTileMapHelper().getMapWidth() - posX;

        if(posX < posHeroX)
            posX = posHeroX;

        if(posX > mapWidth)
            posX = mapWidth;

        if(posY < posHeroY)
            posY = posHeroY;

        position.x = Math.round(posX);
        position.y = Math.round(posY);

        camera.position.set(position);
        camera.update();
    }

    protected void pressedButtons() {
        this.heroSpellSelect();
        this.heroPowerSelect();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause = !this.pause;
            this.music.pause();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && this.activeSpell) {
            heroBullets.add(new BulletObject(this, this.heroObject.getX() + (this.hero.getWidth() >> 1), this.heroObject.getY() + (this.hero.getHeight() >> 1), this.hero.getHitPower(), this.spells.get(this.activeSpellIndex), this.heroObject.getDirection()));
            this.bulletCounters.set(this.activeSpellIndex, this.bulletCounters.get(this.activeSpellIndex) - 1);
            this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
            this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/gun.wav"));
            this.sound.setVolume(this.sound.play(), Config.inGameSound);
            if(this.bulletCounters.get(this.activeSpellIndex) <= 0) {
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/outOfPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.timer = 0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.rewardInfo.setText("You are out of " + this.spells.get(this.activeSpellIndex).getName() + "!");
                this.activeSpell = false;
                this.spellIcon.setIcon("defaultSpell");
                this.selectedSpellText.setText("No spell selected");
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            heroBullets.add(new BulletObject(this, this.heroObject.getX() + (this.hero.getWidth() >> 1), this.heroObject.getY() + (this.hero.getHeight() >> 1), this.hero.getHitPower(), this.hero.getSpeed(), this.heroObject.getDirection()));
            this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/gun.wav"));
            this.sound.setVolume(this.sound.play(), Config.inGameSound);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(this.heroObject.getDirection().contains("R"))
                this.heroObject.shootRight();
            else
                this.heroObject.shootLeft();
        }
    }

    private void beforeWorldStepUpdate() {
        if(enemies != null)
            for(EnemyObject object: enemies)
                if(object.isToDestroy() && object.isNotDestroyed()) {
                    object.safeDestroy();
                    this.heroMoney += object.getEnemy().getMoneyReward();
                    this.heroExperience += object.getEnemy().getMoneyReward();
                    this.moneyTextBox.setText(this.heroMoney + " bucks");
                    this.experienceTextBox.setText(this.heroExperience + " pts");
                    this.enemiesKilled ++;
                    this.enemiesKilledText.setText(this.enemiesKilled + " / " + this.totalEnemies);
                    this.timer =  0;
                    this.ribbonIcon.changeVisibility(true);
                    this.rewardInfo.setText("Enemy killed");
                    this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/enemyHit.wav"));
                    this.sound.setVolume(this.sound.play(), Config.inGameSound);
                }

        if(moneyBoxes != null)
            for(MoneyBoxObject object: moneyBoxes)
                if(object.isToDestroy() && !object.isDestroyed()) {
                    object.safeDestroy();
                    this.heroMoney += object.getReward().getValue();
                    this.heroExperience += object.getReward().getValue();
                    this.openedChests += 1;
                    this.openedChestsTextBox.setText(this.openedChests + " / " + this.totalChests);
                    this.moneyTextBox.setText(this.heroMoney + " bucks");
                    this.experienceTextBox.setText(this.heroExperience + " pts");
                    this.timer =  0;
                    this.ribbonIcon.changeVisibility(true);
                    this.rewardInfo.setText(object.getReward().getName());
                }

        if(healthBoxes != null)
            for(HealthBoxObject object: healthBoxes)
                if(object.isToDestroy() && !object.isDestroyed()) {
                    object.safeDestroy();
                    this.heroObject.addHealth(object.getReward().getValue());
                    this.openedChests += 1;
                    this.openedChestsTextBox.setText(this.openedChests + " / " + this.totalChests);
                    this.timer =  0;
                    this.ribbonIcon.changeVisibility(true);
                    this.rewardInfo.setText(object.getReward().getName());
                }

        if(heroBullets != null)
            for(BulletObject object: heroBullets)
                if(object.isToDestroy() && object.isNotDestroyed())
                    object.safeDestroy();

        if(enemyBullets != null)
            for(BulletObject object: enemyBullets)
                if(object.isToDestroy() && object.isNotDestroyed())
                    object.safeDestroy();
    }

    private void pressedOnPauseButtons() {

        if(this.restart.isJustPressed()) {
            this.music.dispose();
            Boot.bootInstance.setScreen(new GameScreen(this.camera, this.level, this.baseScore));
        }

        if(this.quit.isJustPressed()) {
            this.music.dispose();
            Boot.bootInstance.playDefaultMusic();
            Boot.bootInstance.setScreen(new LevelSelectorScreen(this.camera));
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || this.resume.isJustPressed()) {
            this.music.play();
            this.pause = !this.pause;
        }
    }

    private void gameTiming() {
        this.timer ++;
        this.enemyTimer ++;
        if(this.powerStatus == 1 || this.powerStatus == 2)
            this.powerTimer ++;
        if(this.enemyTimer >= 1000)
            this.enemyTimer = 0;

        if(this.timer > 70) {
            this.rewardInfo.setText("");
            this.ribbonIcon.setIcon("bigRibbon");
            this.ribbonIcon.changeVisibility(false);
            this.timer = 0;
        }

        if(this.powerTimer == this.powerActiveTime && this.powerStatus == 1) {
            this.powerTimer = 0;
            this.powerStatus = 2;
            this.powerIcon.setPowerIcon("offline" + this.power.getNameSlug());
            this.powerTextBox.setText(this.power.getName() + " off");
            this.heroObject.deactivatePower();
            this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/outOfPower.wav"));
            this.sound.setVolume(this.sound.play(), Config.inGameSound);
            this.timer =  0;
            this.ribbonIcon.changeVisibility(true);
            this.ribbonIcon.setIcon("bigBadRibbon");
            this.rewardInfo.setText("You are out of power!");
        }

        if(this.powerTimer == this.powerRefuelTime && this.powerStatus == 2) {
            this.powerTimer = 0;
            this.powerStatus = 0;
            this.powerIcon.setPowerIcon(this.power.getNameSlug());
            this.powerTextBox.setText(this.power.getName() + " ready");
        }
    }

    private void mainContentRender() {
        this.heroObject.render(this.batch);

        if(healthBoxes != null)
            for(HealthBoxObject object: healthBoxes)
                object.render(this.batch);

        if(moneyBoxes != null)
            for(MoneyBoxObject object: moneyBoxes)
                object.render(this.batch);

        if(heroBullets != null)
            for(BulletObject object: heroBullets)
                object.render(this.batch);

        if(enemyBullets != null)
            for(BulletObject object: enemyBullets)
                object.render(this.batch);

        if(enemies != null)
            for(EnemyObject object: enemies)
                object.render(this.batch);
    }

    private void auxiliaryContentRender() {
        this.enemyIcon.render(this.contentBatch);
        this.enemiesKilledText.render(this.contentBatch);
        this.moneyIcon.render(this.contentBatch);
        this.ribbonIcon.render(this.contentBatch);
        this.rewardInfo.render(this.contentBatch);
        this.heartIcon.render(this.contentBatch);
        this.xpIcon.render(this.contentBatch);
        this.chestsIcon.render(this.contentBatch);
        this.openedChestsTextBox.render(this.contentBatch);
        this.moneyTextBox.render(this.contentBatch);
        this.experienceTextBox.render(this.contentBatch);
        this.bannerSpell.render(this.contentBatch);
        this.bannerPower.render(this.contentBatch);
        this.spellIcon.render(this.contentBatch);
        this.powerIcon.render(this.contentBatch);
        this.selectedSpellText.render(this.contentBatch);
        this.powerTextBox.render(this.contentBatch);

        if(pause) {
            this.pauseBanner.render(this.contentBatch);
            this.onPause.render(this.contentBatch);
            this.restart.render(this.contentBatch);
            this.resume.render(this.contentBatch);
            this.quit.render(this.contentBatch);
        }
    }

    private void heroPowerSelect() {
        if(power != null)
            if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 0) {
                this.powerIcon.setPowerIcon("active" + this.power.getNameSlug());
                this.powerTextBox.setText(this.power.getName() + " on");
                this.heroObject.activatePower();
                this.powerTimer  = 0;
                this.powerStatus = 1;
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/power.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 1) {
                this.powerIcon.setPowerIcon(this.power.getNameSlug());
                this.powerTextBox.setText(this.power.getName() + " on hold");
                this.heroObject.deactivatePower();
                this.powerStatus = 3;
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/holdPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 3) {
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/power.wav"));
                this.powerIcon.setPowerIcon("active" + this.power.getNameSlug());
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.powerTextBox.setText(this.power.getName() + " on");
                this.heroObject.activatePower();
                this.powerStatus = 1;
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 2) {
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/outOfPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.timer =  0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.rewardInfo.setText("You are out of power!");
            }
    }

    private void heroSpellSelect() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q) && spells.size() >= 1)
            if(bulletCounters.get(0) > 0) {
                this.activeSpell = true;
                this.activeSpellIndex = 0;
                this.spellIcon.setSpellIcon(this.spells.get(this.activeSpellIndex).getNameSlug());
                this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/holdPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.timer =  0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigRibbon");
                this.rewardInfo.setText(this.spells.get(this.activeSpellIndex).getName() + " activated!");
            } else {
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/outOfPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.rewardInfo.setText("You don't have " + this.spells.get(0).getName() + " anymore");
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.ribbonIcon.changeVisibility(true);
                this.timer =  0;
            }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W) && spells.size() >= 2)
            if(bulletCounters.get(1) > 0) {
                this.activeSpell = true;
                this.activeSpellIndex = 1;
                this.spellIcon.setSpellIcon(this.spells.get(this.activeSpellIndex).getNameSlug());
                this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/holdPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.timer =  0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigRibbon");
                this.rewardInfo.setText(this.spells.get(this.activeSpellIndex).getName() + " activated!");
            } else {
                this.timer =  0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.rewardInfo.setText("You don't have " + this.spells.get(1).getName() + " anymore");
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/outOfPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
            }

        if(Gdx.input.isKeyJustPressed(Input.Keys.E) && spells.size() >= 3)
            if(bulletCounters.get(2) > 0) {
                this.activeSpell      = true;
                this.activeSpellIndex = 2;
                this.spellIcon.setSpellIcon(this.spells.get(this.activeSpellIndex).getNameSlug());
                this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/holdPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.timer =  0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigRibbon");
                this.rewardInfo.setText(this.spells.get(this.activeSpellIndex).getName() + " activated!");
            } else {
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/outOfPower.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
                this.rewardInfo.setText("You don't have " + this.spells.get(2).getName() + " anymore");
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.ribbonIcon.changeVisibility(true);
                this.timer = 0;
            }

        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.activeSpell = false;
            this.spellIcon.setIcon("defaultSpell");
            this.selectedSpellText.setText("No spell selected");
        }
    }

    private void enemiesShooting() {
        if(enemies != null)
            for(EnemyObject object: enemies)
                if(object.readyToShoot() && this.enemyTimer % (object.getEnemy().getId() * 5 + 48 ) == 0 && object.isNotDestroyed())
                    enemyBullets.add(new BulletObject(this, object.getX() + (object.getEnemy().getWidth() >> 1), object.getY() + (object.getEnemy().getHeight() >> 1), object.getEnemy().getHitPower(), object.getEnemy().getSpeed(), object.getDirection(), ObjectType.ENEMY_BULLET));
    }

    private void passTheFinishLine() {
        if(this.heroObject.getBody().getPosition().x > this.getTileMapHelper().getMapWidth() / Config.PPM) {
            this.music.dispose();
            Boot.bootInstance.playDefaultMusic();
            Boot.bootInstance.setScreen(new WonScreen(this.camera, this.heroMoney, this.heroExperience, this.heroObject.getHeroHealth(), this.baseScore, this.level));
        }
    }

    private void checkHealthStatus() {

        if(this.heroObject.getHeroHealth() > 75)
            this.heartIcon.setIcon("life100");
        else if(this.heroObject.getHeroHealth() > 51)
            this.heartIcon.setIcon("life75");
        else if(this.heroObject.getHeroHealth() > 30)
            this.heartIcon.setIcon("life50");
        else if(this.heroObject.getHeroHealth() > 16)
            this.heartIcon.setIcon("life25");
        else
            this.heartIcon.setIcon("life15");

        if(this.heroObject.getHeroHealth() <= 0 || this.heroObject.getY() + this.hero.getHeight() < 0) {
            this.music.dispose();
            Boot.bootInstance.playDefaultMusic();
            Boot.bootInstance.setScreen(new LostScreen(this.camera, this.level));
        }
    }

    public void addHealthBox(HealthBoxObject healthBox) {
        if(this.healthBoxes == null)
            this.healthBoxes = new ArrayList<>();

        this.healthBoxes.add(healthBox);
    }

    public void addMoneyBox(MoneyBoxObject moneyBox) {
        if(this.moneyBoxes == null)
            this.moneyBoxes = new ArrayList<>();

        this.moneyBoxes.add(moneyBox);
    }

    public void addEnemy(EnemyObject enemy) {
        if(this.enemies == null)
            this.enemies = new ArrayList<>();

        this.enemies.add(enemy);
    }

    public void addMainHero(float heroX, float heroY) {
        this.hero                = new HeroController().getMainHero();
        this.heroObject          = new HeroObject(this, this.hero, (int) heroX, (int) heroY);

    }

    public void moneyBoxContact(Fixture fixture) {
        for(MoneyBoxObject object: moneyBoxes)
            if(fixture == object.getFixture()){
                object.flaggedToDestroy();
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/moneyReward.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
            }
    }

    public void killerHeroContact() {
        this.heroObject.takeHit(this.heroObject.getHeroHealth());
    }

    public void healthBoxContact(Fixture fixture) {
        for(HealthBoxObject object: healthBoxes)
            if(fixture == object.getFixture()){
                object.flaggedToDestroy();
                this.sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/healthReward.wav"));
                this.sound.setVolume(this.sound.play(), Config.inGameSound);
            }
    }

    public void heroEnemyContact(Fixture fixture) {
        for(EnemyObject object: enemies)
            if(fixture == object.getFixture()) {
                this.heroObject.takeHit(object.getEnemy().getHitPower());
                object.takeDamage(this.hero.getHitPower());
            }
    }

    public void heroBulletEnemyContact(Fixture enemyFixture, Fixture bulletFixture) {
        int bulletHitPower = 0;

        for(BulletObject object: heroBullets)
            if(bulletFixture == object.getFixture()) {
                bulletHitPower = object.getHitPower();
                object.flaggedToDestroy();
            }

        for(EnemyObject object: enemies)
            if(enemyFixture == object.getFixture())
                object.takeDamage(bulletHitPower);
    }

    public void heroBulletEnemyBulletContact(Fixture objectFixture, Fixture heroBulletFixture) {
        for(BulletObject object: heroBullets)
            if(heroBulletFixture == object.getFixture())
                object.flaggedToDestroy();

        for(BulletObject object: enemyBullets)
            if(objectFixture == object.getFixture())
                object.flaggedToDestroy();
    }

    public void enemyBulletHeroContact(Fixture fixture) {
        for(BulletObject object: enemyBullets)
            if(fixture == object.getFixture()){
                object.flaggedToDestroy();
                this.heroObject.takeHit(object.getHitPower());
            }
    }

    public void killEnemy(Fixture enemyFixture) {
        for(EnemyObject object: enemies)
            if(enemyFixture == object.getFixture())
                object.takeDamage(object.getEnemy().getHealth());
    }

}