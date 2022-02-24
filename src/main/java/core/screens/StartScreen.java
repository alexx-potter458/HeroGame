package core.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import objects.Clouds;
import objects.TextBox;
import utils.Constants;

public class StartScreen extends Screen {
    private final Clouds clouds;
    private final TextBox banner;

    public StartScreen(OrthographicCamera camera) {
        super(camera);
        this.clouds = new Clouds(this);
        this.banner = new TextBox(Constants.gameTitleCaps, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2), 'l');
    }

    public StartScreen(OrthographicCamera camera, int bannerY) {
        this(camera);
        this.banner.setY(bannerY);
    }

    @Override
    protected void update() {
        super.update();
        this.clouds.update();
        this.banner.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();

        this.clouds.render(this.batch);
        this.banner.render(this.batch);

        this.batch.end();
    }
}
