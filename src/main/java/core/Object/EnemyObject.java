package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import core.Model.Enemy;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class EnemyObject {
    private Texture         texture;
    private final Texture   firstRight;
    private final Texture   firstLeft;
    private final Texture   secondRight;
    private final Texture   secondLeft;
    private float           x;
    private float           y;
    private float           velocityX;
    private final Enemy     enemy;
    private final Body      body;
    private int             jumpCounter;
    private float           oldVelY;
    private String          direction;
    private int             timer;
    private int             inGameHealth;
    private boolean         toDestroy;
    private boolean         destroyed;
    private boolean         readyToShoot;


    public EnemyObject(Screen screen, float x, float y, Enemy enemy) {
        this.x            = x;
        this.y            = y;
        this.enemy        = enemy;
        this.direction    = "FL";
        this.timer        = 0;
        this.velocityX    = 0;
        this.body         = BodyHelper.initBody(this.x, this.y, this.enemy.getWidth(), this.enemy.getHeight(), 0, 2, screen.getWorld(), ObjectType.ENEMY);
        this.firstRight   = new Texture("textures/enemies/" + enemy.getName() + "/firstRight.png");
        this.firstLeft    = new Texture("textures/enemies/" + enemy.getName() + "/firstLeft.png");
        this.secondRight  = new Texture("textures/enemies/" + enemy.getName() + "/secondRight.png");
        this.secondLeft   = new Texture("textures/enemies/" + enemy.getName() + "/secondLeft.png");
        this.texture      = this.firstLeft;
        this.jumpCounter  = 0;
        this.oldVelY      = 0;
        this.inGameHealth = this.enemy.getHealth();
        this.toDestroy    = false;
        this.destroyed    = false;
        this.readyToShoot = false;
    }

    public void update(float heroX, float heroY) {
        if(timer == 1000)
            timer = 0;

        this.timer ++;

        this.velocityX = 0;
        this.x         = body.getPosition().x * Config.PPM  - (this.enemy.getWidth() >> 1);
        this.y         = body.getPosition().y * Config.PPM  - (this.enemy.getHeight() >> 1);

        if(this.inGameHealth <= 0)
            this.toDestroy = true;

        if(Math.abs(heroX - this.x) > 1200 && Math.abs(heroY - this.y) > 300) {
            this.readyToShoot = false;
            this.stay();
        }

        if(Math.abs(heroX - this.x) < 1150 && Math.abs(heroX - this.x) > 150) {

            this.readyToShoot = Math.abs(heroY - this.y) < 100;

            if(heroX < this.x)
                this.goLeft();

            if (heroX > this.x)
                this.goRight();

            if(heroY - this.y > 100 && Math.abs(heroY - this.y) < 290 && timer % 23 == 0)
                this.goUp();
        }

        if(Math.abs(heroX - this.x) < 150 ) {
            if( Math.abs(heroY - this.y) < 100) {
                this.readyToShoot = true;
                this.stay();
            } else {
                this.readyToShoot = false;
                if(this.y - heroY < 100 && this.timer % (int)(Math.random()*15 + 10) == 0)
                    this.goUp();

                if(Math.abs(heroX - this.x) >= 0 && this.timer % (int)(Math.random()*15 + 10) == 0) {
                    if(heroX < this.x)
                        this.goLeft();

                    if (heroX > this.x)
                        this.goRight();
                }
            }

        }
    }

    public void render(SpriteBatch batch) {
        if(!toDestroy && !destroyed)
            batch.draw(texture, x, y, this.enemy.getWidth(), this.enemy.getHeight());
    }

    public void goUp() {
        if(this.jumpCounter < 2) {
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, body.getMass() * this.enemy.getJumpPower()), body.getPosition(), true);
            jumpCounter++;
        }

        if(body.getLinearVelocity().y <= 0 && body.getLinearVelocity().y >= -0.01 && this.oldVelY <= 0)
            jumpCounter = 0;

        this.oldVelY = body.getLinearVelocity().y;

        body.setLinearVelocity(this.velocityX * this.enemy.getSpeed(), body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    private void goLeft() {
        this.velocityX = -1 * this.enemy.getSpeed();

        if(timer % 13 == 0)
            if(this.direction.contains("FL")) {
                this.direction  = "SL";
                this.texture    = this.secondLeft;
            } else {
                this.direction  = "FL";
                this.texture    = this.firstLeft;
            }

        body.setLinearVelocity(velocityX * this.enemy.getSpeed(), body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    private void goRight() {
        this.velocityX = this.enemy.getSpeed();

        if(timer % 13 == 0 )
            if(this.direction.contains("FR")) {
                this.direction  = "SR";
                this.texture    = this.secondRight;
            } else {
                this.direction  = "FR";
                this.texture    = this.firstRight;
            }

        body.setLinearVelocity(velocityX * this.enemy.getSpeed(), body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
    }

    private void stay() {
        body.setLinearVelocity(velocityX * this.enemy.getSpeed(), body.getLinearVelocity().y < 25 ? body.getLinearVelocity().y : 25);
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

    public Enemy getEnemy() {
        return this.enemy;
    }

    public Fixture getFixture() {
        if(this.body.getFixtureList().size > 0)
            return this.body.getFixtureList().get(0);
        else
            return null;
    }

    public void takeDamage(int value) {
        this.inGameHealth -= value;
    }

    public boolean isToDestroy() {
        return toDestroy;
    }

    public void safeDestroy() {
        if (this.toDestroy && this.body.getFixtureList().size > 0)
            body.destroyFixture(this.body.getFixtureList().get(0));
        this.setDestroyed();
    }

    public void setDestroyed() {
        destroyed = true;
    }

    public boolean isNotDestroyed() {
        return !this.destroyed;
    }

    public boolean readyToShoot() {
        return this.readyToShoot;
    }

}