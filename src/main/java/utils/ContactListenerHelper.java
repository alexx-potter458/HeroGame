package utils;

import com.badlogic.gdx.physics.box2d.*;
import core.Screen.GameScreen;

public class ContactListenerHelper implements ContactListener {
    private final GameScreen gameScreen;

    public ContactListenerHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture firstObjectFixture  = contact.getFixtureA();
        Fixture secondObjectFixture = contact.getFixtureB();

        if(firstObjectFixture == null || secondObjectFixture == null)
            return;
        if(firstObjectFixture.getUserData() == null || secondObjectFixture.getUserData() == null)
            return;

        Fixture heroFixture       = null;
        Fixture objectFixture     = null;
        Fixture heroBulletFixture = null;

        if(firstObjectFixture.getUserData() == ObjectType.HERO) {
            heroFixture   = firstObjectFixture;
            objectFixture = secondObjectFixture;
        } else if(secondObjectFixture.getUserData() == ObjectType.HERO) {
            heroFixture   = secondObjectFixture;
            objectFixture = firstObjectFixture;
        } else if(firstObjectFixture.getUserData() == ObjectType.HERO_BULLET) {
            heroBulletFixture   = firstObjectFixture;
            objectFixture       = secondObjectFixture;
        } else if(secondObjectFixture.getUserData() == ObjectType.HERO_BULLET) {
            heroBulletFixture   = secondObjectFixture;
            objectFixture       = firstObjectFixture;
        } else if(secondObjectFixture.getUserData() == ObjectType.KILLER) {
            heroBulletFixture   = secondObjectFixture;
            objectFixture       = firstObjectFixture;
        }

        if(heroFixture != null || heroBulletFixture != null) {
            if(objectFixture.getUserData() == ObjectType.REWARD_MY_XP)
                this.gameScreen.moneyBoxContact(objectFixture);

            if(objectFixture.getUserData() == ObjectType.REWARD_HH)
                this.gameScreen.healthBoxContact(objectFixture);
        }

        if(heroFixture != null) {
            if(objectFixture.getUserData() == ObjectType.ENEMY)
                this.gameScreen.heroEnemyContact(objectFixture);

            if(objectFixture.getUserData() == ObjectType.ENEMY_BULLET)
                this.gameScreen.enemyBulletHeroContact(objectFixture);

            if(objectFixture.getUserData() == ObjectType.KILLER)
                this.gameScreen.killerHeroContact();
        }

        if(heroBulletFixture != null) {
            if(objectFixture.getUserData() == ObjectType.ENEMY)
                this.gameScreen.heroBulletEnemyContact(objectFixture, heroBulletFixture);

            if(objectFixture.getUserData() == ObjectType.ENEMY_BULLET)
                this.gameScreen.heroBulletEnemyBulletContact(objectFixture, heroBulletFixture);
        }

        if(firstObjectFixture.getUserData() == ObjectType.KILLER || secondObjectFixture.getUserData() == ObjectType.KILLER) {
            if(firstObjectFixture.getUserData() == ObjectType.ENEMY)
                this.gameScreen.killEnemy(firstObjectFixture);
            else if(secondObjectFixture.getUserData() == ObjectType.ENEMY)
                this.gameScreen.killEnemy(firstObjectFixture);

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

}