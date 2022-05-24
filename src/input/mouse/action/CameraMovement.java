package input.mouse.action;

import core.Vector2D;
import input.Input;
import main.Game;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Tool for moving the camera through the minimap.
 */
public class CameraMovement extends MouseAction {
    private Vector2D dragPosition;

    @Override
    public void onClick(Game game) {

    }

    @Override
    public void onDrag(Game game) {
        Input input = Input.getInstance();

        if (dragPosition != null) {
            dragPosition.subtract(input.getMousePosition());
            Vector2D cameraPosition = game.getCamera().getPosition().getCopy();
            cameraPosition.add(dragPosition);
            game.getCamera().setPosition(cameraPosition);
        }
        dragPosition = input.getMousePosition().getCopy();
    }

    @Override
    public void onRelease(Game game) {
        dragPosition = null;
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
