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

        Fixture heroFixture   = null;
        Fixture objectFixture = null;

        if(firstObjectFixture.getUserData() == ObjectType.HERO) {
            heroFixture   = firstObjectFixture;
            objectFixture = secondObjectFixture;
        } else if(secondObjectFixture.getUserData() == ObjectType.HERO) {
            heroFixture   = secondObjectFixture;
            objectFixture = firstObjectFixture;
        }

        if(heroFixture != null) {
            if(objectFixture.getUserData() == ObjectType.REWARD_MY_XP)
                this.gameScreen.moneyBoxContact(objectFixture);

            if(objectFixture.getUserData() == ObjectType.REWARD_HH)
                this.gameScreen.healthBoxContact(objectFixture);
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