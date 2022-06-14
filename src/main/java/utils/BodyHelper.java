package utils;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {
    public static Body initBody(float x, float y, float width, float height, float density, int bodyType, World world, ObjectType type) {
        BodyDef bodyInit = new BodyDef();

        if(bodyType == 0)
            bodyInit.type = BodyDef.BodyType.StaticBody;
        else if(bodyType == 1)
            bodyInit.type = BodyDef.BodyType.KinematicBody;
        else
            bodyInit.type = BodyDef.BodyType.DynamicBody;

        bodyInit.position.set(x / Config.PPM, y / Config.PPM);
        bodyInit.fixedRotation   = true;

        Body body               = world.createBody(bodyInit);
        PolygonShape polygonShape      = new PolygonShape();

        polygonShape.setAsBox(width / 2 / Config.PPM, height / 2 / Config.PPM);

        FixtureDef fixtureInit   = new FixtureDef();
        fixtureInit.shape        = polygonShape;
        fixtureInit.friction     = 0;
        fixtureInit.density      = density;

        body.createFixture(fixtureInit).setUserData(type);
        polygonShape.dispose();

        return body;
    }

}