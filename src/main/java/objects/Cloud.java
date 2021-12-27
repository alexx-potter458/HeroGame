package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Boot;
import core.screens.StartScreen;
import utils.BodyHelper;
import utils.Constants;
import utils.ObjectType;

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
        this.x       = this.getRandomCloudX();
        this.y       = this.getRandomCloudY();
        this.speed   = this.getRandomCloudSpeed();
        this.width   = 240;
        this.height  = 135;
        this.body    = BodyHelper.createBody(x, y, width, height, 0, 1, screen.getWorld(), ObjectType.CLOUD);
        this.texture = new Texture("textures/cloud.png");
    }

    public void update() {
        this.x = body.getPosition().x * Constants.PPM - (width >> 1);
        this.y = body.getPosition().y * Constants.PPM - (height >> 1);

        this.body.setLinearVelocity( speed,  0);

        if(x < -250) {
            this.body.setTransform((Boot.bootInstance.getScreenWidth()) / Constants.PPM, getRandomCloudY() / Constants.PPM, 0);
            this.speed = getRandomCloudSpeed();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    private int getRandomCloudSpeed() {
        return -((int) Math.floor(Math.random() * 3)) - 3;
    }
    private int getRandomCloudY() {
        return Boot.bootInstance.getScreenHeight() - 75 - ((int) Math.floor(Math.random() * Boot.bootInstance.getScreenHeight() / 4));
    }
    private int getRandomCloudX() {
        return (int) Math.floor(Math.random() * Boot.bootInstance.getScreenWidth());
    }
}
