package core.Model;

import com.badlogic.gdx.physics.box2d.Fixture;

public class Enemy {
    private final int    id;
    private final int    width;
    private final int    height;
    private final int    hitPower;
    private final int    moneyReward;
    private final int    jumpPower;
    private final int    speed;
    private final int    health;
    private final String name;

    public Enemy(int id, int width, int height, int hitPower, int moneyReward, int jumpPower, int speed, int health, String name) {
        this.id          = id;
        this.width       = width;
        this.height      = height;
        this.hitPower    = hitPower;
        this.moneyReward = moneyReward;
        this.jumpPower   = jumpPower;
        this.health      = health;
        this.speed       = speed;
        this.name        = name;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHitPower() {
        return hitPower;
    }

    public int getMoneyReward() {
        return moneyReward;
    }

    public int getJumpPower() {
        return jumpPower;
    }

    public int getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

}