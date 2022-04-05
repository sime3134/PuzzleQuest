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
    private State state;
    private final Debug debug;
    GameController gameController;

    public Game(){
        state = new MainMenuState();
        debug = new Debug(state);
        gameController = new GameController();
    }

    public void update(){
        gameController.update();
        state.update(this);
        debug.update(state);
    }

    public void draw(Graphics g){
        state.draw(g);
        if(Settings.isDebugMode()) {
            debug.draw(state, g);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
