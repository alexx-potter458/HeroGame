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
                ResultSet rs = stm.executeQuery("SELECT * FROM hero WHERE hero.id NOT IN (SELECT heroId FROM userHero);");
                while (rs.next())
                    heroes.add(new Hero(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("baseHealth"),
                            rs.getInt("width"),
                            rs.getInt("height"),
                            rs.getInt("price"),
                            rs.getInt("speed"),
                            rs.getInt("hitPower"),
                            rs.getInt("jumpPower"),
                            rs.getString("description")));

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

    public Hero getMainHero() {
        try(Connection conn = this.connect()) {

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT hero.* FROM hero, userHero WHERE userHero.heroId = hero.id AND userHero.isPrimary = 1;");
                if(rs.next())
                    return new Hero(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("baseHealth"),
                            rs.getInt("width"),
                            rs.getInt("height"),
                            rs.getInt("price"),
                            rs.getInt("speed"),
                            rs.getInt("hitPower"),
                            rs.getInt("jumpPower"),
                            rs.getString("description"));

                rs.close();

                return null;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

        }   catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void makePrimary(int id, int idHero) {
        String query = "UPDATE userHero SET isPrimary = 1  WHERE userId = " + id + " AND heroId = " + idHero;

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

    public void resetUserHeroes() {
        String query = "UPDATE userHero SET isPrimary = 0  WHERE isPrimary = 1";

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

    public void buyHero(int userId, int heroId) {
        String query = "INSERT INTO userHero(userId, heroId, isPrimary) VALUES('" + userId + "','" + heroId + "', '1')";

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

    public Integer getHeroPowerId(Hero mainHero, int id) {
        try(Connection conn = this.connect()) {

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM heroPower WHERE idHero = " + mainHero.getId() + " AND idPower = " + id + ";");
                if(rs.next())
                    return rs.getInt("id");

                rs.close();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }   catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Hero> loadBoughtHeroes() {
        try(Connection conn = this.connect()) {
            ArrayList<Hero> heroes = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM hero WHERE hero.id IN (SELECT heroId FROM userHero);");
                while (rs.next())
                    heroes.add(new Hero(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("baseHealth"),
                            rs.getInt("width"),
                            rs.getInt("height"),
                            rs.getInt("price"),
                            rs.getInt("speed"),
                            rs.getInt("hitPower"),
                            rs.getInt("jumpPower"),
                            rs.getString("description")));

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

    public void deleteBoughtHeroes() {
        String query = "DELETE FROM userHero; DELETE FROM userHeroSpell; DELETE FROM userHeroPower;";

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