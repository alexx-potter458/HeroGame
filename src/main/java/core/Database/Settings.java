package core.Database;

import utils.Config;
import utils.Constants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Settings extends Database {

    public void loadSettings() {
        Connection conn = this.connect();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from config;");
            while (rs.next()) {
                String name = rs.getString("name");
                String value = rs.getString("value");

                if(name.equals("isWindow")) {
                    Config.isWindow = value.equals("1");
                }

                if(name.equals("time"))
                    Config.time = value;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.changeBackground();
    }

    public void toggleTime(String time) {
        String query = "UPDATE config SET value ='" + time + "' WHERE name = 'time'";
        Connection conn = this.connect();

        try {
            Statement stm = conn.createStatement();
            if(stm.executeUpdate(query) == 1)
                Config.time = time;
            this.changeBackground();

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleDisplayMode() {
        String query = "UPDATE config SET value = ";
        String querySecondPart = " WHERE name = 'isWindow'";

        Connection conn = this.connect();
        if(Config.isWindow){
            query += 0;
        } else {
            query += 1;
        }
        query += querySecondPart;

        try {
            Statement stm = conn.createStatement();
            if(stm.executeUpdate(query) == 1)
                Config.isWindow = !Config.isWindow;

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void changeBackground() {
        if(Config.time.equals(Constants.dayValue)) {
            Config.bgColor = Config.bgDayColor;
        } else {
            Config.bgColor = Config.bgNightColor;
        }
    }

}

