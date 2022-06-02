package main.state;

import main.Game;
import ui.*;
import ui.clickable.UIButton;
import ui.clickable.UIText;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

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

        UIButton newGame = new UIButton("New Game", game -> game.enterUsername());
        UIButton loadGame = new UIButton("Load Game", game -> game.loadGame());
        UIButton settings = new UIButton("Settings", game -> game.goToSettingsMenu());
        UIButton exitGame = new UIButton("Exit Game", game -> displayWarning(game));
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

        VerticalContainer menu = new VerticalContainer(newGame, loadGame, settings, worldEditor, exitGame);
        menu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(menu);
    }


    private void displayWarning(Game game) {
        Locale locale = Locale.ENGLISH;
        JOptionPane.setDefaultLocale(locale);
        int answer = JOptionPane.showConfirmDialog(null,
                "Are you sure you would like to exit the game?", "Warning, you are about to exit the game!",
                JOptionPane.YES_NO_OPTION);

        if(answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    @Override
    public void escapeButtonPressed(Game game) {

    }
}
