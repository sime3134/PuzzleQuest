package input.mouse.action;

import main.Game;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Clears any active tool.
 */
public class ClearAction extends MouseAction{
    @Override
    public void onClick(Game game) {
        game.getCurrentState().getMouseHandler().switchLeftButtonAction(new GameObjectTool());
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public void update(Game game) {

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

    }
}
