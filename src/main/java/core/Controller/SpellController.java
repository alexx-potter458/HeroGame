package core.Controller;

import core.Database.SpellDatabase;
import core.Model.Spell;

import java.util.ArrayList;

public class SpellController {

    public ArrayList<Spell> getAllSpells() {
        return (new SpellDatabase().loadAllSpells());
    }
}
