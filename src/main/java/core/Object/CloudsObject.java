package core.Object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import core.Screen.Screen;

public class CloudsObject {
    private final CloudObject[] cloudObjects;

    public CloudsObject(Screen screen) {
        this.cloudObjects = new CloudObject[this.getRandomCloudNumber()];
        for(int i = 0; i< this.cloudObjects.length; i++){
            this.cloudObjects[i] = new CloudObject(screen);
        }
    }

    public void update() {
        for(CloudObject cloudObject : this.cloudObjects){
            cloudObject.update();
            cloudObject.addOpacity(1f/120f);
        }
    }

    public void render(SpriteBatch batch) {
        for(CloudObject cloudObject : this.cloudObjects){
            cloudObject.render(batch);
        }
    }

    private int getRandomCloudNumber() {
        return (int) Math.floor(Math.random() * 2) + 4;
    }
}


