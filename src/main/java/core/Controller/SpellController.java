package core.Controller;

import core.Database.SpellDatabase;
import core.Model.Hero;
import core.Model.Spell;
import core.Model.User;
import java.util.ArrayList;

public class SpellController {
    public ArrayList<Spell> getAllSpells() {
        return (new SpellDatabase().loadAllSpells());
    }

    public ArrayList<Spell> getActiveSpells() {
        return (new SpellDatabase().loadActiveSpells());
    }

    public void buy(Spell spell) {
        SpellDatabase spellDatabase   = new SpellDatabase();
        HeroController heroController = new HeroController();
        UserController userController = new UserController();

        spellDatabase.buySpell(User.user.getId(), heroController.getMainHero().getId(), spell.getId());
        userController.setMoney(User.user.getMoney() - spell.getPrice());
    }

    public ArrayList<Spell> getBoughtSpells() {
        return (new SpellDatabase().loadBoughtSpells());
    }

    public void changeSpellStatus(int active, int idSpell) {
        Hero hero = new HeroController().getMainHero();
        new SpellDatabase().changeSpellStatus((active == 1)? 0 : 1, idSpell, hero.getId(), User.user.getId());
    }

}