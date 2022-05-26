package core.Model;

public class Power {
    private int          id;
    private String       name;
    private final int    price;
    private final int    isActive;
    private final int    activeTime;
    private final int    refuelTime;
    private final int    value;
    private final String description;
    private final String type;
    private final String nameSlug;

    public Power(int id, String name, String nameSlug, int price, int isActive, int activeTime, int refuelTime, int value, String type, String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.price       = price;
        this.description = description;
        this.isActive    = isActive;
        this.value       = value;
        this.activeTime  = activeTime;
        this.refuelTime  = refuelTime;
        this.type        =  type;
    }

    public Power(int id, String name, String nameSlug, int price,  String description) {
        this.id          = id;
        this.name        = name;
        this.nameSlug    = nameSlug;
        this.price       = price;
        this.description = description;
        this.type        =  "";
        this.isActive    = 0;
        this.activeTime  = 0;
        this.value       = 0;
        this.refuelTime  = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNameSlug() {
        return nameSlug;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public int getValue() {
        return value;
    }

    public int getRefuelTime() {
        return refuelTime;
    }

    public int isActive() {
        return this.isActive;
    }

    public int getPrice() {
        return price;
    }

}