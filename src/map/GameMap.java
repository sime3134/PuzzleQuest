package map;

import IO.Persistable;
import content.ContentManager;
import core.Vector2D;
import display.Camera;
import settings.Settings;

import java.awt.*;
import java.util.Arrays;

/**
 * @author Simon Jern
 * Implements a map in the game. The maps are built up with tiles.
 */
public class GameMap implements Persistable {

    private Tile[][] tiles;

    public GameMap() {

    }

    //region Getters and Setters (click to open)

    public int getWidth(){
        return tiles.length * Settings.getSpriteSize();
    }

    public int getHeight(){
        return tiles[0].length * Settings.getSpriteSize();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean isWithinBounds(int gridX, int gridY) {
        return gridX >= 0 && gridX < tiles.length
                && gridY >= 0 && gridY < tiles[0].length;
    }

    public void setTile(int gridX, int gridY, Tile tile) {
        tiles[gridX][gridY] = tile;
    }

    public Vector2D getRandomPositionOnMap() {
        double x = Math.random() * tiles.length * Settings.getSpriteSize();
        double y = Math.random() * tiles[0].length * Settings.getSpriteSize();
        return new Vector2D(x, y);
    }
    //endregion

    public GameMap(int mapWidth, int mapHeight, ContentManager content) {
        tiles = new Tile[mapWidth][mapHeight];
        initializeTiles(content);
    }

    private void initializeTiles(ContentManager content) {
        for(Tile[] row : tiles){
            Arrays.fill(row, new Tile(content, 501, "terrain"));
        }
    }

    /**
     * Draw map in view at the moment.
     * We add margins to make sure we don't miss any tiles.
     */
    public void draw(Graphics g, Camera camera) {
        double startPosX = Math.max(0, camera.getPosition().getX() / Settings.getSpriteSize()); //left border of screen
        double endPosX = Math.min(tiles.length,
                (camera.getPosition().getX() + Settings.getScreenWidth()) / Settings.getSpriteSize()
                        + Settings.getRenderMargin()); //right border of screen
        double startPosY = Math.max(0, camera.getPosition().getY() / Settings.getSpriteSize()); //top of screen
        double endPosY = Math.min(tiles[0].length,
                (camera.getPosition().getY() + Settings.getScreenHeight()) / Settings.getSpriteSize()
                        + Settings.getRenderMargin()); //bottom of screen

        for(int x = (int)startPosX; x < (int)endPosX; x++){
            for(int y = (int)startPosY; y < (int)endPosY; y++){
                g.drawImage(tiles[x][y].getSprite(),
                        x * Settings.getSpriteSize() - camera.getPosition().intX(),
                        y * Settings.getSpriteSize() - camera.getPosition().intY(),
                        null);

                if(Settings.getShouldRenderGrid().getValue()) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(
                            x * Settings.getSpriteSize() - camera.getPosition().intX(),
                            y * Settings.getSpriteSize() - camera.getPosition().intY(),
                            Settings.getSpriteSize(),
                            Settings.getSpriteSize()
                    );
                }
            }
        }
    }

    /**
     * Used to reload the tile sheet images after loading a game map since
     * images can't be serialized.
     */
    public void reloadGraphics(ContentManager content){
        for(Tile[] row : tiles){
            for(Tile tile : row){
                tile.reloadGraphics(content);
            }
        }
    }

    /**
     * Writes a game map to file.
     */
    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName())
                .append(DELIMITER)
                .append(tiles.length)
                .append(DELIMITER)
                .append(tiles[0].length)
                .append(DELIMITER)
                .append(SECTION_DELIMETER);

        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles[0].length; y++) {
                sb.append(tiles[x][y].serialize());
                sb.append(LIST_DELIMETER);
            }
            sb.append(COLUMN_DELIMETER);
        }

        return sb.toString();
    }

    /**
     * Loads a game map from file.
     */
    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        tiles = new Tile[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[2])];

        String tileSection = serializedData.split(SECTION_DELIMETER)[1];
        String[] columns = tileSection.split(COLUMN_DELIMETER);

        for(int x = 0; x < tiles.length; x++) {
            String[] tilesInColumn = columns[x].split(LIST_DELIMETER);

            for(int y = 0; y < tiles[0].length; y++) {
                Tile tile = new Tile();
                tile.applySerializedData(tilesInColumn[y]);

                tiles[x][y] = tile;
            }
        }
    }
}
