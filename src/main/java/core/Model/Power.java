package core.Model;

public class Power {
    private int id;
    private String name;
    private String nameSlug;
    private int price;

    public Power(int id, String name, String nameSlug, int price) {
        this.id = id;
        this.name = name;
        this.nameSlug = nameSlug;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
