package main.state;

import input.Input;
import input.KeyInputConsumer;
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

    protected KeyInputConsumer keyInputConsumer;

    //region Getters and Setters (click to view)

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public KeyInputConsumer getKeyInputConsumer() {
        return keyInputConsumer;
    }

    public void setKeyInputConsumer(KeyInputConsumer keyInputConsumer) {
        this.keyInputConsumer = keyInputConsumer;
    }

    //endregion

    protected State() {
        uiContainers = new ArrayList<>();
        setupUI();
    }

    public void setupUI(){
        uiContainers.clear();
    }

    public void update(Game game) {
        uiContainers.forEach(uiContainer -> uiContainer.update(game));

        handleKeyInput(game);
        mouseHandler.update(game);
    }

    private void handleKeyInput(Game game) {
        if(keyInputConsumer != null){
            for(int keyCode : Input.getInstance().getTypedKeyBuffer()){
                keyInputConsumer.onKeyPressed(game, keyCode);
            }
        }
    }

    public void draw(Graphics g) {
        mouseHandler.draw(g);

        uiContainers.forEach(uiContainer -> uiContainer.draw(g));
    }

    public abstract void escapeButtonPressed(Game game);

    public void addUIContainer(UIContainer container){
        uiContainers.add(container);
    }
}
