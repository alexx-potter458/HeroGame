package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Model.Spell;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.ObjectType;

public class SpellObject {
    private Texture texture;
    private float x;
    private float y;
    private final float width;
    private final float height;

    public SpellObject(Screen screen, Spell spell, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 20;
        this.height = 20;

        Body body = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.NONOBJECT);
        this.texture = new Texture("textures/characters/stitch/stationary/stitch.png");
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

}
