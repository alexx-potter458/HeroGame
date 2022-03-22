package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import core.Screen.LoadingScreen;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Boot extends Game {

    public static Boot bootInstance;
    private int screenWidth;
    private int screenHeight;
    BitmapFont fontSmall;
    BitmapFont fontMedium;
    BitmapFont fontLarge;

    public Boot() {
        bootInstance = this;
    }

    @Override
    public void create() {
        this.screenHeight = Gdx.graphics.getHeight();
        this.screenWidth = Gdx.graphics.getWidth();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/mainFont.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();

        parameter.borderWidth = 3f;
        parameter.borderColor = Color.BLACK;

        parameter.size = 16;
        fontSmall = generator.generateFont(parameter);

        parameter.size = 32;
        fontMedium = generator.generateFont(parameter);

        parameter.size = 64;
        fontLarge = generator.generateFont(parameter);

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        setScreen(new LoadingScreen(camera));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public BitmapFont getFontSmall() { return this.fontSmall; }

    public BitmapFont getFontMedium() { return this.fontMedium; }

    public BitmapFont getFontLarge() { return this.fontLarge; }
}
