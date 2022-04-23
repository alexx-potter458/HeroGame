package core.Controller;

import core.Database.HeroDatabase;
import core.Model.Hero;
import core.Model.User;

import java.util.ArrayList;

public class HeroController {

    public ArrayList<Hero> getAllHeroes() {
        return (new HeroDatabase()).loadAllHeroes();
    }
    public Hero getMainHero() {
        return (new HeroDatabase()).getMainHero();
    }

    public void buy(Hero hero) {
        HeroDatabase heroDatabase = new HeroDatabase();
        UserController userController = new UserController();
        heroDatabase.resetUserHeroes();
        heroDatabase.buyHero(User.user.getId(), hero.getId());
        userController.setMoney(User.user.getMoney() - hero.getPrice());
    }

    public int getHeroPowerId(Hero mainHero, int id) {
        return (new HeroDatabase()).getHeroPowerId(mainHero, id);
    }

    public ArrayList<Hero> getBoughtHeroes() {
        return (new HeroDatabase()).loadBoughtHeroes();
    }

    public void makeHeroPrimary(Hero selectedHero) {
        HeroDatabase heroDatabase = new HeroDatabase();
        heroDatabase.resetUserHeroes();
        heroDatabase.makePrimary(User.user.getId(), selectedHero.getId());
    }
}
