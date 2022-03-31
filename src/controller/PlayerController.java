package controller;

import input.Input;

import java.awt.event.KeyEvent;

/**
 * Handles the controller for the player.
 */
public class PlayerController implements EntityController {

    private static PlayerController playerController;

    public static PlayerController getInstance() {
        if(playerController == null){
            playerController = new PlayerController();
        }
        return playerController;
    }

    private PlayerController(){

    }

    private final Input input = Input.getInstance();

    @Override
    public boolean requestedUp() {
        return input.isCurrentlyPressed(KeyEvent.VK_W);
    }

    @Override
    public boolean requestedDown() {
        return input.isCurrentlyPressed(KeyEvent.VK_S);
    }

    @Override
    public boolean requestedLeft() {
        return input.isCurrentlyPressed(KeyEvent.VK_A);
    }

    @Override
    public boolean requestedRight() {
        return input.isCurrentlyPressed(KeyEvent.VK_D);
    }
}
