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

    private final ControlsState controlsState;

    private final PauseMenuState pauseMenuState;

    private final QuestViewState questViewState;

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


    public EditorState getEditorState() {
        return editorState;
    }

    public QuestViewState getQuestViewState() {
        return questViewState;
    }

    public StateManager(Game game) {
        mainMenuState = new MainMenuState();
        pauseMenuState = new PauseMenuState();
        questViewState = new QuestViewState();
        settingsState = new SettingsMenuState();
        currentState = new MainMenuState();
        editorState = new EditorState(game.getContent());
        setupNameState = new SetupNameState();
        gameState = new GameState(game);
        controlsState = new ControlsState();
    }

    public void update(Game game) {
        currentState.update(game);
    }

    public void draw(Game game, Graphics g) {
        currentState.draw(game, g);
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
    }

    public void goToQuestViewState() {
        lastState = currentState;
        this.currentState = questViewState;
        currentState.setupUI();
        questViewState.updateQuestMenu(gameState);
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

    public void goToControlsMenu() {
        lastState = currentState;
        currentState = controlsState;
        currentState.setupUI();
    }
}
