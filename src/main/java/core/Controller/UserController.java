package core.Controller;

import core.Database.UserDatabase;
import objects.User;

public class UserController {
    public void loadUser() {
        UserDatabase userDatabase = new UserDatabase();
        boolean userCreated = userDatabase.loadUser();
        if(!userCreated) {
            User noUser = new User(0,"", 0, 0, 0, 1);
        }
    }

    public void createUser(String nickname) {
        UserDatabase userDatabase = new UserDatabase();
        userDatabase.createUser(nickname);
        this.loadUser();
    }
}
