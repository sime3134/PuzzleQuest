package map;

import IO.Persistable;
import content.ContentManager;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Simon Jern
 * A tile represents a small part of the map and has graphic to display.
 */
public class Tile implements Persistable {

    private Image tileSheet;
    private Image sprite;
    private int tileIndex;
    private String tileSheetName;

    public Image getSprite(){
        return sprite;
    }

    public Tile getCopy() {
        return new Tile(tileSheet, tileIndex, tileSheetName);
    }

    public Image getTileSheet() {
        return tileSheet;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public String getTileSheetName() {
        return tileSheetName;
    }

    public void setTileIndex(int tileIndex) {
        this.tileIndex = tileIndex;
        generateSprite();
    }

    public Tile() {}

    /**
     * Constructor used to display tile sheet in tile editor.
     */
    public Tile(ContentManager content, String tileSheetName){
        this.tileSheet = content.getTileSheet(tileSheetName);
        this.tileSheetName = tileSheetName;
        generateSprite();
    }

    public Tile(ContentManager content, int tileIndex, String tileSheetName){
        this.tileSheet = content.getTileSheet(tileSheetName);
        this.tileIndex = tileIndex;
        this.tileSheetName = tileSheetName;
        generateSprite();
    }

    public Tile(Image tileSheet, int tileIndex, String tileSheetName){
        this.tileSheet = tileSheet;
        this.tileIndex = tileIndex;
        this.tileSheetName = tileSheetName;
        generateSprite();
    }

    /**
     * Cuts out a part of a tile sheet to use as this tiles graphic.
     */
    private void generateSprite() {
        sprite = ((BufferedImage)tileSheet).getSubimage(
                (tileIndex / (tileSheet.getWidth(null) / Settings.getSpriteSize())) * Settings.getSpriteSize(),
                (tileIndex % (tileSheet.getWidth(null) / Settings.getSpriteSize())) * Settings.getSpriteSize(),
                Settings.getSpriteSize(),
                Settings.getSpriteSize()
        );
    }

    public void reloadGraphics(ContentManager content){
        tileSheet = content.getTileSheet(tileSheetName);
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
    }
}
