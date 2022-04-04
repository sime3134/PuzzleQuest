package input.mouse.action;

import input.mouse.MouseConsumer;
import main.state.State;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements a mouse action. Can take control over mouse input since it is implementing the interface MouseConsumer.
 */
public abstract class MouseAction implements MouseConsumer {

    public abstract void update(State state);
    public abstract void draw(Graphics g);
    public abstract UIImage getSprite();
}
