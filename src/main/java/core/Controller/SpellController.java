package core.Controller;

import core.Database.SpellDatabase;
import core.Model.Spell;
import core.Model.User;

import java.util.ArrayList;

public class SpellController {

    public ArrayList<Spell> getAllSpells() {
        return (new SpellDatabase().loadAllSpells());
    }

    public void buy(Spell spell) {
        SpellDatabase spellDatabase = new SpellDatabase();
        HeroController heroController = new HeroController();
        UserController userController = new UserController();
        spellDatabase.buySpell(User.user.getId(), heroController.getMainHero().getId(), spell.getId());
        userController.setMoney(User.user.getMoney() - spell.getPrice());
    }
}
