package core.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import core.Boot;
import core.Object.TextBoxObject;
import org.lwjgl.opengl.GL20;
import utils.Config;
import utils.Constants;

public class LoadingScreen extends Screen {
    private final Color backgroundColor;
    private final TextBoxObject banner;
    private float fontOpacity = 0f;
    private float fontOpacityChanger = 0.01f;
    private int timer = 0;

    public LoadingScreen(OrthographicCamera camera) {
        super(camera);
        this.backgroundColor = new Color(0,0,0,1);

        this.banner = new TextBoxObject(Constants.loadingScreenStudioText, (Boot.bootInstance.getScreenWidth()/2),  (Boot.bootInstance.getScreenHeight()/2), 'm');
        banner.setFontOpacity(this.fontOpacity);
    }

    @Override
    protected void update() {
        super.update();
        banner.update();

        this.fontOpacity += this.fontOpacityChanger;
        this.banner.setFontOpacity(this.fontOpacity);
        this.timer ++;

        if(this.fontOpacity > 1.2  && this.timer < 200) {
            this.fontOpacityChanger = -0.02f;
        }

        if(this.fontOpacity < 0.001 ) {
            this.fontOpacityChanger = 0;
        }

        if(this.timer == 250) {
            this.fontOpacity = 0f;
            this.fontOpacityChanger = 0.02f;
            this.banner.changeFontSize('l');
            String text2 = Constants.gameTitleCaps;
            this.banner.setTextAndOpacity(text2, this.fontOpacity);
        }

        if(timer > 250 && timer <= 450) {
            if(Config.time.equals(Constants.dayValue))
                this.backgroundColor.add(0.685f/255f,1.035f/255f, 1.2f/255f,0);
            else
                this.backgroundColor.add(0.055f/255f,0.08f/255f, 0.19f/255f,0);
            banner.moveY(1);
        }

        if(this.timer == 500) {
            Boot.bootInstance.setScreen(new StartScreen(this.camera, this.banner.getY()));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        banner.render(this.batch);
        batch.end();
    }
}
