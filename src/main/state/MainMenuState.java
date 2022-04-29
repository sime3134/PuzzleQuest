package main.state;

import main.Game;
import ui.*;
import ui.clickable.UIButton;
import utilities.WorldMapDrawer;

import java.awt.*;

/**
 * @author Johan Salomonsson, Sara Persson, Simon Jern
 * The applications main menu.
 */
public class MainMenuState extends State{

    public MainMenuState(Game game) {
        super(game);
        game.loadMap("main_menu_map");
        WorldMapDrawer.
    }

    @Override
    protected void setupUI(Game game) {
        super.setupUI(game);
        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton newGame = new UIButton("New Game", () -> game.enterUsername());
        UIButton loadGame = new UIButton("Load Game", () -> game.loadGame());
        UIButton settings = new UIButton("Settings", () -> game.goToSettingsMenu());
        UIButton exitGame = new UIButton("Exit Game", () -> System.exit(0));
        UIButton worldEditor = new UIButton("World Editor", () -> game.goToWorldEditor());

        newGame.setBackgroundColor(Color.GRAY);
        newGame.setClickColor(Color.YELLOW);
        newGame.setHoverColor(Color.lightGray);
        newGame.setPadding(25);

        loadGame.setBackgroundColor(Color.GRAY);
        loadGame.setClickColor(Color.YELLOW);
        loadGame.setHoverColor(Color.lightGray);
        loadGame.setPadding(25);

        settings.setBackgroundColor(Color.GRAY);
        settings.setClickColor(Color.YELLOW);
        settings.setHoverColor(Color.lightGray);
        settings.setPadding(25);

        exitGame.setBackgroundColor(Color.GRAY);
        exitGame.setClickColor(Color.YELLOW);
        exitGame.setHoverColor(Color.lightGray);
        exitGame.setPadding(25);

        worldEditor.setBackgroundColor(Color.GRAY);
        worldEditor.setClickColor(Color.YELLOW);
        worldEditor.setHoverColor(Color.lightGray);
        worldEditor.setPadding(25);

        VerticalContainer menu = new VerticalContainer(newGame, loadGame, settings, worldEditor, exitGame);
        menu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(menu);
    }

    @Override
    public void update(Game game) {
        super.update(game);
        game.getCamera().centerOnMap(game.getCurrentMap());
    }

    @Override
    public void escapeButtonPressed(Game game) {

    }
}
