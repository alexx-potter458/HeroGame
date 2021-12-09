package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import core.Boot;
import core.screens.StartScreen;

public class Cloud {
    private Body body;
    private float x;
    private float y;
    private float speed;
    private float velX;
    private int width;
    private int height;
    private Texture texture;
    private StartScreen gameScreen;

    public Cloud(StartScreen screen) {
        this.x = Boot.bootInstance.getScreenWidth() >> 1;
        this.y = Boot.bootInstance.getScreenHeight() >> 1;
        this.speed = 3;
        this.width = 320;
        this.height = 120;
        this.velX = -1;

    }
}
