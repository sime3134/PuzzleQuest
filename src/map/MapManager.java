package map;

import IO.MapIO;
import content.ContentManager;
import display.Camera;
import main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Map<String, GameMap> getMaps() {
        return maps;
    }

    public MapManager() {
        maps = new HashMap<>();
    }

    public void loadAll(ContentManager content, String basePath) {
        maps = MapIO.loadAllMaps(content, basePath);
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
}
