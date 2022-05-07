package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Boot;
import core.Controller.HeroController;
import core.Object.HealthBoxObject;
import core.Object.HeroObject;
import core.Object.MoneyBoxObject;
import utils.Config;
import utils.ContactListenerHelper;
import java.util.ArrayList;

public class GameScreen extends Screen {
    private final HeroObject            heroObject;
    private ArrayList<HealthBoxObject>  healthBoxes;
    private ArrayList<MoneyBoxObject>   moneyBoxes;

    public GameScreen(OrthographicCamera camera, int level) {
        super(camera,"levels/level" + level, true);
        this.getWorld().setContactListener( new ContactListenerHelper(this));
        this.heroObject = new HeroObject(this, new HeroController().getMainHero(), 300, Boot.bootInstance.getScreenHeight()/2 + 86);
    }

    @Override
    public void update() {
        this.beforeWorldStepUpdate();

        this.getWorld().step(1/60f, 6, 2);
        this.cameraUpdate();

        this.batch.setProjectionMatrix(camera.combined);
        this.pressedButtons();

        if(this.orthogonalTiledMapRenderer != null)
            this.orthogonalTiledMapRenderer.setView(camera);

        this.heroObject.update();
        this.heroObject.checkUserInput();

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

    private void passTheFinishLine() {
        if(this.heroObject.getBody().getPosition().x > this.getTileMapHelper().getMapWidth() / Config.PPM)
            Boot.bootInstance.setScreen(new LevelSelectorScreen(this.camera));
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
        for(MoneyBoxObject object: moneyBoxes)
            if(object.isToDestroy())
                 object.safeDestroy();

        for(HealthBoxObject object: healthBoxes)
            if(object.isToDestroy())
                object.safeDestroy();
    }

}