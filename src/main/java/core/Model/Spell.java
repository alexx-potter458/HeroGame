package core.Model;

public class Spell {
    private final int    id;
    private final String name;
    private final String nameSlug;
    private final int    hitPower;
    private final int    price;
    private final String description;

    public Spell(int id, String name, String nameSlug, int hitPower, int price, String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.hitPower    = hitPower;
        this.price       = price;
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

    public String getDescription() {
        return description;
    }

}