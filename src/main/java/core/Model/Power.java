package core.Model;

public class Power {
    private int          id;
    private String       name;
    private final String nameSlug;
    private final int    price;
    private final String description;

    public Power(int id, String name, String nameSlug, int price, String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.price       = price;
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

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

}
