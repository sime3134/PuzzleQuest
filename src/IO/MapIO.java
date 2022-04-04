package IO;

import content.ContentManager;
import map.GameMap;

import java.io.*;
import java.net.URL;

public class MapIO {

    public static void save(GameMap gameMap){
        final URL urlToResourcesFolder = MapIO.class.getResource("/");
        File mapsFolder = new File(urlToResourcesFolder.getFile() + "/maps");

        if(!mapsFolder.exists()){
            mapsFolder.mkdir();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(mapsFolder + "/map.pzq"))){
            writer.write(gameMap.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameMap load(ContentManager content){
        try(BufferedReader reader = new BufferedReader(new FileReader(MapIO.class.getResource("/maps/map.pzq").getFile()))){
            GameMap gameMap = new GameMap();

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(System.lineSeparator());
                sb.append(line);
            }

            gameMap.applySerializedData(sb.toString());

            gameMap.reloadGraphics(content);

            return gameMap;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
