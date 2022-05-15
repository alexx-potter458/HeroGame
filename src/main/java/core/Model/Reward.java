package core.Model;

public class Reward {
    private final int     id;
    private final String  name;
    private final String  type;
    private final int     value;
    private final int     minimumLevel;

    public Reward(int id, String name, String type, int value, int minimumLevel) {
        this.id           = id;
        this.name         = name;
        this.type         = type;
        this.value        = value;
        this.minimumLevel = minimumLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getMinimumLevel() {
        return minimumLevel;
    }

}
