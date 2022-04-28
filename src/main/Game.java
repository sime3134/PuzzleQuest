package main;

import audio.AudioPlayer;
import content.ContentManager;
import controller.GameController;
import core.Time;
import display.Debug;
import display.GameFrame;
import main.state.*;
import settings.Settings;

import java.awt.*;

/**
 * @author Simon Jern, Johan Salomonsson
 * Main class for the game controlling the current state of the game.
 */
public class Game {

    ContentManager content;

    GameFrame gameFrame;

    private final Debug debug;
    GameController gameController;

    protected AudioPlayer audioPlayer;


    private Time time;

    private State currentState;
    private State lastState;
    protected GameState gameState;
    protected EditorState editorState;

    public State getCurrentState() {
        return currentState;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ContentManager getContent() {
        return content;
    }

    public Game(){
        content = new ContentManager();
        time = new Time();
        content.loadContent();
        currentState = new MainMenuState(content);
        lastState = currentState;
        gameController = new GameController();
        debug = new Debug(currentState);
        audioPlayer = new AudioPlayer();
        audioPlayer.playMusic("menu.wav");
        gameFrame = new GameFrame(this);
    }

    public void update() {
        gameController.update(this);
        currentState.update(this);
        time.update();
        debug.update(this);
        audioPlayer.update();
    }

    public void draw(Graphics g) {
        currentState.draw(g);
        debug.draw(currentState, g);
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
        this.currentState = new MainMenuState(content);
        Settings.reset();
        audioPlayer.playMusic("menu.wav");
    }

    public void pauseGame() {
        lastState = currentState;
        this.currentState = new PauseMenuState(content, gameState);
        this.currentState.getCamera().setPosition(gameState.getCamera().getPosition());
    }

    public void goToSettingsMenu() {
        lastState = currentState;
        this.currentState = new SettingsMenuState(content, gameState);
    }

    public void startNewGame() {
        lastState = currentState;
        gameState = new GameState(content);
        currentState = gameState;
        audioPlayer.playMusic("suburbs.wav");
    }

    public void enterUsername() {
        lastState = currentState;
        this.currentState = new SetupNameState(content);
    }

    public void goToWorldEditor() {
        lastState = currentState;
        if(editorState == null) {
            editorState = new EditorState(content);
        }else{
            Settings.toggleDebugMode();
        }

        currentState = editorState;
    }

    public void goToLastState() {
        currentState = lastState;
    }

    public void toggleAudio() {
        if(Settings.getAudioOn().getValue()){
            audioPlayer.playLastMusic();
        }else{
            audioPlayer.clear();
        }
    }

    public void setFullScreen() {
        gameFrame.toggleFullScreen();
    }

    public void showDialog(String text) {
        System.out.println(text);
    }

    public Time getTime() {
        return time;
    }
}
