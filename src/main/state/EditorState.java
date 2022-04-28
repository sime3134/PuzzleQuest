package main.state;

import content.ContentManager;
import editor.UIAnimatedTileMenu;
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

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    private final JFileChooser fileChooser;

    public EditorState(ContentManager content){
        super(content);
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Game map", "txt"));
        fileChooser.setCurrentDirectory(new File(getClass().getResource("/").getFile()));
        createNewMap(64, 64);
        setupMouseButtons();
        Settings.setDebugMode(true);
        camera.centerOnMap(currentMap);
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
        toolsContainer.addTab("Animated", new UIAnimatedTileMenu(content));
        uiContainers.add(toolsContainer);

        UIButton mainMenuButton = new UIButton("main menu", game -> game.goToMainMenu());
        UIButton saveButton = new UIButton("save", game -> displaySaveDialog());
        UIButton loadButton = new UIButton("load", game -> displayLoadDialog());
        UIButton newButton = new UIButton("new", game -> game.getCurrentState().createNewMap(64, 64));
        HorizontalContainer buttonMenu = new HorizontalContainer(mainMenuButton, saveButton, loadButton, newButton);
        mainMenuButton.setWidth(180);
        loadButton.setWidth(180);
        saveButton.setWidth(180);
        newButton.setWidth(180);
        uiContainers.add(buttonMenu);
    }

    private void displayLoadDialog() {
        final int fileChosen = fileChooser.showOpenDialog(new JFrame());

        if(fileChosen == JFileChooser.APPROVE_OPTION){
            loadMap(fileChooser.getSelectedFile().getPath(), true);
        }
    }

    private void displaySaveDialog(){
        final int fileChosen = fileChooser.showSaveDialog(new JFrame());

        if(fileChosen == JFileChooser.APPROVE_OPTION){
            saveMap(fileChooser.getSelectedFile().toString());
        }
    }

    @Override
    public void escapeButtonPressed(Game game) {

    }
}
