package main.state;

import ui.*;
import ui.clickable.UIButton;

import java.awt.*;

/**
 * @author Johan Salomonsson
 */
public class MainMenuState extends State{


    public MainMenuState(){
        loadMap();
        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton newGame = new UIButton("New Game", game -> game.startNewGame());
        UIButton loadGame = new UIButton("Load Game", game -> game.loadGame());
        UIButton settings = new UIButton("Settings", game -> game.goToSettingsMenu());
        UIButton exitGame = new UIButton("Exit Game", game -> System.exit(0));
        UIButton worldEditor = new UIButton("World Editor", game -> game.goToWorldEditor());

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

        VerticalContainer menu = new VerticalContainer(newGame, loadGame, exitGame, settings, worldEditor);
        menu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(menu);
        /*
        UIButtonMenu newGameButton = new UIButtonMenu(newGame);
        newGameButton.setFixedPosition(true);
        newGameButton.setAbsolutePosition(new Vector2D((Settings.getScreenWidth()/2) - newGameButton.getWidth(), 100));
        uiContainers.add(newGameButton);

        UIButtonMenu loadGameButton = new UIButtonMenu(loadGame);
        loadGameButton.setFixedPosition(true);
        loadGameButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 200));
        uiContainers.add(loadGameButton);

        UIButtonMenu exitButton = new UIButtonMenu(exitGame);
        exitButton.setFixedPosition(true);
        exitButton.setAbsolutePosition(new Vector2D((Settings.getScreenWidth()/2) - (exitGame.getWidth()/2), 300));
        uiContainers.add(exitButton);

        UIContainer settingsButton = new UIButtonMenu(settings);
        settingsButton.setFixedPosition(true);
        settingsButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 400));
        uiContainers.add(settingsButton);

        UIContainer worldEditorButton = new UIButtonMenu(worldEditor);
        worldEditorButton.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.BOTTOM));
        uiContainers.add(worldEditorButton); */
    }
}
