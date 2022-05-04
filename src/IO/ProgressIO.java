package IO;

import main.Game;

import java.io.*;

public class ProgressIO {

    private ProgressIO(){}

    public static void save(Game game, String filePath){
        if(filePath.contains(".txt")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(game.serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".txt"))) {
                writer.write(game.serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void load(Game game, String path){
        File file = new File(path);
        if (file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(System.lineSeparator());
                    sb.append(line);
                }

                game.applySerializedData(sb.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
