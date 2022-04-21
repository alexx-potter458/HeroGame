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
                ResultSet rs = stm.executeQuery("SELECT power.* FROM power, heroPower, userHero WHERE heroPower.idPower = power.id AND userHero.heroId = heroPower.idHero AND userHero.isPrimary = 1;");
                while(rs.next()) {
                    powers.add(new Power(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("price"),
                            rs.getString("description")));
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

    public void buyPower(int userId, int heroPowerId) {
        String query = "INSERT INTO userHeroPower(idUser, idHeroPower) VALUES('" + userId + "','" + heroPowerId + "')";

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
