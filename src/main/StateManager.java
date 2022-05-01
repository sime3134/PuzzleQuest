package main;

import main.state.*;

import java.awt.*;

public class StateManager {
    private State currentState;
    private State lastState;

    private MainMenuState mainMenuState;
    private GameState gameState;
    private EditorState editorState;
    private SettingsMenuState settingsState;

    private PauseMenuState pauseMenuState;

    private SetupNameState setupNameState;

    public StateManager(Game game) {
        mainMenuState = new MainMenuState(game);
        pauseMenuState = new PauseMenuState(game);
        settingsState = new SettingsMenuState(game);
        currentState = new MainMenuState(game);
        editorState = new EditorState(game);
        setupNameState = new SetupNameState(game);
        gameState = new GameState(game);
    }

    public GameState getGameState() {
        return gameState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void update(Game game) {
        currentState.update(game);
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

    public void goToLastState() {
        currentState = lastState;
    }


    public void goToGameState() {
        lastState = currentState;
        this.currentState = gameState;
    }

    public void goToMainMenuState() {
        lastState = currentState;
        this.currentState = mainMenuState;
    }

    public void goToPauseState() {
        lastState = currentState;
        this.currentState = pauseMenuState;
    }

    public void goToSettingsState() {
        lastState = currentState;
        this.currentState = settingsState;
    }

    public void newGameState(Game game) {
        lastState = currentState;

        if(gameState == null){
            gameState = new GameState(game);
        }

        gameState.initialize(game);

        currentState = gameState;
    }

    public void goToSetupNameState() {
        lastState = currentState;
        this.currentState = setupNameState;
    }

    public void goToEditorState() {
        lastState = currentState;
        currentState = editorState;
    }

    public void initialize(Game game) {
        currentState.initialize(game);
    }
}
