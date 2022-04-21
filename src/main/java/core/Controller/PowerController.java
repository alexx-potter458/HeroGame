package core.Controller;

import core.Database.PowerDatabase;
import core.Database.SpellDatabase;
import core.Model.Power;
import core.Model.Spell;
import core.Model.User;

import java.util.ArrayList;

public class PowerController {
    public ArrayList<Power> getAllPowers() {
        return (new PowerDatabase()).loadAllPowers();
    }

    public void buy(Power power) {
        PowerDatabase powerDatabase = new PowerDatabase();
        UserController userController = new UserController();
        HeroController heroController = new HeroController();
        powerDatabase.buyPower(User.user.getId(), heroController.getHeroPowerId(heroController.getMainHero(), power.getId()));
        userController.setMoney(User.user.getMoney() - power.getPrice());
    }

}
