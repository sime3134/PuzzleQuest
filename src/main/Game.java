package main;

import audio.AudioPlayer;
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

    protected AudioPlayer audioPlayer;

    private State currentState;
    private State lastState;
    protected GameState gameState;
    protected EditorState editorState;

    public Game(){
        currentState = new MainMenuState();
        lastState = currentState;
        gameController = new GameController();
        debug = new Debug(currentState);
        audioPlayer = new AudioPlayer();
        audioPlayer.playMusic("menu.wav");
    }

    public void update(){
        gameController.update(this);
        currentState.update(this);
        debug.update(currentState);
        audioPlayer.update();
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
        audioPlayer.playMusic("menu.wav");
    }


    public void pauseGame() {
        lastState = currentState;
        this.currentState = new PauseMenuState(gameState);
        this.currentState.getCamera().setPosition(gameState.getCamera().getPosition());
    }

    public void goToSettingsMenu() {
        lastState = currentState;
        this.currentState = new SettingsMenuState(gameState);
    }

    public void startNewGame() {
        lastState = currentState;
        gameState = new GameState();
        currentState = gameState;
        audioPlayer.playMusic("suburbs.wav");
    }

    public void enterUsername() {
        lastState = currentState;
        this.currentState = new SetupNameState();
    }

    public void goToWorldEditor() {
        lastState = currentState;
        if(editorState == null) {
            editorState = new EditorState();
        }else{
            Settings.toggleDebugMode();
        }

        currentState = editorState;
    }

    public void goToLastState() {
        currentState = lastState;
    }

    public void toggleAudio() {
        if(Settings.getAudioMode().getValue()){
            audioPlayer.playLastMusic();
        }else{
            audioPlayer.clear();
        }
    }
}
