package core.Controller;

import core.Database.LevelDatabase;
import core.Model.Level;

import java.util.ArrayList;

public class LevelController {
    public ArrayList<Level> getAllNotPlayedLevels() {
        return new LevelDatabase().getAllNotPlayedLevels();
    }

    public ArrayList<Level> getAllPlayedLevels() {
        return new LevelDatabase().getAllPlayedLevels();
    }

    public void unlockNextLevel(int level) {
        new LevelDatabase().unlockNextLevel(level);
        new LevelDatabase().setPlayedLevel(level);
    }

    public Level getLevelById(int level) {
        return new LevelDatabase().getLevelById(level);
    }

    public Level getNextLevelById(int level) {
        return new LevelDatabase().getNextLevelById(level);
    }

}