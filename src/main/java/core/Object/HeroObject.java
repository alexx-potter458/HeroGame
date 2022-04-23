package core.Object;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.Model.Hero;
import core.Screen.Screen;
import utils.BodyHelper;
import utils.ObjectType;

public class HeroObject {
    private Texture texture;
    private final Texture stationary;
    private final Texture ready;
    private final Texture running;
    private float x;
    private float y;
    private final float width;
    private final float height;

    public HeroObject(Screen screen) {
        this.x = 0;
        this.y = 0;
        this.width   = 0;
        this.height  = 0;
        Body body = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.BUTTON);
        this.stationary = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.ready = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.running = new Texture("textures/characters/stitch/stationary/stitch.png");
        this.texture = this.stationary;
    }

    public HeroObject(Screen screen, Hero hero, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = hero.getWidth();
        this.height = hero.getHeight();

        Body body = BodyHelper.createBody(this.x, this.y, width, height, 0, 1, screen.getWorld(), ObjectType.BUTTON);
        this.stationary = new Texture("textures/characters/" + hero.getNameSlug() + "/stationary/" + hero.getNameSlug() + ".png");
        this.ready = new Texture("textures/characters/" + hero.getNameSlug() + "/stationary/" + hero.getNameSlug() + ".png");
        this.running = new Texture("textures/characters/" + hero.getNameSlug() + "/stationary/" + hero.getNameSlug() + ".png");
        this.texture = this.stationary;
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }

}
