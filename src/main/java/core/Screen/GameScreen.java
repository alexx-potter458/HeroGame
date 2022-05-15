package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Boot;
import core.Controller.HeroController;
import core.Model.Hero;
import core.Object.*;
import utils.Config;
import utils.ContactListenerHelper;
import java.util.ArrayList;

public class GameScreen extends Screen {
    private final HeroObject            heroObject;
    private final Hero                  hero;
    private int                         heroHealth;
    private int                         heroMoney;
    private int                         heroExperience;
    private int                         openedChests;
    private int                         totalChests;
    private ArrayList<HealthBoxObject>  healthBoxes;
    private ArrayList<MoneyBoxObject>   moneyBoxes;
    private final TextBoxObject         experienceTextBox;
    private final TextBoxObject         healthTextBox;
    private final TextBoxObject         moneyTextBox;
    private final SpriteBatch           contentBatch;
    private final IconObject            moneyIcon;
    private final IconObject            heartIcon;
    private final IconObject            xpIcon;
    private final IconObject            chestsIcon;
    private final TextBoxObject         openedChestsTextBox;
    private final int                   baseScore;

    public GameScreen(OrthographicCamera camera, int level, int baseScore) {
        super(camera,"levels/level" + level, true, level);
        this.getWorld().setContactListener( new ContactListenerHelper(this));

        this.contentBatch        = new SpriteBatch();
        this.hero                = new HeroController().getMainHero();
        this.heroHealth          = this.hero.getBaseHealth();
        this.heroMoney           = 0;
        this.heroExperience      = 0;
        this.baseScore           = baseScore;
        this.heroObject          = new HeroObject(this, this.hero, 300, Boot.bootInstance.getScreenHeight()/2 + 86);
        this.moneyTextBox        = new TextBoxObject( this.heroMoney + " bucks", 172, Boot.bootInstance.getScreenHeight() - 100, 's');
        this.moneyIcon           = new IconObject("coin", 48, Boot.bootInstance.getScreenHeight() - 100, 38, 38);
        this.experienceTextBox   = new TextBoxObject( this.heroExperience + " pts", 142, Boot.bootInstance.getScreenHeight() - 154, 's');
        this.heartIcon           = new IconObject("heart", 48, Boot.bootInstance.getScreenHeight() - 48, 38, 38);
        this.healthTextBox       = new TextBoxObject( heroHealth + " / " + this.hero.getBaseHealth(), 172, Boot.bootInstance.getScreenHeight() - 48, 's');
        this.xpIcon              = new IconObject("star", 48, Boot.bootInstance.getScreenHeight() - 154, 38, 38);
        this.openedChests        = 0;
        this.chestsIcon          = new IconObject("chest", Boot.bootInstance.getScreenWidth() - 180, Boot.bootInstance.getScreenHeight() - 48, 38, 38);
        this.totalChests         = 0;

        if(this.healthBoxes != null)
            this.totalChests += this.healthBoxes.size();
        if(this.moneyBoxes != null)
            this.totalChests += this.moneyBoxes.size();

        this.openedChestsTextBox = new TextBoxObject( this.openedChests + " / " + this.totalChests, Boot.bootInstance.getScreenWidth() - 80, Boot.bootInstance.getScreenHeight() - 48, 's');
    }

    @Override
    public void update() {
        this.beforeWorldStepUpdate();

        this.getWorld().step(1/60f, 6, 2);
        this.cameraUpdate();

        this.batch.setProjectionMatrix(camera.combined);
        this.contentBatch.getProjectionMatrix();
        this.pressedButtons();

        if(this.orthogonalTiledMapRenderer != null)
            this.orthogonalTiledMapRenderer.setView(camera);

        this.heroObject.update();
        this.healthTextBox.update();
        this.heroObject.checkUserInput();

        this.checkHealthStatus();
        this.passTheFinishLine();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        this.heroObject.render(this.batch);

        if(healthBoxes != null)
            for(HealthBoxObject object: healthBoxes)
                object.render(this.batch);

        if(moneyBoxes != null)
            for(MoneyBoxObject object: moneyBoxes)
                object.render(this.batch);

        batch.end();

        contentBatch.begin();
        this.moneyIcon.render(this.contentBatch);
        this.heartIcon.render(this.contentBatch);
        this.xpIcon.render(this.contentBatch);
        this.chestsIcon.render(this.contentBatch);
        this.openedChestsTextBox.render(this.contentBatch);
        this.healthTextBox.render(this.contentBatch);
        this.moneyTextBox.render(this.contentBatch);
        this.experienceTextBox.render(this.contentBatch);
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

        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
            Boot.bootInstance.setScreen(new LobbyScreen(this.camera));
    }

    private void passTheFinishLine() {
        if(this.heroObject.getBody().getPosition().x > this.getTileMapHelper().getMapWidth() / Config.PPM)
            Boot.bootInstance.setScreen(new WonScreen(this.camera, this.heroMoney, this.heroExperience, this.heroHealth, this.baseScore));
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

    private void checkHealthStatus() {
        if(this.heroHealth <= 0)
            Boot.bootInstance.setScreen(new LostScreen(this.camera));
    }

    public void moneyBoxContact(Fixture fixture) {
        for(MoneyBoxObject object: moneyBoxes)
            if(fixture == object.getFixture())
                 object.flaggedToDestroy();
    }

    public void healthBoxContact(Fixture fixture) {
        for(HealthBoxObject object: healthBoxes) {
            if(fixture == object.getFixture()){
                object.flaggedToDestroy();
            }
        }
    }

    private void beforeWorldStepUpdate() {
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
                }

        if(healthBoxes != null)
            for(HealthBoxObject object: healthBoxes)
                if(object.isToDestroy() && !object.isDestroyed()) {
                    object.safeDestroy();
                    this.heroHealth += object.getReward().getValue();
                    this.openedChests += 1;
                    this.openedChestsTextBox.setText(this.openedChests + " / " + this.totalChests);
                    this.healthTextBox.setText(heroHealth + " / " + this.hero.getBaseHealth());
                }
    }

}