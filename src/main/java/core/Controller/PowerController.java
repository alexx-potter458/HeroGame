package core.Controller;

import core.Database.PowerDatabase;
import core.Model.Power;
import java.util.ArrayList;

public class PowerController {
    public ArrayList<Power> getAllHeroes() {
        return (new PowerDatabase()).loadAllPowers();
    }

}
