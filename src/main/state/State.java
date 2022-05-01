package main.state;

import input.mouse.MouseHandler;
import main.Game;
import ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Base class for the several states in the game. Ex: in-game, pause or menu states.
 */
public abstract class State {

    protected List<UIContainer> uiContainers;
    protected MouseHandler mouseHandler = new MouseHandler();

    //region Getters and Setters (click to view)

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    //endregion

    protected State(Game game) {
        uiContainers = new ArrayList<>();
        setupUI(game);
    }

    protected void setupUI(Game game) {
    }

    public void update(Game game) {
        uiContainers.forEach(uiContainer -> uiContainer.update(game));

        mouseHandler.update(game);
    }

    public void draw(Graphics g) {
        mouseHandler.draw(g);

        uiContainers.forEach(uiContainer -> uiContainer.draw(g));
    }

    public abstract void escapeButtonPressed(Game game);
}
