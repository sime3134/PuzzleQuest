package input.mouse.action;

import main.Game;
import main.state.State;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Clears any active tool.
 */
public class ClearAction extends MouseAction{
    @Override
    public void onClick(Game game) {
        game.getCurrentState().getMouseHandler().switchLeftButtonAction(new SceneryTool());
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public void update(State state) {

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

    }
}
