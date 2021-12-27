package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import org.lwjgl.opengl.GL20;

public class LoadingScreen extends Screen {
    
    public LoadingScreen(OrthographicCamera camera) {
        super(camera);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
//        Boot.bootInstance.setScreen(new StartScreen(this.camera));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.end();
    }
}
