package utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import core.Screen.Screen;

public class TileMapHelper {
    private final Screen  screen;
    private final boolean isGame;
    private Integer       mapWidth;

    public TileMapHelper(Screen screen, boolean isGame) {
        this.isGame    = isGame;
        this.screen    = screen;
        this.mapWidth  = 0;
    }

    public OrthogonalTiledMapRenderer setupMap(String path) {
        TiledMap tiledMap = new TmxMapLoader().load("maps/" + path + ".tmx");

        if(isGame) {
            parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
            this.mapWidth = tiledMap.getProperties().get("width", Integer.class) * tiledMap.getProperties().get("tilewidth", Integer.class);
        }

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for(MapObject object: mapObjects)
            if(object instanceof PolygonMapObject)
                createStaticBody((PolygonMapObject) object);
    }

    private void createStaticBody(PolygonMapObject object) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type    = BodyDef.BodyType.StaticBody;
        Body body       = screen.getWorld().createBody(bodyDef);
        Shape shape     = createPolygonShape(object);

        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject object) {
        float[] vertices        = object.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < vertices.length / 2; i ++) {
            Vector2 current     = new Vector2(vertices[i * 2] / Config.PPM, vertices[i * 2 + 1] / Config.PPM);
            worldVertices[i]    = current;
        }

        PolygonShape shape      = new PolygonShape();
        shape.set(worldVertices);

        return shape;
    }

    public Integer getMapWidth() {
        return mapWidth;
    }

}
