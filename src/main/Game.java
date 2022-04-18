package main;

import controller.GameController;
import display.Debug;
import main.state.*;
import settings.Settings;

import java.awt.*;

/**
 * @author Simon Jern
 * Main class for the game controlling the current state of the game.
 */
public class Game {
    private final Debug debug;
    GameController gameController;

    private State currentState;
    private State lastState;
    protected State gameState;
    protected State editorState;

    public Game(){
        currentState = new MainMenuState();
        lastState = currentState;
        gameController = new GameController();
        debug = new Debug(currentState);
    }

    public void update(){
        gameController.update(this);
        currentState.update(this);
        debug.update(currentState);
    }

    public void draw(Graphics g){
        currentState.draw(g);
        debug.draw(currentState, g);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void resumeGame() {
        lastState = currentState;

        this.currentState = gameState;
    }

    public void saveGame() {
        //TODO: implement
    }

    public void loadGame() {
        //TODO: implement
    }

    public void goToMainMenu() {
        lastState = currentState;
        this.currentState = new MainMenuState();
        Settings.reset();
    }


    public void pauseGame() {
        lastState = currentState;
        this.currentState = new PauseMenuState();
    }

    public void goToSettingsMenu() {
        lastState = currentState;
        this.currentState = new SettingsMenuState();
    }

    public void startNewGame() {
        lastState = currentState;
        gameState = new GameState();
        currentState = gameState;
    }

    public void enterUsername() {
        lastState = currentState;
        this.currentState = new SetupName();
    }

    public void goToWorldEditor() {
        lastState = currentState;
        if(editorState == null) {
            editorState = new EditorState();
        }

        currentState = editorState;
    }

    public void goToLastState() {
        currentState = lastState;
    }
}
