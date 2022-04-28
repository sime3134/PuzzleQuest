package main.state;

import editor.UISettingsContainer;
import input.mouse.MouseHandler;
import main.Game;
import settings.Settings;
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
    protected static MouseHandler mouseHandler = new MouseHandler();
    private UIContainer debugSettingsContainer;

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
        debugSettingsContainer = new UISettingsContainer(game.getCurrentMap(), game.getContent());
    }

    public void update(Game game) {
        uiContainers.forEach(uiContainer -> uiContainer.update(game));

        mouseHandler.update(game);

        if(Settings.isDebugMode()){
            debugSettingsContainer.update(game);
        }
    }

    public void draw(Graphics g) {
        mouseHandler.draw(g);
        uiContainers.forEach(uiContainer -> uiContainer.draw(g));

        if(Settings.isDebugMode()){
            debugSettingsContainer.draw(g);
        }
    }

    public abstract void escapeButtonPressed(Game game);
}
