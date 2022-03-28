package core.Database;

import objects.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase extends Database {

    public boolean loadUser() {
        Connection conn = this.connect();
        User user;

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * from user LIMIT 1;");
            if (rs.next()) {
                new User(   rs.getInt("id"),
                            rs.getString("nickname"),
                            rs.getInt("money"),
                            rs.getInt("level"),
                            rs.getInt("score"),
                            rs.getInt("firstTime"));
                return true;
            }

            conn.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createUser(String nickname) {
        String query = "INSERT INTO user(nickname, firstTime) VALUES('" + nickname + "', 0)";
        Connection conn = this.connect();
        try {
            Statement stm = conn.createStatement();
            stm.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
