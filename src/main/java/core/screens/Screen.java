package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import core.Boot;
import org.lwjgl.opengl.GL20;
import utils.Config;

public abstract  class Screen extends ScreenAdapter {

    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    private final World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public Screen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.bootInstance.getScreenWidth() >> 1, Boot.bootInstance.getScreenHeight() >> 1, 0));

        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false);

        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(137/255f, 207/255f, 240/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(Config.PPM));
    }

    protected void update() {
        this.world.step(1/60f, 6, 2);
        this.cameraUpdate();
        this.batch.setProjectionMatrix(camera.combined);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void cameraUpdate() {
        this.camera.position.set(new Vector3(Boot.bootInstance.getScreenWidth() >> 1, Boot.bootInstance.getScreenHeight() >> 1, 0));
        camera.update();
    }

    public World getWorld() {
        return world;
    }
}
