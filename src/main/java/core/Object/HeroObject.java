package core.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import core.Model.Hero;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class HeroObject {
    private final Texture   texture;
    private final Texture   stationary;
    private final Texture   ready;
    private final Texture   running;
    private float           x;
    private float           y;
    private float           velocityX;
    private final float     velocityY;
    private final float     speed;
    private final float     width;
    private final float     height;
    private Body            body;
    private int             jumpCounter;

    public HeroObject(Screen screen) {
        this.x           = 0;
        this.y           = 0;
        this.width       = 0;
        this.height      = 0;
        this.velocityX   = 0;
        this.velocityY   = 0;
        this.speed       = 0;
        this.body        = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.BUTTON);
        this.stationary  = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.ready       = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.running     = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.texture     = this.stationary;
        this.jumpCounter = 0;
    }

    public HeroObject(Screen screen, Hero hero, int x, int y) {
        this.x           = x;
        this.y           = y;
        this.width       = hero.getWidth();
        this.height      = hero.getHeight();
        this.velocityX   = 0;
        this.velocityY   = 0;
        this.speed       = hero.getSpeed();
        this.body        = BodyHelper.createBody(this.x, this.y, width, height, 0, 2, screen.getWorld(), ObjectType.HERO);
        this.stationary  = new Texture("textures/characters/" + hero.getNameSlug() + "/stationary/" + hero.getNameSlug() + ".png");
        this.ready       = new Texture("textures/characters/" + hero.getNameSlug() + "/stationary/" + hero.getNameSlug() + ".png");
        this.running     = new Texture("textures/characters/" + hero.getNameSlug() + "/stationary/" + hero.getNameSlug() + ".png");
        this.texture     = this.stationary;
        this.jumpCounter = 0;
    }

    public void update() {
        this.x = body.getPosition().x * Config.PPM  - (width /2);
        this.y = body.getPosition().y * Config.PPM  - (height /2);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void checkUserInput() {
        this.velocityX = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            this.velocityX = 1;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.getBody().getPosition().x >=  (this.width / (Config.PPM * 2)))
            this.velocityX = -1;

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && this.jumpCounter < 2) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, body.getMass() * 18), body.getPosition(), true);
            jumpCounter++;
        }

        if(body.getLinearVelocity().y == 0) {
            jumpCounter = 0;
        }

        body.setLinearVelocity(velocityX * speed, body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}
