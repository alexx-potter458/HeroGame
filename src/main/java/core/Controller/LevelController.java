package core.Controller;

import core.Database.LevelDatabase;
import core.Model.Level;

import java.util.ArrayList;

public class LevelController {

    public ArrayList<Level> getAllLevels() {
        return new LevelDatabase().getAllLevels();
    }

    public void unlockNextLevel() {
        new LevelDatabase().unlockNextLevel();
    }

}
