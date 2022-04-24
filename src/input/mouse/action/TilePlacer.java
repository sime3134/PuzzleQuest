package input.mouse.action;

import core.Vector2D;
import input.Input;
import main.Game;
import main.state.State;
import map.AnimatedTile;
import map.Tile;
import settings.Settings;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Tool to place tiles on the game map.
 */
public class TilePlacer extends MouseAction{

    private final Tile tile;
    private final UIImage previewImage;
    private int currentGridX;
    private int currentGridY;

    public TilePlacer(Tile tile) {
        this.tile = tile;
        previewImage = new UIImage(tile.getSprite());
    }

    @Override
    public void onClick(Game game) {
        if(game.getCurrentState().getCurrentMap().isWithinBounds(currentGridX, currentGridY)){
            Tile newTile = tile.getCopy();
            if(newTile instanceof AnimatedTile animatedTile){
                animatedTile.setAnimationManager(((AnimatedTile)tile).getAnimationManager().getCopy(animatedTile.getTileSheetName()));
                game.getCurrentState().getCurrentMap().setTile(currentGridX, currentGridY, animatedTile);
            }else{
                game.getCurrentState().getCurrentMap().setTile(currentGridX, currentGridY, newTile);
            }
        }
        System.out.println("TilePlacer class: Placed tile: " + tile.getTileSheetName() + "    " + tile.getTileIndex());
    }

    @Override
    public void onDrag(Game game) {
        if(game.getCurrentState().getCurrentMap().isWithinBounds(currentGridX, currentGridY)){
            Tile newTile = tile.getCopy();
            if(newTile instanceof AnimatedTile animatedTile){
                animatedTile.setAnimationManager(((AnimatedTile)tile).getAnimationManager().getCopy(animatedTile.getTileSheetName()));
                game.getCurrentState().getCurrentMap().setTile(currentGridX, currentGridY, animatedTile);
            }else{
                game.getCurrentState().getCurrentMap().setTile(currentGridX, currentGridY, newTile);
            }
        }
    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public void update(State state) {
        Vector2D mousePosition = Input.getInstance().getMousePosition().getCopy();
        mousePosition.add(state.getCamera().getPosition());

        currentGridX = mousePosition.intX() / Settings.getSpriteSize();
        currentGridY = mousePosition.intY() / Settings.getSpriteSize();

        mousePosition.subtract(new Vector2D(
                mousePosition.intX() % Settings.getSpriteSize(),
                mousePosition.intY() % Settings.getSpriteSize()));
        mousePosition.subtract(state.getCamera().getPosition());

        previewImage.setAbsolutePosition(mousePosition);
    }

    @Override
    public void draw(Graphics g) {
        previewImage.draw(g);
    }

    @Override
    public UIImage getSprite() {
        return previewImage;
    }

    @Override
    public void cleanUp() {

    }
}
