package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;
import core.Boot;

public class CloudObject {
    private final Body body;
    private final Texture texture;
    private float x;
    private float y;
    private float speedX;
    private float opacity;
    private final int width;
    private final int height;

    public CloudObject(Screen screen) {
        this.x       = this.getRandomCloudX();
        this.y       = this.getRandomCloudY();
        this.speedX  = this.getRandomCloudSpeed();
        this.width   = 240;
        this.height  = 135;
        this.opacity = 0f;
        this.body    = BodyHelper.createBody(x, y, width, height, 0, 1, screen.getWorld(), ObjectType.CLOUD);
        this.texture = new Texture("textures/cloud.png");
    }

    public void update() {
        this.x = body.getPosition().x * Config.PPM - (width >> 1);
        this.y = body.getPosition().y * Config.PPM - (height >> 1);
        this.body.setLinearVelocity( speedX,  0);

        if(x < -250) {
            this.body.setTransform((Boot.bootInstance.getScreenWidth()) / Config.PPM, getRandomCloudY() / Config.PPM, 0);
            this.speedX = getRandomCloudSpeed();
            this.setOpacity(0f);
        }
    }

    public void render(SpriteBatch batch) {
        batch.setColor(1.0f, 1.0f, 1.0f, this.opacity);
        batch.draw(texture, x, y, width, height);
    }

    private int getRandomCloudSpeed() {
        return -((int) Math.floor(Math.random() * 2)) - 2;
    }

    private int getRandomCloudY() {
        return Boot.bootInstance.getScreenHeight() - 75 - ((int) Math.floor(Math.random() * Boot.bootInstance.getScreenHeight() / 3));
    }

    private int getRandomCloudX() {
        return (int) Math.floor(Math.random() * Boot.bootInstance.getScreenWidth());
    }

    public void addOpacity(float value) {
        if(this.opacity < 1 && this.opacity >= 0)
            this.opacity += value;
    }

    public void setOpacity(float value) {
        this.opacity = value;
    }

}
