package ui.clickable;

import core.Vector2D;
import input.Input;
import input.mouse.MouseConsumer;
import main.Game;
import ui.UIComponent;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements clickable UI components.
 */
public abstract class UIClickable extends UIComponent implements MouseConsumer {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(Game game) {
        Vector2D mousePosition = Input.getInstance().getMousePosition().getCopy();

        hasFocus = getBounds().contains(mousePosition.intX(), mousePosition.intY());
        isPressed = hasFocus && Input.getInstance().isLeftMousePressed();

        if(hasFocus){
            game.getCurrentState().getMouseHandler().setCurrentConsumer(this);
        }
    }

    @Override
    public abstract void draw(Graphics g);

    protected Rectangle getBounds(){
        return new Rectangle(
                absolutePosition.intX(),
                absolutePosition.intY(),
                width,
                height);
    }
}
