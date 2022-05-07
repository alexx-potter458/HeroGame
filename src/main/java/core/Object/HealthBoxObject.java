package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class HealthBoxObject {
    private final       Texture texture;
    private float       x;
    private float       y;
    private final float width;
    private final float height;
    private final Body  body;
    private boolean     toDestroy;

    public HealthBoxObject(Screen screen, float x, float y) {
        this.width      = 60;
        this.height     = 60;
        this.x          = x;
        this.y          = y;
        this.body       = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.REWARD_HH);
        this.texture    =  new Texture("textures/rewards/health.png");
        this.x          = body.getPosition().x * Config.PPM - (width /2);
        this.y          = body.getPosition().y * Config.PPM - (height /2);
        this.toDestroy  = false;
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        if(!toDestroy)
            batch.draw(texture, x, y, width, height);
    }

    public Fixture getFixture() {
        return this.body.getFixtureList().get(0);
    }

    public void flaggedToDestroy() {
        this.toDestroy = true;
    }

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void safeDestroy() {
        if (toDestroy && this.body.getFixtureList().size > 0)
            body.destroyFixture(this.body.getFixtureList().get(0));
    }

}