package main.state;

import content.ContentManager;
import editor.UIAnimatedTileMenu;
import editor.UINPCMenu;
import editor.UISceneryMenu;
import editor.UITileMenu;
import input.mouse.action.CameraMovement;
import input.mouse.action.ClearAction;
import input.mouse.action.GameObjectTool;
import main.Game;
import ui.*;
import ui.clickable.UIButton;
import utilities.WorldMapDrawer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    private final JFileChooser fileChooser;

    private UIContainer options;

    public UIContainer getOptions() {
        return options;
    }

    public EditorState(ContentManager content){
        super();
        setupToolsContainers(content);
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Game map", "txt"));
        fileChooser.setCurrentDirectory(new File(getClass().getResource("/").getFile()));
        setupMouseButtons();
    }

    public void setupToolsContainers(ContentManager content) {
        UITabContainer toolsContainer = new UITabContainer();
        toolsContainer.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));
        toolsContainer.addTab("Scenery", new UISceneryMenu(content));
        toolsContainer.addTab("Tiles", new UITileMenu(content));
        toolsContainer.addTab("Animated", new UIAnimatedTileMenu(content));
        toolsContainer.addTab("NPC", new UINPCMenu(content));
        uiContainers.add(toolsContainer);
    }

    private void setupMouseButtons() {
        mouseHandler.setWheelButtonAction(new CameraMovement());
        mouseHandler.switchLeftButtonAction(new GameObjectTool());
        mouseHandler.setRightButtonAction(new ClearAction());
    }

    @Override
    public void setupUI() {
        super.setupUI();
        UIButton mainMenuButton = new UIButton("main menu", (game) -> displayWarning(game));
        UIButton saveButton = new UIButton("save", (game) -> displaySaveDialog(game));
        UIButton loadButton = new UIButton("load", (game) -> displayLoadDialog(game));
        UIButton newButton = new UIButton("new", (game) -> game.createNewMap(64, 64, game.getContent()));
        UIButton pngButton = new UIButton("PNG",
                (game) -> WorldMapDrawer.generateMap(game.getCurrentMap(), 2304, game.getCurrentMap().getName()));
        HorizontalContainer buttonMenu = new HorizontalContainer(mainMenuButton, pngButton, saveButton,
                loadButton, newButton);
        mainMenuButton.setWidth(180);
        loadButton.setWidth(180);
        saveButton.setWidth(180);
        newButton.setWidth(180);
        pngButton.setWidth(180);
        uiContainers.add(buttonMenu);

        options = new VerticalContainer();
        options.setFixedPosition(true);
        options.setBackgroundColor(Color.GRAY);
        uiContainers.add(options);
    }

    private void displayWarning(Game game) {
        int answer = JOptionPane.showConfirmDialog(null,
                "Have you saved your progress?", "Warning!",
                JOptionPane.YES_NO_OPTION);

        if(answer == JOptionPane.YES_OPTION) {
            game.goToMainMenu();
        }
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
