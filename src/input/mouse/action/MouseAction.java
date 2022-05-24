package input.mouse.action;

import input.mouse.MouseConsumer;
import main.Game;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements a mouse action. Can take control over mouse input since it is implementing the interface MouseConsumer.
 */
public abstract class MouseAction implements MouseConsumer {

    public abstract void update(Game game);
    public abstract void draw(Game game, Graphics g);
    public abstract UIImage getSprite();
    public abstract void cleanUp();
}
