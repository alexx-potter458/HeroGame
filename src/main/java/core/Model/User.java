package core.Model;

public class User {
    public static User  user;
    private final int         id;
    private final String      nickname;
    private final int         money;
    private final int         level;
    private final int         firstTime;
    private final int         score;

    public User(int id, String name, int money, int level, int score, int firstTime) {
        this.id         = id;
        this.nickname   = name;
        this.firstTime  = firstTime;
        this.level      = level;
        this.money      = money;
        this.score      = score;

        user = this;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean isFirstTime() {
        return this.firstTime == 1;
    }

    public int getMoney() {
        return money;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getId() {
        return id;
    }

}