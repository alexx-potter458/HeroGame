package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.screens.StartScreen;

public class Boot extends Game {

    public static Boot bootInstance;
    private int screenWidth;
    private int screenHeight;
    private OrthographicCamera camera;

    public Boot() {
        bootInstance = this;
    }

    @Override
    public void create() {
        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        setScreen(new StartScreen(camera));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
}
