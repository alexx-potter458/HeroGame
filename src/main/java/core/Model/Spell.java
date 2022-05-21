package core.Model;

public class Spell {
    private final int    id;
    private final String name;
    private final String nameSlug;
    private final int    hitPower;
    private final int    price;
    private final int    speed;
    private final int    bullets;
    private final int    isActive;
    private final String description;

    public Spell(int id, String name, String nameSlug, int hitPower, int price, int speed, int bulletCounter, String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.hitPower    = hitPower;
        this.price       = price;
        this.speed       = speed;
        this.bullets     = bulletCounter;
        this.isActive    = 0;
        this.description = description;
    }

    public Spell(int id, String name, String nameSlug, int hitPower, int price, int speed, int bulletCounter, int isActive, String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.hitPower    = hitPower;
        this.price       = price;
        this.speed       = speed;
        this.bullets     = bulletCounter;
        this.isActive    = isActive;
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

    public int getHitPower() {
        return hitPower;
    }

    public int getPrice() {
        return price;
    }

    public int getSpeed() {
        return speed;
    }

    public int getBullets() {
        return this.bullets;
    }

    public String getDescription() {
        return description;
    }

    public int isActive() {
        return this.isActive;
    }

}