package core.Database;

import utils.Config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingsDatabase extends Database {

    public void loadSettings() {
        Connection conn = this.connect();

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from config;");
            while (rs.next()) {
                String name = rs.getString("name");
                String value = rs.getString("value");

                if(name.equals("isWindow"))
                    Config.isWindow = value.equals("1");

                if(name.equals("time"))
                    Config.time = value;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void toggleTime(String time) {
        String query = "UPDATE config SET value ='" + time + "' WHERE name = 'time'";
        Connection conn = this.connect();

        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleDisplayMode(int value) {
        String query = "UPDATE config SET value = "+ value + " WHERE name = 'isWindow'";
        Connection conn = this.connect();

        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

