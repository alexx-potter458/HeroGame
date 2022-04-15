package core.Controller;

import core.Database.SpellDatabase;
import core.Model.Hero;
import core.Model.Spell;

import java.util.ArrayList;

public class SpellController {

    public ArrayList<Spell> getAllSpells() {
        return (new SpellDatabase().loadAllSpells());
    }

    public void buy(Spell hero) {
        System.out.println(hero);
        System.out.println("trebuie facuta si acutalizarea de bani");
    }
}
