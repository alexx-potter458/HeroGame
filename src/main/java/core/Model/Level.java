package core.Model;

public class Level {
    private final int    id;
    private final String name;
    private final int    unlocked;
    private final int    baseScore;

    public Level(int id, String name, int unlocked, int baseScore) {
        this.id = id;
        this.name = name;
        this.unlocked = unlocked;
        this.baseScore = baseScore;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUnlocked() {
        return unlocked;
    }

    public int getBaseScore() {
        return baseScore;
    }

}
