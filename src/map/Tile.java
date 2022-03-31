package map;

import java.awt.*;

/**
 * A tile represents a small part of the map and has graphic to display.
 */
public class Tile {

    private final Image sprite;

    public Tile(Image sprite){
        this.sprite = sprite;
    }

    public Image getSprite(){
        return sprite;
    }
}
