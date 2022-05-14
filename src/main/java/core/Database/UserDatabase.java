package core.Database;

import core.Model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase extends Database {

    public boolean loadUser() {
        try(Connection conn = this.connect()) {
            try(Statement stm = conn.createStatement()) {

                ResultSet rs = stm.executeQuery("SELECT * from user ORDER BY id DESC LIMIT 1;");
                if (rs.next()) {
                    new User(   rs.getInt("id"),
                            rs.getString("nickname"),
                            rs.getInt("money"),
                            rs.getInt("level"),
                            rs.getInt("score"),
                            rs.getInt("firstTime"));
                    return true;
                }

                rs.close();
                return false;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }   catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(String nickname) {
        String query = "INSERT INTO user(nickname, money, firstTime) VALUES('" + nickname + "', 100, 0)";

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

    public void deleteAllUsers() {
        String query = "DELETE FROM user;";

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

    public void setLevel(int id, int level) {
        String query = "UPDATE user SET level = " + level + " WHERE id=" + id;

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

    public void setExperience(int id, int experience) {
        String query = "UPDATE user SET score = " + experience + " WHERE id=" + id;

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

    public void setMoney(int id, int money) {
        String query = "UPDATE user SET money = " + money + " WHERE id=" + id;

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
