package core.Database;

import utils.Config;
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
                if(name.equals("isWindow"))
                    Config.isWindow = Boolean.parseBoolean(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
