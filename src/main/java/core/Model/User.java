package core.Model;

public class User {
    public static User user;
    private int id;
    private String nickname;
    private int money;
    private int level;
    private int firstTime;
    private int score;

    public User(int id, String name, int money, int level, int score, int firstTime) {
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
