package main.state;

import core.Vector2D;
import editor.UISceneryMenu;
import editor.UISettingsContainer;
import editor.UITileMenu;
import input.mouse.action.CameraMovement;
import input.mouse.action.ClearAction;
import input.mouse.action.SceneryTool;
import map.GameMap;
import ui.Alignment;
import ui.UIButtonMenu;
import ui.UIContainer;
import ui.UITabContainer;
import ui.clickable.UIButton;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    public EditorState(){
        currentMap = new GameMap(32, 32, content, getGameObjects());

        setupMouseButtons();

        setupUI();
    }

    private void setupMouseButtons() {
        mouseHandler.setWheelButtonAction(new CameraMovement());
        mouseHandler.switchLeftButtonAction(new SceneryTool());
        mouseHandler.setRightButtonAction(new ClearAction());
    }

    private void setupUI() {
        UIContainer container = new UISettingsContainer(currentMap, content);
        uiContainers.add(container);

        UITabContainer toolsContainer = new UITabContainer();
        toolsContainer.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));
        toolsContainer.addTab("Scenery", new UISceneryMenu(content));
        toolsContainer.addTab("Tiles", new UITileMenu(content));
        uiContainers.add(toolsContainer);


        UIButton mainMenuButton = new UIButton("main menu", game -> game.setState(new MainMenuState()));
        UIButton saveButton = new UIButton("save", game -> game.getState().saveMap());
        UIButton loadButton = new UIButton("load", game -> game.getState().loadMap());
        UIButtonMenu buttonMenu = new UIButtonMenu(mainMenuButton, saveButton, loadButton);
        uiContainers.add(buttonMenu);
    }
}
