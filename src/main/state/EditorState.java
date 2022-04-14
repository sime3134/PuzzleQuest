package main.state;

import editor.UISceneryMenu;
import editor.UISettingsContainer;
import editor.UITileMenu;
import input.mouse.action.CameraMovement;
import input.mouse.action.ClearAction;
import input.mouse.action.SceneryTool;
import ui.Alignment;
import ui.HorizontalUIButtonMenu;
import ui.UIContainer;
import ui.UITabContainer;
import ui.clickable.UIButton;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    public EditorState(){
    }

    @Override
    public void prepare() {
        newMap();
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

        UIButton mainMenuButton = new UIButton("main menu", game -> game.setCurrentState(new MainMenuState()));
        UIButton saveButton = new UIButton("save", game -> game.getCurrentState().saveMap());
        UIButton loadButton = new UIButton("load", game -> game.getCurrentState().loadMap());
        UIButton newButton = new UIButton("new", game -> game.getCurrentState().newMap());
        HorizontalUIButtonMenu buttonMenu = new HorizontalUIButtonMenu(mainMenuButton, saveButton, loadButton, newButton);
        uiContainers.add(buttonMenu);
    }
}
