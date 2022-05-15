package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Model.Reward;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class MoneyBoxObject {
    private final Texture texture;
    private float         x;
    private float         y;
    private final float   width;
    private final float   height;
    private final Body    body;
    private boolean       toDestroy;
    private boolean       destroyed;
    private final Reward  reward;

    public MoneyBoxObject(Screen screen, float x, float y, Reward reward) {
        this.width      = 60;
        this.height     = 60;
        this.x          = x;
        this.y          = y;
        this.body       = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.REWARD_MY_XP);
        this.texture    =  new Texture("textures/rewards/moneyAndExperience.png");
        this.x          = body.getPosition().x * Config.PPM - (width /2);
        this.y          = body.getPosition().y * Config.PPM - (height /2);
        this.toDestroy  = false;
        this.destroyed  = false;
        this.reward     = reward;
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        if(!toDestroy && !destroyed)
            batch.draw(texture, x, y, width, height);
    }

    public Fixture getFixture() {
        if(this.body.getFixtureList().size > 0)
            return this.body.getFixtureList().get(0);
        else
            return null;
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
        this.setDestroyed();
    }

    public void setDestroyed() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public Reward getReward() {
        return reward;
    }

}