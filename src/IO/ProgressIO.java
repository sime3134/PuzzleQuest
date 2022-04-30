package IO;

import main.Game;
import main.state.GameState;

import java.io.*;

public class ProgressIO {

    private ProgressIO(){}

    public static void save(GameState gameState, String filePath){
        if(filePath.contains(".txt")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(gameState.serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".txt"))) {
                writer.write(gameState.serialize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static GameState load(Game game, String path){
        File file = new File(path);
        if (file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                GameState gameState = new GameState(game);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(System.lineSeparator());
                    sb.append(line);
                }

                gameState.applySerializedData(sb.toString());

                return gameState;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new GameState(game);
    }

}
