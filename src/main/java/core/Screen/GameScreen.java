package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private final HeroObject              heroObject;
    private final Hero                    hero;
    private int                           heroMoney;
    private int                           heroExperience;
    private int                           openedChests;
    private int                           enemiesKilled;
    private int                           totalChests;
    private int                           totalEnemies;
    private ArrayList<HealthBoxObject>    healthBoxes;
    private ArrayList<MoneyBoxObject>     moneyBoxes;
    private ArrayList<EnemyObject>        enemies;
    private final ArrayList<BulletObject> heroBullets;
    private final ArrayList<BulletObject> enemyBullets;
    private final ArrayList<Integer>      bulletCounters;
    private final ArrayList<Spell>        spells;
    private final TextBoxObject           experienceTextBox;
    private final TextBoxObject           healthTextBox;
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
    private final TextBoxObject           powerTextBox;
    private final IconObject              ribbonIcon;
    private final TextBoxObject           openedChestsTextBox;
    private final TextBoxObject           enemiesKilledText;
    private final int                     baseScore;
    private int                           timer;
    private int                           powerTimer;
    private int                           enemyTimer;
    private int                           activeSpellIndex;
    private boolean                       activeSpell;
    private int                           powerStatus;
    private final int                     powerActiveTime;
    private final int                     powerRefuelTime;
    private final Power                   power;


    public GameScreen(OrthographicCamera camera, int level, int baseScore) {
        super(camera,"levels/level" + level, true, level);
        this.getWorld().setContactListener( new ContactListenerHelper(this));

        this.contentBatch        = new SpriteBatch();
        this.hero                = new HeroController().getMainHero();
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
        this.activeSpellIndex    = -1;
        this.baseScore           = baseScore;
        this.heroBullets         = new ArrayList<>();
        this.enemyBullets        = new ArrayList<>();
        this.bulletCounters      = new ArrayList<>();
        this.spells              = new SpellController().getActiveSpells();
        this.heroObject          = new HeroObject(this, this.hero, 300, Boot.bootInstance.getScreenHeight()/2 + 86);
        this.moneyTextBox        = new TextBoxObject( this.heroMoney + " bucks", 172, Boot.bootInstance.getScreenHeight() - 100, 's');
        this.moneyIcon           = new IconObject("coin", 48, Boot.bootInstance.getScreenHeight() - 100, 38, 38);
        this.ribbonIcon          = new IconObject("bigRibbon", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 205, 900, 64);
        this.experienceTextBox   = new TextBoxObject( this.heroExperience + " pts", 142, Boot.bootInstance.getScreenHeight() - 154, 's');
        this.heartIcon           = new IconObject("heart", 48, Boot.bootInstance.getScreenHeight() - 48, 38, 38);
        this.healthTextBox       = new TextBoxObject( heroObject.getHeroHealth() + " / " + this.hero.getBaseHealth(), 172, Boot.bootInstance.getScreenHeight() - 48, 's');
        this.rewardInfo          = new TextBoxObject("", (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()) - 200, 's');
        this.xpIcon              = new IconObject("star", 48, Boot.bootInstance.getScreenHeight() - 154, 38, 38);
        this.chestsIcon          = new IconObject("chest", Boot.bootInstance.getScreenWidth() - 180, Boot.bootInstance.getScreenHeight() - 48, 38, 38);
        this.enemyIcon           = new IconObject("orangeEnemy", Boot.bootInstance.getScreenWidth() - 180, Boot.bootInstance.getScreenHeight() - 96, 38, 38);
        this.bannerSpell         = new IconObject("banner", Boot.bootInstance.getScreenWidth() - 220,  32, 400, 48);
        this.bannerPower         = new IconObject("banner", 220,  32, 400, 48);
        this.spellIcon           = new IconObject("defaultSpell", Boot.bootInstance.getScreenWidth() - 420,  32, 50, 50);
        this.powerIcon           = new IconObject("noPower", 22,  32, 50, 50);
        this.selectedSpellText   = new TextBoxObject("No spell selected", Boot.bootInstance.getScreenWidth() - 220,  32, 's');
        this.powerTextBox        = new TextBoxObject("No power", 220,  32, 's');
        this.power               = new PowerController().getActivePower();
        this.ribbonIcon.changeVisibility(false);

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

        this.pressedButtons();
        this.enemiesShooting();
        this.checkHealthStatus();
        this.passTheFinishLine();

        this.heroObject.update();
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

    @Override
    protected void pressedButtons() {
        super.pressedButtons();
        this.heroSpellSelect();
        this.heroPowerSelect();

        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && this.activeSpell) {
            heroBullets.add(new BulletObject(this, this.heroObject.getX() + (this.hero.getWidth() >> 1), this.heroObject.getY() + (this.hero.getHeight() >> 1), this.hero.getHitPower(), this.spells.get(this.activeSpellIndex), this.heroObject.getDirection()));
            this.bulletCounters.set(this.activeSpellIndex, this.bulletCounters.get(this.activeSpellIndex) - 1);
            this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
            if(this.bulletCounters.get(this.activeSpellIndex) <= 0) {
                this.activeSpell = false;
                this.spellIcon.setIcon("defaultSpell");
                this.selectedSpellText.setText("No spell selected");
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            heroBullets.add(new BulletObject(this, this.heroObject.getX() + (this.hero.getWidth() >> 1), this.heroObject.getY() + (this.hero.getHeight() >> 1), this.hero.getHitPower(), this.hero.getSpeed(), this.heroObject.getDirection()));
    }

    private void beforeWorldStepUpdate() {
        if(enemies != null)
            for(EnemyObject object: enemies)
                if(object.isToDestroy() && object.isNotDestroyed()) {
                    object.safeDestroy();
                    this.heroMoney += object.getEnemy().getMoneyReward();
                    this.heroExperience += object.getEnemy().getMoneyReward() >> 2;
                    this.moneyTextBox.setText(this.heroMoney + " bucks");
                    this.experienceTextBox.setText(this.heroExperience + " pts");
                    this.enemiesKilled ++;
                    this.enemiesKilledText.setText(this.enemiesKilled + " / " + this.totalEnemies);
                    this.timer =  0;
                    this.ribbonIcon.changeVisibility(true);
                    this.rewardInfo.setText("Enemy killed");
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
                    this.healthTextBox.setText(this.heroObject.getHeroHealth() + " / " + this.hero.getBaseHealth());
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
        this.healthTextBox.render(this.contentBatch);
        this.moneyTextBox.render(this.contentBatch);
        this.experienceTextBox.render(this.contentBatch);
        this.bannerSpell.render(this.contentBatch);
        this.bannerPower.render(this.contentBatch);
        this.spellIcon.render(this.contentBatch);
        this.powerIcon.render(this.contentBatch);
        this.selectedSpellText.render(this.contentBatch);
        this.powerTextBox.render(this.contentBatch);
    }

    private void heroPowerSelect() {
        if(power != null)
            if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 0) {
                this.powerIcon.setPowerIcon("active" + this.power.getNameSlug());
                this.powerTextBox.setText(this.power.getName() + " on");
                this.heroObject.activatePower();
                this.powerTimer  = 0;
                this.powerStatus = 1;
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 1) {
                this.powerIcon.setPowerIcon(this.power.getNameSlug());
                this.powerTextBox.setText(this.power.getName() + " on hold");
                this.heroObject.deactivatePower();
                this.powerStatus = 3;
            } else if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && this.powerStatus == 3) {
                this.powerIcon.setPowerIcon("active" + this.power.getNameSlug());
                this.powerTextBox.setText(this.power.getName() + " on");
                this.heroObject.activatePower();
                this.powerStatus = 1;
            }
    }

    private void heroSpellSelect() {
        if(Gdx.input.isKeyPressed(Input.Keys.Q) && spells.size() >= 1)
            if(bulletCounters.get(0) > 0) {
                this.activeSpell = true;
                this.activeSpellIndex = 0;
                this.spellIcon.setSpellIcon(this.spells.get(this.activeSpellIndex).getNameSlug());
                this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
            } else {
                this.rewardInfo.setText("You don't have " + this.spells.get(0).getName() + " anymore");
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.ribbonIcon.changeVisibility(true);
                this.timer =  0;
            }

        if(Gdx.input.isKeyPressed(Input.Keys.W) && spells.size() >= 2)
            if(bulletCounters.get(1) > 0) {
                this.activeSpell = true;
                this.activeSpellIndex = 1;
                this.spellIcon.setSpellIcon(this.spells.get(this.activeSpellIndex).getNameSlug());
                this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
            } else {
                this.timer =  0;
                this.ribbonIcon.changeVisibility(true);
                this.ribbonIcon.setIcon("bigBadRibbon");
                this.rewardInfo.setText("You don't have " + this.spells.get(1).getName() + " anymore");
            }

        if(Gdx.input.isKeyPressed(Input.Keys.E) && spells.size() >= 3)
            if(bulletCounters.get(2) > 0) {
                this.activeSpell      = true;
                this.activeSpellIndex = 2;
                this.spellIcon.setSpellIcon(this.spells.get(this.activeSpellIndex).getNameSlug());
                this.selectedSpellText.setText(this.spells.get(this.activeSpellIndex).getName() + " (" + this.bulletCounters.get(this.activeSpellIndex) + ")");
            } else {
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
        if(this.heroObject.getBody().getPosition().x > this.getTileMapHelper().getMapWidth() / Config.PPM)
            Boot.bootInstance.setScreen(new WonScreen(this.camera, this.heroMoney, this.heroExperience, this.heroObject.getHeroHealth(), this.baseScore));
    }

    private void checkHealthStatus() {
        if(this.heroObject.getHeroHealth() <= 0)
            Boot.bootInstance.setScreen(new LostScreen(this.camera));
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

    public void moneyBoxContact(Fixture fixture) {
        for(MoneyBoxObject object: moneyBoxes)
            if(fixture == object.getFixture())
                 object.flaggedToDestroy();
    }

    public void healthBoxContact(Fixture fixture) {
        for(HealthBoxObject object: healthBoxes)
            if(fixture == object.getFixture())
                object.flaggedToDestroy();
    }

    public void heroEnemyContact(Fixture fixture) {
        for(EnemyObject object: enemies)
            if(fixture == object.getFixture()) {
                this.heroObject.takeHit(object.getEnemy().getHitPower());
                object.takeDamage(this.hero.getHitPower());
                this.healthTextBox.setText(this.heroObject.getHeroHealth() + " / " + this.hero.getBaseHealth());
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
                this.healthTextBox.setText(heroObject.getHeroHealth() + " / " + this.hero.getBaseHealth());
            }
    }

}