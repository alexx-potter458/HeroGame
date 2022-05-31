package core.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
    private Texture         texture;
    private final Texture   firstRight;
    private final Texture   firstLeft;
    private final Texture   secondRight;
    private final Texture   secondLeft;
    private float           x;
    private float           y;
    private float           velocityX;
    private float           velocityY;
    private float           speed;
    private       float     jumpPower;
    private final float     width;
    private final float     height;
    private final Body      body;
    private int             jumpCounter;
    private float           oldVelY;
    private String          direction;
    private int             timer;
    private int             powerValue;
    private String          powerType;
    private int             heroHealth;
    private boolean         shield;

    public HeroObject(Screen screen) {
        this.x           = 0;
        this.y           = 0;
        this.width       = 0;
        this.height      = 0;
        this.velocityX   = 0;
        this.velocityY   = 0;
        this.timer       = 0;
        this.heroHealth  = 0;
        this.jumpPower   = 0;
        this.speed       = 0;
        this.powerValue  = 0;
        this.body        = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.BUTTON);
        this.firstRight = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.secondRight = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.firstLeft = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.secondLeft = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.texture     = this.firstRight;
        this.jumpCounter = 0;
        this.direction   = "R";
        this.powerType   = "";
        this.shield      = false;
    }

    public HeroObject(Screen screen, Hero hero, int x, int y) {
        this.x           = x;
        this.y           = y;
        this.direction   = "FR";
        this.timer       = 0;
        this.width       = hero.getWidth();
        this.height      = hero.getHeight();
        this.velocityX   = 0;
        this.velocityY   = 0;
        this.speed       = hero.getSpeed();
        this.jumpPower   = hero.getJumpPower();
        this.body        = BodyHelper.createBody(this.x, this.y, width, height, 0, 2, screen.getWorld(), ObjectType.HERO);
        this.firstRight = new Texture("textures/characters/" + hero.getNameSlug() + "/inGame/firstRight.png");
        this.firstLeft = new Texture("textures/characters/" + hero.getNameSlug() + "/inGame/firstLeft.png");
        this.secondRight = new Texture("textures/characters/" + hero.getNameSlug() + "/inGame/secondRight.png");
        this.secondLeft = new Texture("textures/characters/" + hero.getNameSlug() + "/inGame/secondLeft.png");
        this.texture     = this.firstRight;
        this.jumpCounter = 0;
        this.oldVelY     = 0;
        this.heroHealth  = hero.getBaseHealth();
        this.powerValue  = 0;
        this.powerType   = "";
        this.shield      = false;
    }

    public void update() {
        this.x = body.getPosition().x * Config.PPM  - (width /2);
        this.y = body.getPosition().y * Config.PPM  - (height /2);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

    public void checkUserInput() {
        if(timer == 100)
            timer = 0;

        timer ++;
        this.velocityX = 0;
        float downPower = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            downPower = Math.abs(body.getLinearVelocity().y) * (-0.5f);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.velocityX = this.speed;

            if(timer % 13 == 0 )
                this.goRight();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && this.getBody().getPosition().x >=  (this.width / (Config.PPM * 2))) {
            this.velocityX = -1 * this.speed;

            if(timer % 13 == 0)
                this.goLeft();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && this.jumpCounter < 2) {
            Sound sound = Gdx.audio.newSound(Gdx.files.internal("audio/inGame/jump.wav"));
            sound.setVolume(sound.play(), Config.inGameSound);
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, body.getMass() * this.jumpPower), body.getPosition(), true);
            jumpCounter++;
        }

        if(body.getLinearVelocity().y <= 0 && body.getLinearVelocity().y >= -0.01 && this.oldVelY <= 0)
            jumpCounter = 0;

        this.oldVelY = body.getLinearVelocity().y;

        float flyPower = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.UP) && velocityY > 0)
            flyPower = velocityY;

        body.setLinearVelocity(velocityX * speed, (body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25) + flyPower + downPower);
    }

    private void goLeft() {
        if(this.direction.contains("FL")) {
            this.direction  = "SL";
            this.texture    = this.secondLeft;
        } else {
            this.direction  = "FL";
            this.texture    = this.firstLeft;
        }
    }

    private void goRight() {
        if(this.direction.contains("FR")) {
            this.direction  = "SR";
            this.texture    = this.secondRight;
        } else {
            this.direction  = "FR";
            this.texture    = this.firstRight;
        }
    }

    public Body getBody() {
        return body;
    }

    public String getDirection() {
        return this.direction;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHeroHealth() {
        return heroHealth;
    }

    public void addHealth(int value){
        this.heroHealth += value;
    }

    public void takeHit(int value) {
        if(!shield)
            this.heroHealth -= value;
    }

    public void activatePower() {
        if(this.powerType.contains("speed"))
            this.speed += this.powerValue;

        if(this.powerType.contains("shield"))
            this.shield = true;

        if(this.powerType.contains("jump"))
            this.jumpPower += this.powerValue;

        if(this.powerType.contains("fly"))
            this.velocityY += this.powerValue;
    }

    public void deactivatePower() {
        if(this.powerType.contains("speed"))
            this.speed -= this.powerValue;

        if(this.powerType.contains("shield"))
            this.shield = false;

        if(this.powerType.contains("jump"))
            this.jumpPower -= this.powerValue;

        if(this.powerType.contains("fly"))
            this.velocityY -= this.powerValue;
    }

    public void setPower(int value, String type) {
        this.powerValue = value;
        this.powerType  = type;
    }

}