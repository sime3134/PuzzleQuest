package main.state;

import main.Game;
import ui.*;
import ui.clickable.UIButton;

import java.awt.*;

/**
 * @author Johan Salomonsson, Sara Persson, Simon Jern
 * The menu that you access by pausing the game.
 */
public class PauseMenuState extends State{

    public PauseMenuState(Game game) {
        super(game);
    }

    @Override
    public void initialize(Game game) {

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

        UIButton resumeGame = new UIButton("Resume Game", () -> game.resumeGame());
        UIButton saveGame = new UIButton("Save Game", () -> game.saveGame());
        UIButton mainMenu = new UIButton("Main Menu", () -> game.goToMainMenu());
        UIButton settings = new UIButton("Settings", () -> game.goToSettingsMenu());

        resumeGame.setBackgroundColor(Color.GRAY);
        resumeGame.setClickColor(Color.YELLOW);
        resumeGame.setHoverColor(Color.lightGray);
        resumeGame.setPadding(25);

        saveGame.setBackgroundColor(Color.GRAY);
        saveGame.setClickColor(Color.YELLOW);
        saveGame.setHoverColor(Color.lightGray);
        saveGame.setPadding(25);

        mainMenu.setBackgroundColor(Color.GRAY);
        mainMenu.setClickColor(Color.YELLOW);
        mainMenu.setHoverColor(Color.lightGray);
        mainMenu.setPadding(25);

        settings.setBackgroundColor(Color.GRAY);
        settings.setClickColor(Color.YELLOW);
        settings.setHoverColor(Color.lightGray);
        settings.setPadding(25);

        VerticalContainer pauseMenu = new VerticalContainer(resumeGame, saveGame, mainMenu, settings);
        pauseMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(pauseMenu);
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.resumeGame();
    }
}
