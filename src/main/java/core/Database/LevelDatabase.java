package core.Database;

import core.Model.Level;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LevelDatabase extends Database {
    public ArrayList<Level> getAllNotPlayedLevels() {
        try(Connection conn = this.connect()) {

            ArrayList<Level> levels = new ArrayList<>();
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM level WHERE played = 0");
                while(rs.next())
                    levels.add(new Level(   rs.getInt("id"),
                                            rs.getString("name"),
                                            rs.getInt("unlocked"),
                                            rs.getInt("baseScore")));
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

    public ArrayList<Level> getAllPlayedLevels() {
        try(Connection conn = this.connect()) {

            ArrayList<Level> levels = new ArrayList<>();
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM level WHERE played = 1 ORDER BY id DESC;");
                while(rs.next())
                    levels.add(new Level(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("unlocked"),
                            rs.getInt("baseScore")));
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

    public void setPlayedLevel(int level) {
        String query = "UPDATE level SET played = 1 WHERE id = " + level + ";";

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

    public void unlockNextLevel(int level) {
        String query = "UPDATE level SET unlocked = 1 WHERE id IN ( SELECT id FROM level WHERE id > " + level + " ORDER BY id LIMIT 1);";

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

    public Level getLevelById(int level) {
        try(Connection conn = this.connect()) {

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM level WHERE id = " + level + ";");
                if(rs.next())
                    return new Level( rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("unlocked"),
                            rs.getInt("baseScore"));
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Level getNextLevelById(int level) {
        try(Connection conn = this.connect()) {

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM level WHERE id > " + level + " ORDER BY id LIMIT 1;");
                if(rs.next())
                    return new Level( rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("unlocked"),
                            rs.getInt("baseScore"));
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void resetLevels() {
        String query = "UPDATE level SET played = 0; UPDATE level SET unlocked = 0; UPDATE level SET unlocked = 1 WHERE id = 1;";

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