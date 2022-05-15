package core.Database;

import core.Model.Reward;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RewardDatabase extends Database{
    public ArrayList<Reward> getHealthRewards(int level) {
        try(Connection conn = this.connect()) {
            ArrayList<Reward> rewards = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM rewardPoint WHERE type = 'HH' AND minimumLevel <= " + level + ";");
                while(rs.next())
                    rewards.add(new Reward(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("value"),
                            rs.getInt("minimumLevel")));

                rs.close();
                return rewards;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Reward> getMoneyRewards(int level) {
        try(Connection conn = this.connect()) {
            ArrayList<Reward> rewards = new ArrayList<>();

            try(Statement stm = conn.createStatement()) {
                ResultSet rs = stm.executeQuery("SELECT * FROM rewardPoint WHERE type = 'MY_XP' AND minimumLevel <= " + level + ";");
                while(rs.next())
                    rewards.add(new Reward(   rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getInt("value"),
                            rs.getInt("minimumLevel")));

                rs.close();
                return rewards;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}