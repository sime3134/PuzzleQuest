package map;

import IO.MapIO;
import content.ContentManager;
import display.Camera;
import entity.GameObject;
import entity.Scenery;
import main.Game;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapManager {

    private GameMap currentMap;

    private Map<String, GameMap> maps;

    public GameMap getByName(String name) {
        return maps.get(name);
    }

    public GameMap getCurrent() {
        return currentMap;
    }

    public void setCurrent(GameMap newMap) {
        this.currentMap = newMap;
    }

    public MapManager() {
        maps = new HashMap<>();
    }

    public void loadAll(ContentManager content, String basePath) {
        maps = MapIO.loadAllMaps(content, basePath);
    }

    public void loadMap(String name) {
        currentMap = maps.get(name);
    }

    public void saveMap(Game game, String filePath) {
        currentMap.setSceneryList(game.getGameObjectsOfClass(Scenery.class));
        MapIO.save(currentMap, filePath);
    }


    public void createNewMap(int width, int height, ContentManager content) {
        currentMap = new GameMap(width, height, content);
    }

    public void spawn(Game game, GameObject gameObject) {
        game.getGameObjects().add(gameObject);
        currentMap.getSceneryList().add((Scenery) gameObject);
    }

    public void despawn(Game game, GameObject gameObject) {
        game.getGameObjects().remove(gameObject);
        currentMap.getSceneryList().remove((Scenery)gameObject);
    }

    public void update(Camera camera) {
        currentMap.update(camera);
    }

    public void draw(Graphics g, Camera camera) {
        currentMap.draw(g, camera);
    }
}
