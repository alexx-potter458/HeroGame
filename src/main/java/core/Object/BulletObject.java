package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Model.Spell;
import core.Screen.GameScreen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class BulletObject {
    private final Texture texture;
    private final Body    body;
    private final int     hitPower;
    private       float   x;
    private       float   y;
    private final int     speed;
    private final int     velX;
    private final int     width;
    private final int     height;
    private      boolean  destroyed;
    private      boolean  toDestroy;

    public BulletObject(GameScreen gameScreen, float x, float y, int hitPower, int speed, String direction) {
        if(direction.contains("R"))
            this.velX = 1;
        else
            this.velX = -1;

        this.x         = x + velX * 60;
        this.y         = y;
        this.speed     = speed * speed + 1;
        this.width     = 23;
        this.height    = 10;
        this.hitPower  = hitPower;
        this.texture   = (this.velX == 1)? new Texture("textures/spells/inGame/defaultRight.png") : new Texture("textures/spells/inGame/defaultLeft.png");
        this.body      = BodyHelper.createBody(this.x, this.y, this.width, this.height, 0, 2, gameScreen.getWorld(), ObjectType.HERO_BULLET);
        this.destroyed = false;
        this.toDestroy = false;
        this.body.setLinearVelocity(this.speed * this.velX, this.body.getMass());
    }

    public BulletObject(GameScreen gameScreen, float x, float y, int hitPower, int speed, String direction, ObjectType bulletType) {
        if(direction.contains("R"))
            this.velX = 1;
        else
            this.velX = -1;

        this.x         = x + velX * 60;
        this.y         = y;
        this.speed     = speed * speed + 1;
        this.width     = 28;
        this.height    = 15;
        this.hitPower  = hitPower;
        this.texture   = (this.velX == 1)? new Texture("textures/spells/inGame/enemyDefaultRight.png") : new Texture("textures/spells/inGame/enemyDefaultLeft.png");
        this.body      = BodyHelper.createBody(this.x, this.y, this.width, this.height, 0, 2, gameScreen.getWorld(), bulletType);
        this.destroyed = false;
        this.toDestroy = false;
        this.body.setLinearVelocity(this.speed * this.velX, this.body.getMass());
    }

    public BulletObject(GameScreen gameScreen, float x, float y, int hitPower, Spell spell, String direction) {
        if(direction.contains("R"))
            this.velX = 1;
        else
            this.velX = -1;

        this.x         = x + velX * 60;
        this.y         = y;
        this.speed     = spell.getSpeed();
        this.width     = 23;
        this.height    = 10;
        this.hitPower  = hitPower + spell.getHitPower();
        this.texture   = (this.velX == 1)? new Texture("textures/spells/inGame/" + spell.getNameSlug() + "Right.png") : new Texture("textures/spells/inGame/" + spell.getNameSlug() + "Left.png");
        this.body      = BodyHelper.createBody(this.x, this.y, this.width, this.height, 0, 2, gameScreen.getWorld(), ObjectType.HERO_BULLET);
        this.destroyed = false;
        this.toDestroy = false;
        this.body.setLinearVelocity(this.speed * this.velX, this.body.getMass());
    }

    public void update() {
        if(this.body.getLinearVelocity().x == 0)
            this.flaggedToDestroy();

        this.x = body.getPosition().x * Config.PPM  - (width >> 1);
        this.y = body.getPosition().y * Config.PPM  - (height >> 1);
        this.body.setLinearVelocity(this.speed * this.velX, 0);
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

    public boolean isToDestroy() {
        return this.toDestroy;
    }

    public void flaggedToDestroy() {
        this.toDestroy = true;
    }

    public void safeDestroy() {
        if (toDestroy && this.body.getFixtureList().size > 0)
            body.destroyFixture(this.body.getFixtureList().get(0));
        this.setDestroyed();
    }

    public void setDestroyed() {
        destroyed = true;
    }

    public boolean isNotDestroyed() {
        return !this.destroyed;
    }

    public int getHitPower() {
        return hitPower;
    }

}