package main.state;

import main.Game;
import ui.Alignment;
import ui.clickable.UIText;
import ui.clickable.UIButton;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * @author Johan Salomonsson, Sara Persson, Simon Jern
 * The menu that you access by pausing the game.
 */
public class PauseMenuState extends State{

    private boolean canSave;

    public void setCanSave(boolean canSave) {
        this.canSave = canSave;
    }

    public PauseMenuState() {
        super();
        canSave = true;
    }

    @Override
    public void setupUI() {
        super.setupUI();
        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Game paused");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton resumeGame = new UIButton("Resume Game", game -> game.resumeGame());
        UIButton saveGame = new UIButton("Save Game", game -> game.saveGame());
        UIButton mainMenu = new UIButton("Main Menu", game -> displayWarningMainMenu(game));
        UIButton settings = new UIButton("Settings", game -> game.goToSettingsMenu());
        UIButton controls = new UIButton("Controls", game -> game.goToControlsMenu());
        UIButton exitGame = new UIButton("Exit Game", game -> displayWarningExitGame(game));

        resumeGame.setBackgroundColor(Color.GRAY);
        resumeGame.setClickColor(Color.YELLOW);
        resumeGame.setHoverColor(Color.lightGray);
        resumeGame.setPadding(25);

        saveGame.setBackgroundColor(Color.GRAY);
        saveGame.setClickColor(Color.YELLOW);
        saveGame.setHoverColor(Color.lightGray);
        saveGame.setPadding(25);
        saveGame.setVisible(canSave);

        mainMenu.setBackgroundColor(Color.GRAY);
        mainMenu.setClickColor(Color.YELLOW);
        mainMenu.setHoverColor(Color.lightGray);
        mainMenu.setPadding(25);

        settings.setBackgroundColor(Color.GRAY);
        settings.setClickColor(Color.YELLOW);
        settings.setHoverColor(Color.lightGray);
        settings.setPadding(25);

        controls.setBackgroundColor(Color.GRAY);
        controls.setClickColor(Color.YELLOW);
        controls.setHoverColor(Color.lightGray);
        controls.setPadding(25);

        exitGame.setBackgroundColor(Color.GRAY);
        exitGame.setClickColor(Color.YELLOW);
        exitGame.setHoverColor(Color.lightGray);
        exitGame.setPadding(25);

        VerticalContainer pauseMenu = new VerticalContainer(resumeGame, saveGame, mainMenu, settings,
                controls, exitGame);
        pauseMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(pauseMenu);
    }


    private void displayWarningExitGame(Game game) {
        Locale locale = Locale.ENGLISH;
        JOptionPane.setDefaultLocale(locale);
        int answer = JOptionPane.showConfirmDialog(null,
                "Are you sure you would like to exit the game?\nAll unsaved progress will be lost.",
                "Warning, you are about to exit the game!",
                JOptionPane.YES_NO_OPTION);

        if(answer == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void displayWarningMainMenu(Game game) {
        Locale locale = Locale.ENGLISH;
        JOptionPane.setDefaultLocale(locale);
        int answer = JOptionPane.showConfirmDialog(null,
                "Are you sure you would like to return to the main menu?\nAll unsaved" +
                        " progress will be lost.",
                "Warning, you are about to go back to the main menu!",
                JOptionPane.YES_NO_OPTION);

        if(answer == JOptionPane.YES_OPTION) {
            game.goToMainMenu();
        }
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.resumeGame();
    }
}
