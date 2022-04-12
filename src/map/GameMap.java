package map;

import IO.Persistable;
import content.ContentManager;
import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import entity.Scenery;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a map in the game. The maps are built up with tiles.
 */
public class GameMap implements Persistable {

    private Tile[][] tiles;
    private List<Scenery> sceneryList;

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

    public List<Scenery> getSceneryList() {
        return sceneryList;
    }

    public void setSceneryList(List<Scenery> sceneryList) {
        this.sceneryList = sceneryList;
    }

    //endregion

    public GameMap(){
        sceneryList = new ArrayList<>();
    }

    public GameMap(int mapWidth, int mapHeight, ContentManager content) {
        tiles = new Tile[mapWidth][mapHeight];
        sceneryList = new ArrayList<>();
        initializeTiles(content);
    }

    private void initializeTiles(ContentManager content) {
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[0].length; y++){
                tiles[x][y] = new Tile(content, 501, "terrain", true);
            }
        }
    }

    public List<CollisionBox> getCollidingUnwalkableTileBoxes(CollisionBox collisionBox){
        int gridX = (int) (collisionBox.getBounds().getX() / Settings.getSpriteSize());
        int gridY = (int) (collisionBox.getBounds().getY() / Settings.getSpriteSize());

        List<CollisionBox> collidingUnwalkableTileBoxes = new ArrayList<>();

        for(int x = gridX - 1; x < gridX + 2; x++){
            for(int y = gridY - 1; y < gridY + 2; y++){
                if(isWithinBounds(x, y) && !getTile(x, y).isWalkable()){
                    CollisionBox gridCollisionBox = getGridCollisionBox(x, y);

                    if(collisionBox.collidingWith(gridCollisionBox)) {
                        collidingUnwalkableTileBoxes.add(gridCollisionBox);
                    }
                }
            }
        }

        return collidingUnwalkableTileBoxes;
    }

    private CollisionBox getGridCollisionBox(int x, int y) {
        return new CollisionBox(new Rectangle(
           x * Settings.getSpriteSize(),
           y * Settings.getSpriteSize(),
           Settings.getSpriteSize(),
           Settings.getSpriteSize()
        ));
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
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
                int drawPositionX = x * Settings.getSpriteSize() - camera.getPosition().intX();
                int drawPositionY = y * Settings.getSpriteSize() - camera.getPosition().intY();

                g.drawImage(tiles[x][y].getSprite(), drawPositionX, drawPositionY, null);

                if(Settings.getShouldRenderTileWalkability().getValue()){
                    Color overlayColor = tiles[x][y].isWalkable()
                            ? new Color(0, 255, 0, 85)
                            : new Color(255, 0, 0, 85);
                    g.setColor(overlayColor);
                    g.fillRect(drawPositionX, drawPositionY, Settings.getSpriteSize(), Settings.getSpriteSize());
                }
            }
        }

        if(Settings.getShouldRenderGrid().getValue()) {
            g.setColor(Color.LIGHT_GRAY);
            for(int x = (int)startPosX; x < endPosX; x++){
                g.drawLine(x * Settings.getSpriteSize() - camera.getPosition().intX(),
                        (int)startPosY * Settings.getSpriteSize() - camera.getPosition().intY(),
                        x * Settings.getSpriteSize() - camera.getPosition().intX(),
                        (int)endPosY * Settings.getSpriteSize() - camera.getPosition().intY()
                );
            }

            for(int y = (int)startPosY; y < endPosY; y++){
                g.drawLine((int)startPosX * Settings.getSpriteSize() - camera.getPosition().intX(),
                        y * Settings.getSpriteSize() - camera.getPosition().intY(),
                        (int)endPosX * Settings.getSpriteSize() - camera.getPosition().intX(),
                        y * Settings.getSpriteSize() - camera.getPosition().intY()
                );
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

        sceneryList.forEach(scenery -> scenery.loadGraphics(content));
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

        sb.append(SECTION_DELIMETER);
        sceneryList.forEach(scenery -> {
            sb.append(scenery.serialize());
            sb.append(COLUMN_DELIMETER);
        });

        return sb.toString();
    }

    /**
     * Loads a game map from file.
     */
    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        String[] sections = serializedData.split(SECTION_DELIMETER);
        tiles = new Tile[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[2])];

        String tileSection = sections[1];
        String[] columns = tileSection.split(COLUMN_DELIMETER);

        for(int x = 0; x < tiles.length; x++) {
            String[] tilesInColumn = columns[x].split(LIST_DELIMETER);

            for(int y = 0; y < tiles[0].length; y++) {
                Tile tile = new Tile();
                tile.applySerializedData(tilesInColumn[y]);

                tiles[x][y] = tile;
            }
        }

        if(sections.length > 2){
            String scenerySection = sections[2];
            String[] serializedSceneries = scenerySection.split(COLUMN_DELIMETER);
            for(String serializedScenery : serializedSceneries){
                Scenery scenery = new Scenery();
                scenery.applySerializedData(serializedScenery);
                sceneryList.add(scenery);
            }
        }
    }
}
