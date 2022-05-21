package core.Controller;

import core.Database.EnemyDatabase;
import core.Model.Enemy;
import java.util.ArrayList;

public class EnemyController {
    public ArrayList<Enemy> getEnemiesByLevel(int level) {
        return new EnemyDatabase().loadEnemiesByLevel(level);
    }

}