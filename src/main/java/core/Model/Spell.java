package core.Model;

public class Spell {
    private int id;
    private String name;
    private String nameSlug;
    private int hitPower;
    private int price;
    private String description;

    public Spell(int id, String name, String nameSlug, int hitPower, int price, String description) {
        this.id = id;
        this.name = name;
        this.nameSlug = nameSlug;
        this.hitPower = hitPower;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public void setNameSlug(String nameSlug) {
        this.nameSlug = nameSlug;
    }

    public int getHitPower() {
        return hitPower;
    }

    public void setHitPower(int hitPower) {
        this.hitPower = hitPower;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
