package controller;

import input.Input;
import main.Game;
import main.state.PauseMenuState;
import settings.Settings;

import java.awt.event.KeyEvent;

/**
 * @author Simon Jern
 * Controller for controlling input outside the actual game. Ex: turning debug mode on or off.
 */
public class GameController {

    private final Input input;

    public GameController(){
        this.input = Input.getInstance();
    }

    public void update(Game game){
        if(input.isKeyPressed(KeyEvent.VK_F2)) {
            Settings.toggleDebugMode();
        }
        if(input.isKeyPressed(KeyEvent.VK_F6)) {
            Settings.decreaseGameSpeedMultiplier();
        }
        if(input.isKeyPressed(KeyEvent.VK_F7)) {
            Settings.resetGameSpeedMultiplier();
        }
        if(input.isKeyPressed(KeyEvent.VK_F8)) {
            Settings.increaseGameSpeedMultiplier();
        }

        if(input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            if(game.getCurrentState() instanceof PauseMenuState){
                game.resumeGame();
            }else{
                game.pauseGame();
            }
        }
    }
}
