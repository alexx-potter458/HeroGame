package utils;

import com.badlogic.gdx.physics.box2d.*;

public class BodyHelper {
    public static Body createBody(float x, float y, float width, float height, float density, int bodyType, World world, ObjectType type) {
        BodyDef bodyDef = new BodyDef();

        if(bodyType == 0)
            bodyDef.type = BodyDef.BodyType.StaticBody;
        else if(bodyType == 1)
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        else
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(x / Config.PPM, y / Config.PPM);
        bodyDef.fixedRotation   = true;

        Body body               = world.createBody(bodyDef);
        PolygonShape shape      = new PolygonShape();

        shape.setAsBox(width / 2 / Config.PPM, height / 2 / Config.PPM);

        FixtureDef fixtureDef   = new FixtureDef();
        fixtureDef.shape        = shape;
        fixtureDef.friction     = 0;
        fixtureDef.density      = density;

        body.createFixture(fixtureDef).setUserData(type);
        shape.dispose();

        return body;
    }

}