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
                ResultSet rs = stm.executeQuery("SELECT * FROM spell;");
                while(rs.next()) {
                    spells.add(new Spell(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("nameSlug"),
                            rs.getInt("hitPower"),
                            rs.getInt("price"),
                            rs.getString("description")));

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

    public void buySpell(int userId, int heroId, int spellId) {
        String query = "INSERT INTO userHeroSpell(idUser, idHero, idSpell) VALUES('" + userId + "','" + heroId + "', " + spellId + ")";

        try(Connection conn = this.connect()) {
            try(Statement stm = conn.createStatement()) {
                stm.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
