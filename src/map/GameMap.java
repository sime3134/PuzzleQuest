package map;

import IO.Persistable;
import content.ContentManager;
import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import entity.GameObject;
import entity.Scenery;
import entity.SelectionCircle;
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
    private List<GameObject> gameObjects;

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

    public Vector2D getRandomAvailablePositionOnMap() {
        double x = Math.random() * tiles.length * Settings.getSpriteSize();
        double y = Math.random() * tiles[0].length * Settings.getSpriteSize();
        int gridX = (int) (x / Settings.getSpriteSize());
        int gridY = (int) (y / Settings.getSpriteSize());

        if(!tileIsAvailable(gridX, gridY)) {
            return getRandomAvailablePositionOnMap();
        }

        return new Vector2D(x, y);
    }

    public List<Scenery> getSceneryList() {
        return sceneryList;
    }

    public void setSceneryList(List<Scenery> sceneryList) {
        this.sceneryList = sceneryList;
    }

    //endregion

    public GameMap(List<GameObject> gameObjects){
        sceneryList = new ArrayList<>();
        this.gameObjects = gameObjects;
    }

    public GameMap(int mapWidth, int mapHeight, ContentManager content, List<GameObject> gameObjects) {
        tiles = new Tile[mapWidth][mapHeight];
        sceneryList = new ArrayList<>();
        this.gameObjects = gameObjects;
        initializeTiles(content);
    }

    private void initializeTiles(ContentManager content) {
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles[0].length; y++){
                tiles[x][y] = new Tile(content, 501, "terrain");
            }
        }
    }

    public List<CollisionBox> getCollidingUnwalkableTileBoxes(CollisionBox collisionBox){
        int gridX = (int) (collisionBox.getBounds().getX() / Settings.getSpriteSize());
        int gridY = (int) (collisionBox.getBounds().getY() / Settings.getSpriteSize());

        List<CollisionBox> collidingUnwalkableTileBoxes = new ArrayList<>();

        for(int x = gridX - 1; x < gridX + 2; x++){
            for(int y = gridY - 1; y < gridY + 2; y++){
                if(isWithinBounds(x, y) && getTile(x, y).getCollisionBoxType() > 0){
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
        if(getTile(x, y).getCollisionBoxType() == 1){
            return new CollisionBox(new Rectangle(
                    x * Settings.getSpriteSize(),
                    y * Settings.getSpriteSize(),
                    Settings.getSpriteSize(),
                    Settings.getSpriteSize() / 3
            ));
        }else{
            return new CollisionBox(new Rectangle(
                    x * Settings.getSpriteSize(),
                    y * Settings.getSpriteSize(),
                    Settings.getSpriteSize(),
                    Settings.getSpriteSize()
            ));
        }
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

                if(Settings.getRenderTileWalkability().getValue()){
                    Color overlayColor = new Color(255, 0, 0, 85);
                    g.setColor(overlayColor);
                    if(tiles[x][y].getCollisionBoxType() == 2) {
                        g.fillRect(drawPositionX, drawPositionY, Settings.getSpriteSize(), Settings.getSpriteSize());
                    }else if(tiles[x][y].getCollisionBoxType() == 1){
                        g.fillRect(drawPositionX, drawPositionY, Settings.getSpriteSize(), Settings.getSpriteSize() / 3);
                    }
                }

                if(Settings.isPathable().getValue()){

                    if(!tileIsAvailable(x, y)) {
                        Color overlayColor = new Color(255, 0, 0, 85);
                        g.setColor(overlayColor);
                    }else{
                        Color overlayColor = new Color(0, 255, 0, 85);
                        g.setColor(overlayColor);
                    }

                    g.fillRect(drawPositionX, drawPositionY, Settings.getSpriteSize(), Settings.getSpriteSize());
                }
            }
        }

        if(Settings.getRenderGrid().getValue()) {
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

    public boolean tileIsAvailable(int x, int y) {
        return tiles[x][y].getCollisionBoxType() == 0 && !tileHasUnwalkableScenery(x, y) && !tileHasUnwalkableEntity(x, y);
    }

    public boolean tileHasUnwalkableScenery(int gridX, int gridY){
        CollisionBox gridCollisionBox = getGridCollisionBox(gridX, gridY);
        return sceneryList.stream()
                .filter(scenery -> !scenery.isWalkable())
                .anyMatch(scenery -> scenery.getExtendedCollisionBox().collidingWith(gridCollisionBox));
    }

    public boolean tileHasUnwalkableEntity(int gridX, int gridY){
        CollisionBox gridCollisionBox = getGridCollisionBox(gridX, gridY);
        return gameObjects.stream()
                .filter(gameObject -> !(gameObject instanceof SelectionCircle))
                .anyMatch(gameObject -> gameObject.getCollisionBox().collidingWith(gridCollisionBox));
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
