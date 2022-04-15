package core.Controller;

import core.Database.PowerDatabase;
import core.Model.Power;
import core.Model.Spell;

import java.util.ArrayList;

public class PowerController {
    public ArrayList<Power> getAllPowers() {
        return (new PowerDatabase()).loadAllPowers();
    }

    public void buy(Power hero) {
        System.out.println(hero);
        System.out.println("trebuie facuta si acutalizarea de bani");
    }

}
