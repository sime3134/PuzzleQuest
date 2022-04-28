package main.state;

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

    public EditorState(Game game){
        super(game);
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Game map", "txt"));
        fileChooser.setCurrentDirectory(new File(getClass().getResource("/").getFile()));
        game.createNewMap(64, 64, game.getContent());
        setupMouseButtons();
        Settings.setDebugMode(true);
        game.getCamera().centerOnMap(game.getCurrentMap());
    }

    private void setupMouseButtons() {
        mouseHandler.setWheelButtonAction(new CameraMovement());
        mouseHandler.switchLeftButtonAction(new SceneryTool());
        mouseHandler.setRightButtonAction(new ClearAction());
    }

    @Override
    protected void setupUI(Game game) {
        super.setupUI(game);
        UITabContainer toolsContainer = new UITabContainer();
        toolsContainer.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));
        toolsContainer.addTab("Scenery", new UISceneryMenu(game.getContent()));
        toolsContainer.addTab("Tiles", new UITileMenu(game.getContent()));
        toolsContainer.addTab("Animated", new UIAnimatedTileMenu(game.getContent()));
        uiContainers.add(toolsContainer);

        UIButton mainMenuButton = new UIButton("main menu", () -> game.goToMainMenu());
        UIButton saveButton = new UIButton("save", () -> displaySaveDialog(game));
        UIButton loadButton = new UIButton("load", () -> displayLoadDialog(game));
        UIButton newButton = new UIButton("new", () -> game.createNewMap(64, 64, game.getContent()));
        HorizontalContainer buttonMenu = new HorizontalContainer(mainMenuButton, saveButton, loadButton, newButton);
        mainMenuButton.setWidth(180);
        loadButton.setWidth(180);
        saveButton.setWidth(180);
        newButton.setWidth(180);
        uiContainers.add(buttonMenu);
    }

    private void displayLoadDialog(Game game) {
        final int fileChosen = fileChooser.showOpenDialog(new JFrame());

        if(fileChosen == JFileChooser.APPROVE_OPTION){
            game.loadMapFromPath(fileChooser.getSelectedFile().getPath());
        }
    }

    private void displaySaveDialog(Game game){
        final int fileChosen = fileChooser.showSaveDialog(new JFrame());

        if(fileChosen == JFileChooser.APPROVE_OPTION){
            game.saveMap(fileChooser.getSelectedFile().toString());
        }
    }

    @Override
    public void escapeButtonPressed(Game game) {

    }
}
