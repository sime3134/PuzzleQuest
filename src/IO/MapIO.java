package IO;

import content.ContentManager;
import map.GameMap;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Jern
 * Implements loading and saving of game maps from files.
 */
public class MapIO {

    private MapIO(){}

    public static void save(GameMap gameMap, String filePath){
        if(filePath.contains(".txt")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(gameMap.serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".txt"))) {
                writer.write(gameMap.serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, GameMap> loadAllMaps(ContentManager content, String basePath) {
        Map<String, GameMap> maps = new HashMap<>();

        if (MapIO.class.getResource(basePath) != null) {
            URL resource = MapIO.class.getResource(basePath);
            File file = new File(resource.getFile());
            String[] mapPaths = file.list((current, name) -> new File(current, name).isFile());

            for(String fileName : mapPaths) {
                try (BufferedReader reader = new BufferedReader(new FileReader(MapIO.class.getResource(basePath + "/" + fileName).getFile()))) {

                    String fileNameWithoutExtension = fileName.substring(0, fileName.length() - 4);
                    GameMap gameMap = new GameMap(fileNameWithoutExtension);

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(System.lineSeparator());
                        sb.append(line);
                    }

                    gameMap.applySerializedData(sb.toString());

                    gameMap.reloadGraphics(content);

                    maps.put(fileNameWithoutExtension, gameMap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return maps;
    }

    public static GameMap loadFromName(ContentManager content, String mapName) {
        if (MapIO.class.getResource("/maps/" + mapName + ".txt") != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MapIO.class.getResource("/maps/"
                    + mapName + ".txt").getFile()))) {
                GameMap gameMap = new GameMap(mapName);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(System.lineSeparator());
                    sb.append(line);
                }

                gameMap.applySerializedData(sb.toString());

                gameMap.reloadGraphics(content);

                return gameMap;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new GameMap(64, 64, content);
    }

    public static GameMap loadFromPath(ContentManager content, String path) {
        File file = new File(path);
        if (file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                String fileNameWithoutExtension = file.getName().substring(0, file.getName().length() - 4);
                GameMap gameMap = new GameMap(fileNameWithoutExtension);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(System.lineSeparator());
                    sb.append(line);
                }

                gameMap.applySerializedData(sb.toString());

                gameMap.reloadGraphics(content);

                return gameMap;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new GameMap(64, 64, content);
    }
}
