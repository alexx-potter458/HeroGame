package core.Database;

import core.Model.Power;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PowerDatabase extends Database {

    public ArrayList<Power> loadAllPowers() {
        try(Connection conn = this.connect()) {
            ArrayList<Power> powers = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM power;");
                while(rs.next()) {
                    powers.add(new Power(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("price")));
                }

                rs.close();
                return powers;

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
