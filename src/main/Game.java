package main;

import controller.GameController;
import display.Debug;
import main.state.GameState;
import main.state.State;
import settings.GameSettings;

import java.awt.*;


public class Game {
    GameSettings settings;
    private State state;
    private final Debug debug;
    GameController gameController;

    public Game(){
        settings = GameSettings.getInstance();
        state = new GameState();
        debug = new Debug(state);
        gameController = new GameController();
    }

    public void update(){
        gameController.update();
        state.update();
        debug.update(state);
    }

    public void draw(Graphics g){
        state.draw(g);
        if(settings.isDebugMode()) {
            debug.draw(state, g);
        }
    }
}
