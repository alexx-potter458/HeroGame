package core.Database;

import core.Model.Enemy;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EnemyDatabase extends Database {
    public ArrayList<Enemy> loadEnemiesByLevel(int level) {
        try(Connection conn = this.connect()) {
            ArrayList<Enemy> enemies = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM enemy WHERE baseLevel <= " + level + ";");
                while (rs.next())
                    enemies.add(new Enemy(
                            rs.getInt("id"),
                            rs.getInt("width"),
                            rs.getInt("height"),
                            rs.getInt("hitPower"),
                            rs.getInt("moneyReward"),
                            rs.getInt("jumpPower"),
                            rs.getInt("speed"),
                            rs.getInt("health"),
                            rs.getString("name")));
                rs.close();
                return enemies;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }   catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}