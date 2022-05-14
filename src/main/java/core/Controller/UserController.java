package core.Controller;

import core.Database.UserDatabase;
import core.Model.User;

public class UserController {
    public void loadUser() {
        UserDatabase userDatabase = new UserDatabase();
        boolean userCreated = userDatabase.loadUser();
        if(!userCreated) {
            new User(0,"", 0, 0, 0, 1);
        }
    }

    public void createUser(String nickname) {
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.createUser(nickname);
        this.loadUser();
    }

    public void deleteUser() {
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.deleteAllUsers();
        new User(0,"", 0, 0, 0, 1);
    }

    public void setMoney(int money) {
        UserDatabase userDatabase = new UserDatabase();

        userDatabase.setMoney(User.user.getId(), money);
        this.loadUser();
    }

    public void addProgress(int moneyToSave, int experienceToSave) {
        UserDatabase userDatabase = new UserDatabase();
        int allExperience         = experienceToSave + User.user.getScore();

        userDatabase.setMoney(User.user.getId(), moneyToSave + User.user.getMoney());

        if(allExperience > (User.user.getLevel() + 1) * 1000) {
            allExperience -= (User.user.getLevel() + 1) * 1000;

            userDatabase.setExperience(User.user.getId(), allExperience);
            userDatabase.setLevel(User.user.getId(), User.user.getLevel() + 1);
        } else
            userDatabase.setExperience(User.user.getId(), allExperience);

        this.loadUser();
    }

}