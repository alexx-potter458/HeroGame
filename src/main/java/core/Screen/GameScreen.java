package core.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import core.Boot;
import core.Controller.HeroController;
import core.Object.HeroObject;
import utils.Config;

public class GameScreen extends Screen {
    private final HeroObject heroObject;

    public GameScreen(OrthographicCamera camera, int level) {
        super(camera,"levels/level" + level, true);

        this.heroObject = new HeroObject(this, new HeroController().getMainHero(), 300, Boot.bootInstance.getScreenHeight()/2 + 86);
    }

    @Override
    public void update() {
        super.update();
        this.heroObject.update();
        this.heroObject.checkUserInput();

        this.passTheFinishLine();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        this.heroObject.render(this.batch);
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
}
