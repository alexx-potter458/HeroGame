package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Model.Power;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.Config;
import utils.ObjectType;

public class PowerObject {
    private final Texture texture;
    private float         x;
    private float         y;
    private final int     width;
    private final int     height;
    private final Body    body;
    private int           timer;

    public PowerObject(Screen screen, Power power, int x, int y) {
        this.x       = x;
        this.y       = y;
        this.width   = 60;
        this.height  = 60;
        this.body    = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.NONOBJECT);
        this.texture = new Texture("textures/powers/inStore/" + power.getNameSlug() + ".png");
    }

    public void update() {
        timer++;
        this.x = body.getPosition().x * Config.PPM  - (width >> 1);
        this.y = body.getPosition().y * Config.PPM  - (height >> 1);

        if(this.timer <= 51)
            this.body.setLinearVelocity( 0,  1);
        else
        if(this.timer <= 100)
            this.body.setLinearVelocity( 0,  -1);
        else
            this.timer = 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

}