package utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TileMapHelper {
    private TiledMap tiledMap;

    public TileMapHelper() {

    }

    public OrthogonalTiledMapRenderer setupMap(String path) {
        this.tiledMap = new TmxMapLoader().load("maps/" + path + ".tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

}
