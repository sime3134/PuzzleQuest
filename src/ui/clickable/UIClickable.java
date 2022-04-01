package ui.clickable;

import core.Vector2D;
import input.Input;
import main.state.State;
import ui.UIComponent;

import java.awt.*;

public abstract class UIClickable extends UIComponent {

    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) {
        Vector2D mousePosition = Input.getInstance().getMousePosition();

        hasFocus = getBounds().contains(mousePosition.intX(), mousePosition.intY());
        isPressed = hasFocus && Input.getInstance().isMousePressed();

        if(hasFocus && Input.getInstance().isMouseClicked()){
            onClick();
        }
    }

    protected abstract void onClick();

    @Override
    public abstract void draw(Graphics g);

    private Rectangle getBounds(){
        return new Rectangle(
                absolutePosition.intX(),
                absolutePosition.intY(),
                width,
                height);
    }
}
