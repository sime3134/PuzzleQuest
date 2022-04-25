package IO;

import content.ContentManager;
import map.GameMap;

import java.io.*;

/**
 * @author Simon Jern
 * Implements loading and saving of game maps from files.
 */
public class MapIO {

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

    public static GameMap loadFromName(ContentManager content, String mapName) {
        if (MapIO.class.getResource("/maps/" + mapName + ".txt") != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MapIO.class.getResource("/maps/"
                    + mapName + ".txt").getFile()))) {
                GameMap gameMap = new GameMap();

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

        return new GameMap(32, 32, content);
    }

    public static GameMap loadFromPath(ContentManager content, String nameOrPath) {
        File file = new File(nameOrPath);
        if (file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(nameOrPath))) {
                GameMap gameMap = new GameMap();

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

        return new GameMap(32, 32, content);
    }
}
