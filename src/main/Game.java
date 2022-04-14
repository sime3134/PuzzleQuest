package main;

import controller.GameController;
import display.Debug;
import main.state.EditorState;
import main.state.GameState;
import main.state.MainMenuState;
import main.state.State;
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
    protected State gameState;
    protected State editorState;

    public State getGameState() {
        return gameState;
    }

    public State getEditorState() {
        return editorState;
    }

    public Game(){
        currentState = new MainMenuState();
        gameController = new GameController();
        gameState = new GameState();
        editorState = new EditorState();
        debug = new Debug(currentState);
    }

    public void update(){
        gameController.update();
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

    public void setCurrentState(State newState) {
        this.currentState = newState;
        Settings.reset();
    }
}
