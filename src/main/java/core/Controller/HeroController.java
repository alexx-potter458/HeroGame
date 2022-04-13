package core.Controller;

import core.Database.HeroDatabase;
import core.Model.Hero;

import java.util.ArrayList;

public class HeroController {

    public ArrayList<Hero> getAllHeroes() {
        return (new HeroDatabase()).loadAllHeroes();
    }

    public void buy(Hero hero) {
        System.out.println(hero);
        System.out.println("trebuie facuta si acutalizarea de bani");
    }
}
