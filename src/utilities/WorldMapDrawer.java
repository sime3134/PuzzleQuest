package utilities;

import core.Vector2D;
import entity.Scenery;
import map.GameMap;
import settings.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorldMapDrawer {

    private WorldMapDrawer() {
    }

    public static void generateFullWorldMap(List<GameMap> maps, int imageSize, int worldMapLength) {
        int lengthOfOneMap = imageSize / worldMapLength;

        BufferedImage mapImage = (BufferedImage) ImgUtils.createCompatibleImage(imageSize, imageSize,
                ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D g = mapImage.createGraphics();

        int x1 = -1;
        int y1 = -1;

        for(int mapNumber = 0; mapNumber < maps.size(); mapNumber++) {

            double minimapRatio = calculateRatio(maps.get(mapNumber), lengthOfOneMap);

            int spriteSizeOnMinimap = (int) Math.round(Settings.getTileSize() * minimapRatio);

            Vector2D pixelOffset = calculatePixelOffset(lengthOfOneMap, maps.get(mapNumber), spriteSizeOnMinimap);

            x1++;
            if(mapNumber % 5 == 0){
                y1++;
                x1 = 0;
            }

            drawTiles(maps, lengthOfOneMap, g, x1, y1, mapNumber, minimapRatio, spriteSizeOnMinimap);

            List<Scenery> scenery = maps.get(mapNumber).getSceneryList();

            drawScenery(lengthOfOneMap, g, x1, y1, minimapRatio, spriteSizeOnMinimap, pixelOffset, scenery);

        }


        saveToFile(mapImage, "./world_map.png");

        g.dispose();
    }

    public static void generateMap(GameMap gameMap, int imageSize, String mapName) {
        BufferedImage mapImage = (BufferedImage) ImgUtils.createCompatibleImage(imageSize, imageSize,
                ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D g = mapImage.createGraphics();

        double minimapRatio = calculateRatio(gameMap, imageSize);

        int spriteSizeOnMinimap = (int) Math.round(Settings.getTileSize() * minimapRatio);

        Vector2D pixelOffset = calculatePixelOffset(imageSize, gameMap, spriteSizeOnMinimap);

        for(int x = 0; x < gameMap.getTiles().length; x++){
            for(int y = 0; y < gameMap.getTiles()[0].length; y++){
                g.drawImage(
                        getScaledSprite(gameMap.getTiles()[x][y].getSprite(), minimapRatio),
                        x * spriteSizeOnMinimap + (imageSize - gameMap.getTiles().length * spriteSizeOnMinimap) / 2,
                        y * spriteSizeOnMinimap + (imageSize - gameMap.getTiles()[0].length * spriteSizeOnMinimap) / 2,
                        null
                );
            }
        }

        List<Scenery> sceneries = gameMap.getSceneryList();

        sceneries.stream().forEach(scenery -> {
            Vector2D positionWithOffset = scenery.getPosition().getCopy();
            positionWithOffset.subtract(scenery.getRenderOffset());
            positionWithOffset.add(pixelOffset);

            g.drawImage(
                    getScaledSprite(scenery.getSprite(), minimapRatio),
                    (int) Math.round(positionWithOffset.getX() / Settings.getTileSize() * spriteSizeOnMinimap + pixelOffset.getX()),
                    (int) Math.round(positionWithOffset.getY() / Settings.getTileSize() * spriteSizeOnMinimap + pixelOffset.getY()),
                    null);
        });

        saveToFile(mapImage, "./" + mapName.substring(0, mapName.length() - 4) + ".png");

        g.dispose();
    }

    private static void saveToFile(BufferedImage mapImage, String path) {
        try {
            ImageIO.write(mapImage, "png", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void drawTiles(List<GameMap> maps, int lengthOfOneMap, Graphics2D g, int x1, int y1, int mapNumber, double minimapRatio, int spriteSizeOnMinimap) {
        for(int x = 0; x < maps.get(mapNumber).getTiles().length; x++){
            for(int y = 0; y < maps.get(mapNumber).getTiles()[0].length; y++){
                g.drawImage(
                        getScaledSprite(maps.get(mapNumber).getTiles()[x][y].getSprite(), minimapRatio),
                        x1 * lengthOfOneMap + x * spriteSizeOnMinimap + (lengthOfOneMap - maps.get(mapNumber).getTiles().length * spriteSizeOnMinimap) / 2,
                        y1 * lengthOfOneMap + y * spriteSizeOnMinimap + (lengthOfOneMap - maps.get(mapNumber).getTiles()[0].length * spriteSizeOnMinimap) / 2,
                        null
                );
            }
        }
    }

    private static void drawScenery(int lengthOfOneMap, Graphics2D g, int x1, int y1, double minimapRatio, int spriteSizeOnMinimap, Vector2D pixelOffset, List<Scenery> scenery) {
        int finalX = x1;
        int finalY = y1;
        scenery.stream().forEach(gameObject -> {
            Vector2D positionWithOffset = gameObject.getPosition().getCopy();
            positionWithOffset.subtract(gameObject.getRenderOffset());
            positionWithOffset.add(pixelOffset);

            g.drawImage(
                    getScaledSprite(gameObject.getSprite(), minimapRatio),
                    (int) Math.round(finalX * lengthOfOneMap + positionWithOffset.getX() / Settings.getTileSize() * spriteSizeOnMinimap + pixelOffset.getX()),
                    (int) Math.round(finalY * lengthOfOneMap + positionWithOffset.getY() / Settings.getTileSize() * spriteSizeOnMinimap + pixelOffset.getY()),
                    null);
        });
    }

    private static Vector2D calculatePixelOffset(int lengthOfOneMap, GameMap gameMap, int spriteSizeOnMinimap) {
        return new Vector2D(
                (lengthOfOneMap - gameMap.getTiles().length * spriteSizeOnMinimap) / 2f,
                (lengthOfOneMap - gameMap.getTiles()[0].length * spriteSizeOnMinimap) / 2f
        );
    }

    private static Image getScaledSprite(Image sprite, double minimapRatio){
        int scaledWidth = (int)Math.round(sprite.getWidth(null) * minimapRatio);
        int scaledHeight = (int)Math.round(sprite.getHeight(null) * minimapRatio);

        return sprite.getScaledInstance(scaledWidth, scaledHeight, 0);
    }

    private static double calculateRatio(GameMap gameMap, int lengthOfOneMap) {
        return Math.min(
                lengthOfOneMap / (double) gameMap.getWidth(),
                lengthOfOneMap / (double) gameMap.getHeight()
        );
    }
}
