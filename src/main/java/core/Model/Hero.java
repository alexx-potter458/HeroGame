package core.Model;

public class Hero {
    private int id;
    private String name;
    private String nameSlug;
    private int baseHealth;
    private int width;
    private int height;
    private int price;
    private int speed;
    private int hitPower;
    private  int jumpPower;

    public Hero(int id, String name, String nameSlug, int baseHealth, int width, int height, int price, int speed, int hitPower, int jumpPower) {
        this.id = id;
        this.name = name;
        this.nameSlug = nameSlug;
        this.baseHealth = baseHealth;
        this.width = width;
        this.height = height;
        this.price = height;
        this.speed = speed;
        this.hitPower = hitPower;
        this.jumpPower = jumpPower;
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

    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int baseHealth) {
        this.baseHealth = baseHealth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHitPower() {
        return hitPower;
    }

    public void setHitPower(int hitPower) {
        this.hitPower = hitPower;
    }

    public int getJumpPower() {
        return jumpPower;
    }

    public void setJumpPower(int jumpPower) {
        this.jumpPower = jumpPower;
    }

}


