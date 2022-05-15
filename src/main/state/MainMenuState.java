package main.state;

import main.Game;
import ui.*;
import ui.clickable.UIButton;
import ui.clickable.UIText;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;

import java.awt.*;

/**
 * @author Johan Salomonsson, Sara Persson, Simon Jern
 * The applications main menu.
 */
public class MainMenuState extends State{

    public MainMenuState() {
        super();
    }

    @Override
    public void setupUI() {
        super.setupUI();
        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton newGame = new UIButton("New Game", (game) -> game.enterUsername());
        UIButton loadGame = new UIButton("Load Game", (game) -> game.loadGame());
        UIButton settings = new UIButton("Settings", (game) -> game.goToSettingsMenu());
        UIButton exitGame = new UIButton("Exit Game", (game) -> System.exit(0));
        UIButton worldEditor = new UIButton("World Editor", (game) -> game.goToWorldEditor());

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
    public void escapeButtonPressed(Game game) {

    }
}
