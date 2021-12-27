package core.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import objects.Cloud;

public class StartScreen extends Screen {

    private Cloud[] clouds;

    public StartScreen(OrthographicCamera camera) {
        super(camera);
        this.clouds = new Cloud[4];
        this.clouds[0] = new Cloud(this);
        this.clouds[1] = new Cloud(this);
        this.clouds[2] = new Cloud(this);
        this.clouds[3] = new Cloud(this);
    }

    @Override
    protected void update() {
        super.update();
        for(Cloud cloud: clouds)
            cloud.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        for(Cloud cloud: clouds)
            cloud.render(this.batch);
        this.batch.end();

    }
}
