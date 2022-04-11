package core.Database;

import core.Model.Hero;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HeroDatabase extends Database {

    public ArrayList<Hero> loadAllHeroes() {
        try(Connection conn = this.connect()) {
            ArrayList<Hero> heroes = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM hero;");
                while (rs.next()) {
                    heroes.add(new Hero(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("baseHealth"),
                            rs.getInt("width"),
                            rs.getInt("height"),
                            rs.getInt("price"),
                            rs.getInt("speed"),
                            rs.getInt("hitPower"),
                            rs.getInt("jumpPower")));
                }

                rs.close();
                return heroes;

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
