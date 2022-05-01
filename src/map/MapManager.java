package map;

import IO.MapIO;
import content.ContentManager;
import controller.NPCController;
import core.Vector2D;
import display.Camera;
import entity.GameObject;
import entity.NPC;
import entity.Scenery;
import main.Game;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

    public void update(Game game) {
        currentMap.update(game.getCamera());
    }

    public void draw(Graphics g, Camera camera) {
        currentMap.draw(g, camera);
    }

    public List<GameMap> getWorldMap() {
        List<GameMap> worldMaps = new ArrayList<>();

        for(int i = 1; i < 26; i++){
            worldMaps.add(maps.get("map" + i));
        }

        return worldMaps;
    }

    public void initialize(Game game) {
        game.spawn(game.getGameState().getPlayer());
        game.updateObjectsDrawOrder();
    }
}