package map;

import IO.Persistable;
import content.ContentManager;
import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import entity.GameObject;
import entity.NPC;
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

    public static long nextId = 0;

    private Tile[][] tiles;
    private List<Scenery> sceneryList;

    private final List<NPC> npcList;

    private String name;

    //region Getters and Setters (click to open)

    public int getWidth() {
        return tiles.length * Settings.getTileSize();
    }

    public int getHeight() {
        return tiles[0].length * Settings.getTileSize();
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
        double x = Math.random() * (tiles.length - 1) * Settings.getTileSize();
        double y = Math.random() * (tiles[0].length - 1) * Settings.getTileSize();
        int gridX = (int) (x / Settings.getTileSize());
        int gridY = (int) (y / Settings.getTileSize());

        if (!tileIsAvailable(gridX, gridY)) {
            return getRandomAvailablePositionOnMap();
        }

        return new Vector2D(gridX * Settings.getTileSize(), gridY * Settings.getTileSize());
    }

    public List<Scenery> getSceneryList() {
        return sceneryList;
    }

    public void setSceneryList(List<Scenery> sceneryList) {
        this.sceneryList = sceneryList;
    }

    public String getName() {
        return name;
    }

    //endregion

    public GameMap(String name) {
        sceneryList = new ArrayList<>();
        npcList = new ArrayList<>();
        this.name = name;
    }

    public GameMap(int mapWidth, int mapHeight, ContentManager content) {
        tiles = new Tile[mapWidth][mapHeight];
        sceneryList = new ArrayList<>();
        npcList = new ArrayList<>();
        initializeTiles(content);
    }

    private void initializeTiles(ContentManager content) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new Tile(content, 41, "grass1");
            }
        }
    }

    public List<CollisionBox> getCollidingUnwalkableTileBoxes(CollisionBox collisionBox) {
        int gridX = (int) (collisionBox.getBounds().getX() / Settings.getTileSize());
        int gridY = (int) (collisionBox.getBounds().getY() / Settings.getTileSize());

        List<CollisionBox> collidingUnwalkableTileBoxes = new ArrayList<>();

        for (int x = gridX - 1; x < gridX + 2; x++) {
            for (int y = gridY - 1; y < gridY + 2; y++) {
                if (isWithinBounds(x, y) && getTile(x, y).getCollisionBoxType() > 0) {
                    CollisionBox gridCollisionBox = getGridCollisionBox(x, y);

                    if (collisionBox.collidingWith(gridCollisionBox)) {
                        collidingUnwalkableTileBoxes.add(gridCollisionBox);
                    }
                }
            }
        }

        return collidingUnwalkableTileBoxes;
    }

    private CollisionBox getGridCollisionBox(int x, int y) {
        if (getTile(x, y).getCollisionBoxType() == 1) {
            return new CollisionBox(new Rectangle(
                    x * Settings.getTileSize(),
                    y * Settings.getTileSize(),
                    Settings.getTileSize(),
                    Settings.getTileSize() / 3
            ));
        } else {
            return new CollisionBox(new Rectangle(
                    x * Settings.getTileSize(),
                    y * Settings.getTileSize(),
                    Settings.getTileSize(),
                    Settings.getTileSize()
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
        double startPosX = Math.max(0, camera.getPosition().getX() / Settings.getTileSize()); //left border of screen
        double endPosX = Math.min(tiles.length,
                (camera.getPosition().getX() + Settings.getScreenWidth()) / Settings.getTileSize()
                        + Settings.getRenderMargin()); //right border of screen
        double startPosY = Math.max(0, camera.getPosition().getY() / Settings.getTileSize()); //top of screen
        double endPosY = Math.min(tiles[0].length,
                (camera.getPosition().getY() + Settings.getScreenHeight()) / Settings.getTileSize()
                        + Settings.getRenderMargin()); //bottom of screen

        for (int x = (int) startPosX; x < (int) endPosX; x++) {
            for (int y = (int) startPosY; y < (int) endPosY; y++) {
                int drawPositionX = x * Settings.getTileSize() - camera.getPosition().intX();
                int drawPositionY = y * Settings.getTileSize() - camera.getPosition().intY();

                tiles[x][y].draw(g, drawPositionX, drawPositionY);

                if(Settings.getRenderGrid().get()) {
                    g.drawString(x + ", " + y, drawPositionX, drawPositionY + 10);
                }

                if (Settings.getRenderTileWalkable().get()) {
                    drawWalkable(g, x, y, drawPositionX, drawPositionY);
                }

                if (Settings.isPathable().get()) {
                    drawPathable(g, x, y, drawPositionX, drawPositionY);
                }
            }
        }

        if (Settings.getRenderGrid().get()) {
            drawGrid(g, camera, (int) startPosX, (int) endPosX, (int) startPosY, (int) endPosY);
        }

    }

    private void drawGrid(Graphics g, Camera camera, int startPosX, int endPosX, int startPosY, int endPosY) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = startPosX; x < endPosX; x++) {
            g.drawLine(x * Settings.getTileSize() - camera.getPosition().intX(),
                    startPosY * Settings.getTileSize() - camera.getPosition().intY(),
                    x * Settings.getTileSize() - camera.getPosition().intX(),
                    endPosY * Settings.getTileSize() - camera.getPosition().intY()
            );
        }

        for (int y = startPosY; y < endPosY; y++) {
            g.drawLine(startPosX * Settings.getTileSize() - camera.getPosition().intX(),
                    y * Settings.getTileSize() - camera.getPosition().intY(),
                     endPosX * Settings.getTileSize() - camera.getPosition().intX(),
                    y * Settings.getTileSize() - camera.getPosition().intY()
            );
        }
    }

    private void drawWalkable(Graphics g, int x, int y, int drawPositionX, int drawPositionY) {
        Color overlayColor = new Color(255, 0, 0, 85);
        g.setColor(overlayColor);
        if (tiles[x][y].getCollisionBoxType() == 2) {
            g.fillRect(drawPositionX, drawPositionY, Settings.getTileSize(), Settings.getTileSize());
        } else if (tiles[x][y].getCollisionBoxType() == 1) {
            g.fillRect(drawPositionX, drawPositionY, Settings.getTileSize(), Settings.getTileSize() / 3);
        }
    }

    private void drawPathable(Graphics g, int x, int y, int drawPositionX, int drawPositionY) {
        if (!tileIsAvailable(x, y)) {
            Color overlayColor = new Color(255, 0, 0, 85);
            g.setColor(overlayColor);
        } else {
            Color overlayColor = new Color(0, 255, 0, 85);
            g.setColor(overlayColor);
        }

        g.fillRect(drawPositionX, drawPositionY, Settings.getTileSize(), Settings.getTileSize());
    }

    public boolean tileIsAvailable(int x, int y) {
        return !tileHasCollisionBox(x, y) && !tileHasUnwalkableScenery(x, y);
    }

    private boolean tileHasCollisionBox(int x, int y) {
        return tiles[x][y].getCollisionBoxType() == 1 || tiles[x][y].getCollisionBoxType() == 2;
    }

    public boolean tileHasUnwalkableScenery(int gridX, int gridY) {
        CollisionBox gridCollisionBox = getGridCollisionBox(gridX, gridY);
        return sceneryList.stream()
                .filter(scenery -> !scenery.isWalkable())
                .anyMatch(scenery -> scenery.getCollisionBox().collidingWith(gridCollisionBox));
        //TODO: Better with getExtendedCollisionBox()?
    }

    public boolean tileHasUnwalkableEntity(List<GameObject> gameObjects, int gridX, int gridY) {
        CollisionBox gridCollisionBox = getGridCollisionBox(gridX, gridY);
        return gameObjects.stream()
                .filter(gameObject -> !(gameObject instanceof SelectionCircle))
                .filter(gameObject -> !gameObject.isWalkable())
                .anyMatch(gameObject -> gameObject.getCollisionBox().collidingWith(gridCollisionBox));
    }

    /**
     * Used to reload the tile sheet images after loading a game map since
     * images can't be serialized.
     */
    public void reloadGraphics(ContentManager content) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                tile.reloadGraphics(content);
            }
        }

        sceneryList.forEach(scenery -> scenery.loadGraphics(content));
        npcList.forEach(npc -> npc.applyGraphics(content));
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
            for (int y = 0; y < tiles[0].length; y++) {
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

        sb.append(SECTION_DELIMETER);
        npcList.forEach(npc -> {
            sb.append(npc.serialize());
            sb.append(COLUMN_DELIMETER);
        });

        return sb.toString();
    }

    /**
     * Loads a game map from file.
     */
    @Override
    public void applySerializedData(String serializedData) {
        String[] sections = serializedData.split(SECTION_DELIMETER);
        String[] tokens = sections[0].split(DELIMITER);
        tiles = new Tile[Integer.parseInt(tokens[1])][Integer.parseInt(tokens[2])];

        String tileSection = sections[1];
        String[] columns = tileSection.split(COLUMN_DELIMETER);

        for (int x = 0; x < tiles.length; x++) {
            String[] tilesInColumn = columns[x].split(LIST_DELIMETER);

            for (int y = 0; y < tiles[0].length; y++) {
                String[] tileTokens = tilesInColumn[y].split(DELIMITER);
                Tile tile;
                if(tileTokens[0].equals("Tile")) {
                    tile = new Tile();
                }else{
                    tile = new AnimatedTile();
                }

                tile.applySerializedData(tilesInColumn[y]);

                tiles[x][y] = tile;
            }
        }

        if(sections.length > 2 && !sections[2].isEmpty()) {
            String scenerySection = sections[2];
            String[] serializedSceneries = scenerySection.split(COLUMN_DELIMETER);
            for (String serializedScenery : serializedSceneries) {
                if(!serializedScenery.isEmpty() && !serializedScenery.equals("###")) {
                    Scenery scenery = new Scenery();
                    scenery.applySerializedData(serializedScenery);
                    sceneryList.add(scenery);
                }
            }
        }

        if(sections.length > 3 && !sections[3].isEmpty()) {
            String NPCSection = sections[3];
            String[] serializedNPCs = NPCSection.split(COLUMN_DELIMETER);
            for (String serializedNPC : serializedNPCs) {
                if(!serializedNPC.isEmpty() && !serializedNPC.equals("###")) {
                    NPC npc = new NPC();
                    npc.applySerializedData(serializedNPC);
                    npcList.add(npc);
                }
            }
        }
    }

    public void update(Camera camera) {
        double startPosX = Math.max(0, camera.getPosition().getX() / Settings.getTileSize()); //left border of screen
        double endPosX = Math.min(tiles.length,
                (camera.getPosition().getX() + Settings.getScreenWidth()) / Settings.getTileSize()
                        + Settings.getRenderMargin()); //right border of screen
        double startPosY = Math.max(0, camera.getPosition().getY() / Settings.getTileSize()); //top of screen
        double endPosY = Math.min(tiles[0].length,
                (camera.getPosition().getY() + Settings.getScreenHeight()) / Settings.getTileSize()
                        + Settings.getRenderMargin()); //bottom of screen

        for (int x = (int) startPosX; x < (int) endPosX; x++) {
            for (int y = (int) startPosY; y < (int) endPosY; y++) {

                tiles[x][y].update();
            }
        }
    }

    public List<NPC> getNPCList() {
        return npcList;
    }

    public void addNPC(NPC npc) {
        npcList.add(npc);
    }

    public void addScenery(Scenery scenery){
        sceneryList.add(scenery);
    }

    public void removeNPC(NPC npc) {
        npcList.remove(npc);
    }

    public void removeScenery(Scenery scenery){
        sceneryList.remove(scenery);
    }

    public GameObject findGameObjectById(long id) {
        for(Scenery scenery : sceneryList){
            if(scenery.getId() == id){
                return scenery;
            }
        }
        for(NPC npc : npcList){
            if(npc.getId() == id){
                return npc;
            }
        }
        return null;
    }
}
