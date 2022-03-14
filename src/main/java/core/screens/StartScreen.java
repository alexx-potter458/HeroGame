package core.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import core.Boot;
import objects.Clouds;
import objects.TextBox;
import utils.Constants;
import utils.TileMapHelper;

public class StartScreen extends Screen {
    private final Clouds clouds;
    private final TextBox banner;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public TileMapHelper tileMapHelper;

    public StartScreen(OrthographicCamera camera) {
        super(camera);
        this.clouds = new Clouds(this);
        this.banner = new TextBox(Constants.gameTitleCaps, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2), 'l');

        this.tileMapHelper = new TileMapHelper();
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap();
    }

    public StartScreen(OrthographicCamera camera, int bannerY) {
        this(camera);
        this.banner.setY(bannerY);
    }

    @Override
    protected void update() {
        super.update();
        orthogonalTiledMapRenderer.setView(camera);
        this.clouds.update();
        this.banner.update();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        orthogonalTiledMapRenderer.render();
        this.batch.begin();
        this.clouds.render(this.batch);
        this.banner.render(this.batch);

        this.batch.end();
    }
}
