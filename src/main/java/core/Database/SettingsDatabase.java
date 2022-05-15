package core.Database;

import utils.Config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsDatabase extends Database {
    public void loadSettings() {
        try(Connection conn = this.connect()){
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * from config;");
                while (rs.next()) {
                    String name = rs.getString("name");
                    String value = rs.getString("value");

                    if(name.equals("isWindow"))
                        Config.isWindow = value.equals("1");

                    if(name.equals("time"))
                        Config.time = value;
                }

                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleTime(String time) {
        String query = "UPDATE config SET value ='" + time + "' WHERE name = 'time'";

        try(Connection conn = this.connect()){
            try(Statement stm = conn.createStatement()) {
                stm.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleDisplayMode(int value) {
        String query = "UPDATE config SET value = "+ value + " WHERE name = 'isWindow'";

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