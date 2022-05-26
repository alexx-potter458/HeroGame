package core.Controller;

import core.Database.PowerDatabase;
import core.Model.Hero;
import core.Model.Power;
import core.Model.User;

import java.util.ArrayList;

public class PowerController {
    public ArrayList<Power> getAllPowers() {
        return (new PowerDatabase()).loadAllPowers();
    }

    public Power getActivePower() {
        return (new PowerDatabase()).loadActivePower();
    }

    public void buy(Power power) {
        PowerDatabase powerDatabase   = new PowerDatabase();
        UserController userController = new UserController();
        HeroController heroController = new HeroController();

        powerDatabase.buyPower(User.user.getId(), heroController.getHeroPowerId(heroController.getMainHero(), power.getId()));
        userController.setMoney(User.user.getMoney() - power.getPrice());
    }

    public ArrayList<Power> getBoughtPowers() {
        return (new PowerDatabase()).loadBoughtPowers();
    }

    public void changePowerStatus(int activeStatus, int idPower) {
        Hero hero        = new HeroController().getMainHero();
        PowerDatabase pd = new PowerDatabase();
        int heroPowerId  = pd.getHeroPowerId(hero.getId(), idPower);

        pd.deactivateAllPowers();
        pd.changePowerStatus((activeStatus == 1)? 0 : 1, heroPowerId, User.user.getId());
    }
}