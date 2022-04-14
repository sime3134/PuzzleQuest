package main.state;

import core.Vector2D;
import settings.Settings;
import ui.*;
import ui.clickable.UIButton;

import java.awt.*;

public class PauseMenuState extends State{

    public PauseMenuState() {
        loadMap();

        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton resumeGame = new UIButton("Resume Game", game -> game.setCurrentState(game.getGameState()));
        UIButton saveGame = new UIButton("Save Game", game -> game.setCurrentState(game.getGameState()));
        UIButton mainMenu = new UIButton("Main Menu", game -> game.setCurrentState(game.getGameState()));
        UIButton settings = new UIButton("Settings", game -> game.setCurrentState(game.getGameState()));

        resumeGame.setBackgroundColor(Color.GRAY);
        resumeGame.setClickColor(Color.YELLOW);
        resumeGame.setHoverColor(Color.lightGray);

        saveGame.setBackgroundColor(Color.GRAY);
        saveGame.setClickColor(Color.YELLOW);
        saveGame.setHoverColor(Color.lightGray);

        mainMenu.setBackgroundColor(Color.GRAY);
        mainMenu.setClickColor(Color.YELLOW);
        mainMenu.setHoverColor(Color.lightGray);

        settings.setBackgroundColor(Color.GRAY);
        settings.setClickColor(Color.YELLOW);
        settings.setHoverColor(Color.lightGray);

        UIButtonMenu resumeButton = new UIButtonMenu(resumeGame);
        resumeButton.setFixedPosition(true);
        resumeButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 100));
        uiContainers.add(resumeButton);

        UIButtonMenu saveButton = new UIButtonMenu(saveGame);
        saveButton.setFixedPosition(true);
        saveButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 200));
        uiContainers.add(saveButton);

        UIButtonMenu mainMenuButton = new UIButtonMenu(mainMenu);
        mainMenuButton.setFixedPosition(true);
        mainMenuButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 300));
        uiContainers.add(mainMenuButton);

        UIButtonMenu settingsButton = new UIButtonMenu(settings);
        settingsButton.setFixedPosition(true);
        settingsButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 400));
        uiContainers.add(settingsButton);

    }

}
