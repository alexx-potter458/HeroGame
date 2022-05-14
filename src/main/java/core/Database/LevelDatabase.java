package core.Database;

import core.Model.Level;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LevelDatabase extends Database {

    public ArrayList<Level> getAllLevels() {
        try(Connection conn = this.connect()) {

            ArrayList<Level> levels = new ArrayList<>();
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM level");
                while(rs.next()) {
                    levels.add(new Level(   rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getInt("unlocked"),
                                            rs.getInt("baseScore"))
                    );
                }

                rs.close();
                return levels;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void unlockNextLevel() {
        String query = "UPDATE level SET unlocked = 1 WHERE id=(SELECT id FROM level WHERE unlocked = 0 ORDER BY id LIMIT 1)";

        try(Connection conn = this.connect()) {
            try(Statement stm = conn.createStatement()) {
                stm.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
