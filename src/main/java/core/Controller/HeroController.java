package core.Controller;

import core.Database.HeroDatabase;
import core.Model.Hero;
import core.Model.User;

import java.util.ArrayList;

public class HeroController {

    public ArrayList<Hero> getAllHeroes() {
        return (new HeroDatabase()).loadAllHeroes();
    }

    public void buy(Hero hero) {
        HeroDatabase heroDatabase = new HeroDatabase();
        UserController userController = new UserController();
        heroDatabase.resetUserHeroes();
        heroDatabase.buyHero(User.user.getId(), hero.getId());
        userController.setMoney(User.user.getMoney() - hero.getPrice());
    }
}
