package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IconObject {
    private final Texture texture;
    private final int     width;
    private final int     height;
    private final float   x;
    private final float   y;

    public IconObject(String iconName, int x, int y, int width, int height) {
        this.width   = width;
        this.height  = height;
        this.x       = x - (width >> 1);
        this.y       = y - (height >> 1);
        this.texture = new Texture("textures/icons/" + iconName + ".png");
    }

    public void update() {
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }


}