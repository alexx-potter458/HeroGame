package objects;

public class User {
    public static User user;
    private int id;
    private String nickname;
    private int money;
    private int level;
    private int firstTime;
    private int score;

    public User(int id, String name, int score, int money, int level, int firstTime) {
        this.id = id;
        this.nickname = name;
        this.firstTime = firstTime;
        this.level = level;
        this.money = money;
        this.score = score;

        user = this;
    }

    public String getNickname() {
        return this.nickname;
    }

    public boolean isFirstTime() {
        return this.firstTime == 1;
    }
}
