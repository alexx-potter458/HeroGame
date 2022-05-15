package core.Model;

public class Hero {
    private final int     id;
    private final String  name;
    private final String  nameSlug;
    private final int     baseHealth;
    private final int     width;
    private final int     height;
    private final int     price;
    private final int     speed;
    private final int     hitPower;
    private final int    jumpPower;
    private final String  description;

    public Hero(int id, String name, String nameSlug, int baseHealth, int width, int height, int price, int speed, int hitPower, int jumpPower, String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.baseHealth  = baseHealth;
        this.width       = width;
        this.height      = height;
        this.price       = price;
        this.speed       = speed;
        this.hitPower    = hitPower;
        this.jumpPower   = jumpPower;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHitPower() {
        return hitPower;
    }

    public int getJumpPower() {
        return jumpPower;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}