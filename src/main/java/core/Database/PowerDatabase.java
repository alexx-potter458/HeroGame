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
                ResultSet rs = stm.executeQuery("SELECT power.* FROM power, heroPower, userHero WHERE power.id NOT IN (SELECT power.id FROM power, heroPower, userHero, userHeroPower WHERE userHeroPower.idHeroPower = heroPower.id AND heroPower.idPower = power.id AND userHero.heroId = heroPower.idHero AND userHero.isPrimary = 1) AND heroPower.idPower = power.id AND userHero.heroId = heroPower.idHero AND userHero.isPrimary = 1;");
                while(rs.next())
                    powers.add(new Power(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("price"),
                            rs.getString("description")));

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

    public ArrayList<Power> loadBoughtPowers() {
        try(Connection conn = this.connect()) {
            ArrayList<Power> powers = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT power.*, userHeroPower.isActive AS isActive FROM power, heroPower, userHero, userHeroPower WHERE userHeroPower.idHeroPower = heroPower.id AND heroPower.idPower = power.id AND userHero.heroId = heroPower.idHero AND userHero.isPrimary = 1;");
                while(rs.next())
                    powers.add(new Power(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("price"),
                            rs.getInt("isActive"),
                            rs.getInt("activeTime"),
                            rs.getInt("refuelTime"),
                            rs.getInt("value"),
                            rs.getString("type"),
                            rs.getString("description")));
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

    public Power loadActivePower() {
        try(Connection conn = this.connect()) {

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT power.*, userHeroPower.isActive AS isActive FROM power, heroPower, userHero, userHeroPower WHERE userHeroPower.idHeroPower = heroPower.id AND heroPower.idPower = power.id AND userHero.heroId = heroPower.idHero AND userHero.isPrimary = 1 AND userHeroPower.isActive = 1;");
                if(rs.next())
                    return new Power(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("price"),
                            rs.getInt("isActive"),
                            rs.getInt("activeTime"),
                            rs.getInt("refuelTime"),
                            rs.getInt("value"),
                            rs.getString("type"),
                            rs.getString("description"));
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }   catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deactivateAllPowers() {
        String query = "UPDATE userHeroPower SET isActive = 0";

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

    public void changePowerStatus(int i, int idPowerHero, int idUser) {
        String query = "UPDATE userHeroPower SET isActive = " + i + " WHERE idUser = " + idUser + " AND idHeroPower = " + idPowerHero + ";";

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

    public int getHeroPowerId(int id, int idPower) {
        try(Connection conn = this.connect()) {
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM heroPower WHERE idHero = " + id + " AND idPower = " + idPower + ";");
                if(rs.next())
                    return rs.getInt("id");

                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }   catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}