package core.Controller;

import core.Database.RewardDatabase;
import core.Model.Reward;
import java.util.ArrayList;

public class RewardController {

    public ArrayList<Reward> getHealthRewards(int level) {
        return new RewardDatabase().getHealthRewards(level);
    }

    public ArrayList<Reward> getMoneyRewards(int level) {
        return new RewardDatabase().getMoneyRewards(level);
    }

}
