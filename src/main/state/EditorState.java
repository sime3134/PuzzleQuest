package main.state;

import editor.UISceneryMenu;
import editor.UITileMenu;
import input.mouse.action.CameraMovement;
import input.mouse.action.ClearAction;
import input.mouse.action.SceneryTool;
import main.Game;
import settings.Settings;
import ui.Alignment;
import ui.HorizontalContainer;
import ui.UITabContainer;
import ui.clickable.UIButton;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    public EditorState(){
        super();
        createNewMap(32, 32);
        setupMouseButtons();
        Settings.setDebugMode(true);
    }

    private void setupMouseButtons() {
        mouseHandler.setWheelButtonAction(new CameraMovement());
        mouseHandler.switchLeftButtonAction(new SceneryTool());
        mouseHandler.setRightButtonAction(new ClearAction());
    }

    @Override
    protected void setupUI() {
        super.setupUI();
        UITabContainer toolsContainer = new UITabContainer();
        toolsContainer.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));
        toolsContainer.addTab("Scenery", new UISceneryMenu(content));
        toolsContainer.addTab("Tiles", new UITileMenu(content));
        uiContainers.add(toolsContainer);

        UIButton mainMenuButton = new UIButton("main menu", game -> game.goToMainMenu());
        UIButton saveButton = new UIButton("save", game -> game.getCurrentState().saveMap());
        UIButton loadButton = new UIButton("load", game -> game.getCurrentState().loadMap());
        UIButton newButton = new UIButton("new", game -> game.getCurrentState().createNewMap(64, 64));
        HorizontalContainer buttonMenu = new HorizontalContainer(mainMenuButton, saveButton, loadButton, newButton);
        mainMenuButton.setWidth(180);
        loadButton.setWidth(180);
        saveButton.setWidth(180);
        newButton.setWidth(180);
        uiContainers.add(buttonMenu);
    }

    @Override
    public void escapeButtonPressed(Game game) {

    }
}
