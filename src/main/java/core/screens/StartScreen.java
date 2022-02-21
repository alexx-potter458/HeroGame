package core.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import objects.Cloud;
import objects.TextBox;
import utils.Constants;

public class StartScreen extends Screen {

    private final Cloud[] clouds;
    private TextBox banner;

    public StartScreen(OrthographicCamera camera) {
        super(camera);
        this.clouds = new Cloud[4];
        this.clouds[0] = new Cloud(this);
        this.clouds[1] = new Cloud(this);
        this.clouds[2] = new Cloud(this);
        this.clouds[3] = new Cloud(this);
        this.banner = new TextBox(Constants.gameTitleCaps, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2), 'l');
    }

    public StartScreen(OrthographicCamera camera, int bannerY) {
        this(camera);
        this.banner.setY(bannerY);
    }

    @Override
    protected void update() {
        super.update();
        banner.update();
        for(Cloud cloud: clouds)
            cloud.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        banner.render(this.batch);

        for(Cloud cloud: clouds)
            cloud.render(this.batch);
        this.batch.end();

    }
}
