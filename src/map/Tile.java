package map;

import java.awt.*;

public class Tile {

    private Image sprite;

    public Tile(Image sprite){
        this.sprite = sprite;
    }

    public Image getSprite(){
        return sprite;
    }
}
