package input.mouse.action;

import core.Vector2D;
import input.Input;
import main.Game;
import map.Tile;
import settings.Settings;
import ui.UIImage;

import java.awt.*;

/**
 * Tool to toggle if tiles should be walkable or not.
 */
public class TileWalkableToggle extends MouseAction{

    @Override
    public void onClick(Game game) {
        Input input = Input.getInstance();

        Vector2D mousePosition = input.getMousePosition().getCopy();
        mousePosition.add(game.getCamera().getPosition());

        int gridX = mousePosition.intX() / Settings.getTileSize();
        int gridY = mousePosition.intY() / Settings.getTileSize();

        if(game.getCurrentMap().isWithinBounds(gridX, gridY)){
            Tile tile = game.getCurrentMap().getTile(gridX, gridY);
            if(tile.getCollisionBoxType() == 0){
                tile.setCollisionBoxType(1);
            }else if(tile.getCollisionBoxType() == 1){
                tile.setCollisionBoxType(2);
            }else{
                tile.setCollisionBoxType(0);
            }
        }
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public void update(Game game) {
        Settings.getRenderTileWalkable().set(true);
    }

    @Override
    public void draw(Game game, Graphics g) {
    }

    @Override
    public UIImage getSprite() {
        return null;
    }

    @Override
    public void cleanUp() {
        Settings.getRenderTileWalkable().set(false);
    }
}
