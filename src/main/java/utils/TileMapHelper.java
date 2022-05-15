package utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import core.Controller.RewardController;
import core.Model.Reward;
import core.Object.HealthBoxObject;
import core.Object.MoneyBoxObject;
import core.Screen.GameScreen;
import core.Screen.Screen;

import java.util.ArrayList;

public class TileMapHelper {
    private final Screen  screen;
    private final boolean isGame;
    private Integer       mapWidth;
    private int           level;

    public TileMapHelper(Screen screen, boolean isGame) {
        this.isGame   = isGame;
        this.screen   = screen;
        this.mapWidth = 0;
        this.level    = -1;
    }

    public TileMapHelper(Screen screen, boolean isGame, int level) {
        this(screen, isGame);
        this.level = level;
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
        RewardController rwc            = new RewardController();
        ArrayList<Reward> moneyRewards  = rwc.getMoneyRewards(this.level);
        ArrayList<Reward> healthRewards = rwc.getHealthRewards(this.level);

        for(MapObject object: mapObjects) {
            if(object instanceof PolygonMapObject)
                createStaticBody((PolygonMapObject) object);

            if(object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                String rectangleName = object.getName();

                if(rectangleName.equals("HH"))
                    ((GameScreen) this.screen).addHealthBox( new HealthBoxObject(this.screen, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2, healthRewards.get((int)(Math.random() * (healthRewards.size())))));

                if(rectangleName.equals("MY_XP"))
                    ((GameScreen) this.screen).addMoneyBox( new MoneyBoxObject(this.screen, rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2, moneyRewards.get((int)(Math.random() * (moneyRewards.size())))));
            }
        }
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
