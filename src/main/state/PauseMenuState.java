package main.state;

import main.Game;
import story.Quest;
import ui.*;
import ui.clickable.UIButton;

import java.awt.*;

/**
 * @author Johan Salomonsson, Sara Persson, Simon Jern
 * The menu that you access by pausing the game.
 */
public class PauseMenuState extends State{

    private UIContainer questMenu;

    public PauseMenuState() {
        super();
    }

    @Override
    public void setupUI() {
        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton resumeGame = new UIButton("Resume Game", (game) -> game.resumeGame());
        UIButton saveGame = new UIButton("Save Game", (game) -> game.saveGame());
        UIButton mainMenu = new UIButton("Main Menu", (game) -> game.goToMainMenu());
        UIButton settings = new UIButton("Settings", (game) -> game.goToSettingsMenu());
        UIButton exitGame = new UIButton("Exit Game", (game) -> System.exit(0));

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

        exitGame.setBackgroundColor(Color.GRAY);
        exitGame.setClickColor(Color.YELLOW);
        exitGame.setHoverColor(Color.lightGray);
        exitGame.setPadding(25);

        VerticalContainer pauseMenu = new VerticalContainer(resumeGame, saveGame, mainMenu, settings, exitGame);
        pauseMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(pauseMenu);

        questMenu = new VerticalContainer(new UIText("Active quests"));
        questMenu.setBackgroundColor(Color.DARK_GRAY);
        questMenu.setBorderColor(Color.WHITE);
        uiContainers.add(questMenu);
    }

    public void updateQuestMenu(GameState gameState){
        questMenu.clear();
        questMenu.addComponent(new UIText("Active quests"));
        for(Quest quest : gameState.getQuestManager().getActiveQuests()){
            questMenu.addComponent(new UIText(quest.getName()));
        }
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.resumeGame();
    }
}
