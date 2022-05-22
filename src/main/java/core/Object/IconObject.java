package core.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IconObject {
    private Texture       texture;
    private final int     width;
    private final int     height;
    private final float   x;
    private final float   y;
    private       boolean isVisible;

    public IconObject(String iconName, int x, int y, int width, int height) {
        this.width     = width;
        this.height    = height;
        this.x         = x - (width >> 1);
        this.y         = y - (height >> 1);
        this.isVisible = true;
        this.texture   = new Texture("textures/icons/" + iconName + ".png");
    }

    public void update() {
    }

    public void setSpellIcon(String name) {
        this.texture = new Texture("textures/spells/inStore/" + name + ".png");
    }

    public void setPowerIcon(String name) {
        this.texture = new Texture("textures/powers/" + name + ".png");
    }

    public void setIcon(String name) {
        this.texture = new Texture("textures/icons/" + name + ".png");
    }

    public void render(SpriteBatch batch) {
        if(isVisible)
            batch.draw(texture, x, y, width, height);
    }

    public void changeVisibility(boolean value) {
        this.isVisible = value;
    }

}