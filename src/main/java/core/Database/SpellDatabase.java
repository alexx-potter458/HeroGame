package core.Database;

import core.Model.Spell;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SpellDatabase extends Database {

    public ArrayList<Spell> loadAllSpells() {
        try(Connection conn = this.connect()) {

            ArrayList<Spell> spells = new ArrayList<>();
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT s.* FROM spell s WHERE s.id NOT IN (SELECT uhp.idSpell FROM userHeroSpell uhp, userHero uh WHERE uh.heroId = uhp.idHero AND uh.isPrimary = 1);");
                while(rs.next()) {
                    spells.add(new Spell(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("hitPower"),
                            rs.getInt("price"),
                            rs.getInt("speed"),
                            rs.getInt("counter"),
                            rs.getString("description")));
                }
                rs.close();
                return spells;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void buySpell(int userId, int heroId, int spellId) {
        String query = "INSERT INTO userHeroSpell(idUser, idHero, idSpell) VALUES('" + userId + "','" + heroId + "', " + spellId + ")";

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

    public ArrayList<Spell> loadBoughtSpells() {
        try(Connection conn = this.connect()) {

            ArrayList<Spell> spells = new ArrayList<>();
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT s.*, uhp.isActive FROM spell s, userHeroSpell uhp, userHero uh WHERE s.id = uhp.idSpell AND uh.heroId = uhp.idHero AND uh.isPrimary = 1;");
                while(rs.next()) {
                    spells.add(
                            new Spell(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("nameSlug"),
                                    rs.getInt("hitPower"),
                                    rs.getInt("price"),
                                    rs.getInt("speed"),
                                    rs.getInt("counter"),
                                    rs.getInt("isActive"),
                                    rs.getString("description")
                            )
                    );
                }

                rs.close();
                return spells;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }   catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Spell> loadActiveSpells() {
        try(Connection conn = this.connect()) {

            ArrayList<Spell> spells = new ArrayList<>();
            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT s.*, uhp.isActive FROM spell s, userHeroSpell uhp, userHero uh WHERE s.id = uhp.idSpell AND uh.heroId = uhp.idHero AND uh.isPrimary = 1 AND uhp.isActive = 1;");
                while(rs.next()) {
                    spells.add(
                            new Spell(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("nameSlug"),
                                    rs.getInt("hitPower"),
                                    rs.getInt("price"),
                                    rs.getInt("speed"),
                                    rs.getInt("counter"),
                                    rs.getInt("isActive"),
                                    rs.getString("description")
                            )
                    );
                }

                rs.close();
                return spells;

            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }   catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeSpellStatus(int i, int idSpell, int idHero, int idUser) {
        String query = "UPDATE userHeroSpell SET isActive = " + i + " WHERE idUser = " + idUser + " AND idHero = " + idHero + " AND idSpell = " + idSpell + ";";

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

}
