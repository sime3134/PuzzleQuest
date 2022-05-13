package main;

import content.ContentManager;
import main.state.*;

import java.awt.*;

public class StateManager {
    private State currentState;
    private State lastState;

    private final MainMenuState mainMenuState;
    private final GameState gameState;
    private final EditorState editorState;
    private final SettingsMenuState settingsState;

    private final PauseMenuState pauseMenuState;

    private final SetupNameState setupNameState;

    public GameState getGameState() {
        return gameState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public PauseMenuState getPauseMenuState() {
        return pauseMenuState;
    }

    public StateManager(Game game) {
        mainMenuState = new MainMenuState();
        pauseMenuState = new PauseMenuState();
        settingsState = new SettingsMenuState();
        currentState = new MainMenuState();
        editorState = new EditorState(game.getContent());
        setupNameState = new SetupNameState();
        gameState = new GameState(game);
    }

    public void update(Game game) {
        currentState.update(game);
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

    public void goToLastState() {
        currentState = lastState;
        currentState.setupUI();
    }

    public void goToGameState() {
        lastState = currentState;
        currentState = gameState;
        currentState.setupUI();
    }

    public void goToMainMenuState() {
        lastState = currentState;
        this.currentState = mainMenuState;
        this.currentState.setupUI();
    }

    public void goToPauseState() {
        lastState = currentState;
        this.currentState = pauseMenuState;
        currentState.setupUI();
        pauseMenuState.updateQuestMenu(gameState);
    }

    public void goToSettingsState() {
        lastState = currentState;
        this.currentState = settingsState;
        currentState.setupUI();
    }

    public void goToSetupNameState() {
        lastState = currentState;
        currentState = setupNameState;
        currentState.setupUI();
    }

    public void goToEditorState(ContentManager content) {
        lastState = currentState;
        currentState = editorState;
        currentState.setupUI();
        editorState.setupToolsContainers(content);
    }

    public EditorState getEditorState() {
        return editorState;
    }
}
