package core.Object;

import core.Screen.Screen;
import utils.BodyHelper;
import utils.ObjectType;

public class DeadlyObject {
    public DeadlyObject(Screen screen, float x, float y, float width, float height) {
        BodyHelper.initBody(x, y, width, height, 0, 1, screen.getWorld(), ObjectType.KILLER);
    }

    public void update() {
    }

}
