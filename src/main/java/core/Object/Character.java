package core.Object;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.ObjectType;

public class Character {
    private Texture texture;
    private final Texture stationary;
    private final Texture ready;
    private final Texture running;
    private float x;
    private float y;
    private final float width;
    private final float height;

    public Character(Screen screen, int x, int y) {
        this.x = x;
        this.y = y;
        this.width   = 160;
        this.height  = 170;
        Body body = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.BUTTON);
        this.stationary = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.ready = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.running = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.texture = this.stationary;
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

}
