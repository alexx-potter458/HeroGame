package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import core.Boot;
import org.lwjgl.opengl.GL20;
import utils.Config;
import utils.TileMapHelper;

public abstract  class Screen extends ScreenAdapter {
    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    protected OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private boolean gameMode;
    private TileMapHelper tileMapHelper;

    public Screen(OrthographicCamera camera) {
        this.camera             = camera;
        this.camera.position.set(new Vector3(Boot.bootInstance.getScreenWidth() >> 1, Boot.bootInstance.getScreenHeight() >> 1, 0));
        this.batch              = new SpriteBatch();
        this.world              = new World(new Vector2(0,-30), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public Screen(OrthographicCamera camera, String mapPath) {
        this(camera);
        this.gameMode                   = false;
        this.tileMapHelper              = new TileMapHelper(this, false);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(mapPath);
    }

    public Screen(OrthographicCamera camera, String mapPath, boolean gameMode) {
        this(camera);
        this.gameMode                   = gameMode;
        this.tileMapHelper              = new TileMapHelper(this, true);
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(mapPath);
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(Config.bgColor.r, Config.bgColor.g, Config.bgColor.b, Config.bgColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(this.orthogonalTiledMapRenderer != null)
            this.orthogonalTiledMapRenderer.render();

        this.batch.begin();
        this.batch.end();

        this.box2DDebugRenderer.render(world, camera.combined.scl(Config.PPM));
    }

    protected void update() {
        this.world.step(1/60f, 6, 2);
        this.cameraUpdate();

        this.batch.setProjectionMatrix(camera.combined);
        this.pressedButtons();

        if(this.orthogonalTiledMapRenderer != null)
            this.orthogonalTiledMapRenderer.setView(camera);
    }

    protected void cameraUpdate() {
        new Vector3(Boot.bootInstance.getScreenWidth() >> 1, Boot.bootInstance.getScreenHeight() >> 1, 0);
        camera.update();
    }

    public World getWorld() {
        return world;
    }

    public TileMapHelper getTileMapHelper() {
        return tileMapHelper;
    }

    private void pressedButtons() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

}
