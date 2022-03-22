package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Screen.Screen;

public class Clouds {
    private final Cloud[] clouds;

    public Clouds(Screen screen) {
        this.clouds = new Cloud[this.getRandomCloudNumber()];
        for(int i = 0; i< this.clouds.length; i++){
            this.clouds[i] = new Cloud(screen);
        }
    }

    public void update() {
        for(Cloud cloud: this.clouds){
            cloud.update();
            cloud.addOpacity(1f/120f);
        }
    }

    public void render(SpriteBatch batch) {
        for(Cloud cloud: this.clouds){
            cloud.render(batch);
        }
    }

    private int getRandomCloudNumber() {
        return (int) Math.floor(Math.random() * 2) + 4;
    }
}


