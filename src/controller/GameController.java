package controller;

import input.Input;
import settings.GameSettings;

import java.awt.event.KeyEvent;

/**
 * Controller for controlling input outside the actual game. Ex: turning debug mode on or off.
 */
public class GameController {

    private final GameSettings settings = GameSettings.getInstance();
    private final Input input;

    public GameController(){
        this.input = Input.getInstance();
    }

    public void update(){
        if(input.isKeyPressed(KeyEvent.VK_F2)) {
            settings.toggleDebugMode();
        }
        if(input.isKeyPressed(KeyEvent.VK_F6)) {
            settings.decreaseGameSpeedMultiplier();
        }
        if(input.isKeyPressed(KeyEvent.VK_F7)) {
            settings.resetGameSpeedMultiplier();
        }
        if(input.isKeyPressed(KeyEvent.VK_F8)) {
            settings.increaseGameSpeedMultiplier();
        }
    }
}
