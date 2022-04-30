package map;

import IO.Persistable;
import content.ContentManager;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * A tile represents a small part of the map and has graphic to display.
 */
public class Tile implements Persistable {

    protected Image tileSheet;
    protected Image sprite;
    protected int tileIndex;
    protected String tileSheetName;
    protected int collisionBoxType;

    public Image getSprite(){
        return sprite;
    }

    public Tile getCopy() {
        return new Tile(tileSheet, tileIndex, tileSheetName);
    }

    public Image getTileSheet() {
        return tileSheet;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        generateSprite();
    }

    public int getCollisionBoxType() {
        return collisionBoxType;
    }

    public void setCollisionBoxType(int collisionBoxType) {
        this.collisionBoxType = collisionBoxType;
    }

    public Tile() {
        collisionBoxType = 0;
    }

    /**
     * Constructor used to display tile sheet in tile editor.
     */
    public Tile(ContentManager content, String tileSheetName){
        this.tileSheet = content.getImage(tileSheetName);
        this.tileSheetName = tileSheetName;
        this.collisionBoxType = 0;
        generateSprite();
    }

    public Tile(ContentManager content, int tileIndex, String tileSheetName){
        this.tileSheet = content.getImage(tileSheetName);
        this.tileIndex = tileIndex;
        this.tileSheetName = tileSheetName;
        this.collisionBoxType = 0;
        generateSprite();
    }

    public Tile(Image tileSheet, int tileIndex, String tileSheetName){
        this.tileSheet = tileSheet;
        this.tileIndex = tileIndex;
        this.tileSheetName = tileSheetName;
        this.collisionBoxType = 0;
        generateSprite();
    }

    /**
     * Cuts out a part of a tile sheet to use as this tiles graphic.
     */
    protected void generateSprite() {
        sprite = ((BufferedImage)tileSheet).getSubimage(
                (tileIndex / (tileSheet.getWidth(null) / Settings.getTileSize())) * Settings.getTileSize(),
                (tileIndex % (tileSheet.getWidth(null) / Settings.getTileSize())) * Settings.getTileSize(),
                Settings.getTileSize(),
                Settings.getTileSize()
        );
    }

    public void reloadGraphics(ContentManager content){
        tileSheet = content.getImage(tileSheetName);
        generateSprite();
    }

    /**
     * Writes a tile to file.
     */
    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(DELIMITER);
        sb.append(tileSheetName);
        sb.append(DELIMITER);
        sb.append(tileIndex);
        sb.append(DELIMITER);
        sb.append(collisionBoxType);
        return sb.toString();
    }

    /**
     * Loads a tiles data back from a file and sets the read variables.
     */
    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        tileSheetName = tokens[1];
        tileIndex = Integer.parseInt(tokens[2]);
        collisionBoxType = Integer.parseInt(tokens[3]);
    }

    public int getMoveCost() {
        return tileSheetName.equals("paths")
                || tileSheetName.equals("borders")
                || tileSheetName.equals("grass1") && tileIsGrassBorder()
                || tileSheetName.equals("grass2") && tileIsGrassBorder() ? 5 : 150;
    }

    private boolean tileIsGrassBorder() {
        boolean notGrassBorder = tileIndex == 40
                || tileIndex == 41 || tileIndex == 42
                || tileIndex == 48 || tileIndex == 49
                || tileIndex == 50 || tileIndex == 56
                || tileIndex == 57 || tileIndex == 58
                || tileIndex == 59 || tileIndex == 60;

        return !notGrassBorder;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public String getTileSheetName() {
        return tileSheetName;
    }

    public void draw(Graphics g, int posX, int posY) {
        g.drawImage(getSprite(), posX, posY, null);
    }

    public void update() {
    }
}
