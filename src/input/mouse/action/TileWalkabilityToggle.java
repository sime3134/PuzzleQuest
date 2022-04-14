package input.mouse.action;

import core.Vector2D;
import input.Input;
import main.Game;
import main.state.State;
import map.Tile;
import settings.Settings;
import ui.UIImage;

import java.awt.*;

public class TileWalkabilityToggle extends MouseAction{

    @Override
    public void onClick(Game game) {
        Input input = Input.getInstance();
        State state = game.getState();

        Vector2D mousePosition = input.getMousePosition().getCopy();
        mousePosition.add(state.getCamera().getPosition());

        int gridX = mousePosition.intX() / Settings.getSpriteSize();
        int gridY = mousePosition.intY() / Settings.getSpriteSize();

        if(state.getCurrentMap().isWithinBounds(gridX, gridY)){
            Tile tile = state.getCurrentMap().getTile(gridX, gridY);
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
    public void update(State state) {
        Settings.getRenderTileWalkability().setValue(true);
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public UIImage getSprite() {
        return null;
    }

    @Override
    public void cleanUp() {
        Settings.getRenderTileWalkability().setValue(false);
    }
}
