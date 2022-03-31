package map;

import content.ContentManager;
import core.Vector2D;
import display.Camera;
import settings.GameSettings;

import java.awt.*;
import java.util.Arrays;

/**
 * Implements a map in the game. The maps are built up with tiles.
 */
public class GameMap {

    private final GameSettings settings = GameSettings.getInstance();

    private final Tile[][] tiles;

    public int getWidth(){
        return tiles.length * settings.getSpriteSize();
    }

    public int getHeight(){
        return tiles[0].length * settings.getSpriteSize();
    }

    public GameMap(int mapWidth, int mapHeight, ContentManager content) {
        tiles = new Tile[mapWidth][mapHeight];
        initializeTiles(content);
    }

    private void initializeTiles(ContentManager content) {
        for(Tile[] row : tiles){
            Arrays.fill(row, new Tile(content.getTile("grass2")));
        }
    }

    /**
     * Draw map in view at the moment.
     * We add margins to make sure we don't miss any tiles.
     */
    public void draw(Graphics g, Camera camera) {
        double startPosX = Math.max(0, camera.getPosition().getX() / settings.getSpriteSize()); //left border of screen
        double endPosX = Math.min(tiles.length,
                (camera.getPosition().getX() + settings.getScreenWidth()) / settings.getSpriteSize()
                        + settings.getRenderMargin()); //right border of screen
        double startPosY = Math.max(0, camera.getPosition().getY() / settings.getSpriteSize()); //top of screen
        double endPosY = Math.min(tiles[0].length,
                (camera.getPosition().getY() + settings.getScreenHeight()) / settings.getSpriteSize()
                        + settings.getRenderMargin()); //bottom of screen

        for(int x = (int)startPosX; x < (int)endPosX; x++){
            for(int y = (int)startPosY; y < (int)endPosY; y++){
                g.drawImage(tiles[x][y].getSprite(),
                        x * settings.getSpriteSize() - camera.getPosition().intX(),
                        y * settings.getSpriteSize() - camera.getPosition().intY(),
                        null);
            }
        }
    }

    public Vector2D getRandomPositionOnMap() {
        double x = Math.random() * tiles.length * settings.getSpriteSize();
        double y = Math.random() * tiles[0].length * settings.getSpriteSize();
        return new Vector2D(x, y);
    }
}
