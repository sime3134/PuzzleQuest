package map;

import IO.Persistable;
import content.ContentManager;

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
                (tileIndex / (tileSheet.getWidth(null) / 48)) * 48,
                (tileIndex % (tileSheet.getWidth(null) / 48)) * 48,
                48,
                48
        );

        //sprite = sprite.getScaledInstance(sprite.getWidth(null) * 2, sprite.getHeight(null) * 2, 0);
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(DELIMITER);
        stringBuilder.append(tileSheetName);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(tileIndex);
        stringBuilder.append(DELIMITER);
        stringBuilder.append(collisionBoxType);
        return stringBuilder.toString();
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
        return tileSheetName.equals("dirt") ? 5 : 150;
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
