package controller;

import input.Input;
import main.Game;
import main.state.GameState;
import main.state.QuestViewState;
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
            game.getCurrentState().escapeButtonPressed(game);
        }

        if(input.isKeyPressed(KeyEvent.VK_Q)) {
            if(game.getCurrentState() instanceof GameState gameState){
                gameState.QButtonPressed(game);
            }else if(game.getCurrentState() instanceof QuestViewState questState){
                questState.QButtonPressed(game);
            }
        }
    }
}
