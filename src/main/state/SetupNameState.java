package main.state;

import content.ContentManager;
import main.Game;
import ui.Alignment;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;
import ui.clickable.UIButton;

import java.awt.*;

/**
 * @author Johan Salomonsson
 * Menu that lets you enter a username before the game starts.
 * Not finished.
 */
public class SetupNameState extends State{

    public SetupNameState(ContentManager content) {
        super(content);
    }

    @Override
    protected void setupUI() {
        super.setupUI();

        UIContainer container = new VerticalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIText enterName = new UIText("Enter your character's name");
        enterName.setFontSize(24);
        UIText nameRules = new UIText("3-10 characters");
        UIButton startGame = new UIButton("Start Game", game -> game.startNewGame());
        UIButton back = new UIButton("Back", game -> game.goToLastState());

        startGame.setBackgroundColor(Color.GRAY);
        startGame.setClickColor(Color.YELLOW);
        startGame.setHoverColor(Color.lightGray);

        back.setBackgroundColor(Color.GRAY);
        back.setClickColor(Color.YELLOW);
        back.setHoverColor(Color.lightGray);
        back.setWidth(80);

        VerticalContainer menu = new VerticalContainer(enterName, nameRules, startGame);
        menu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        menu.setCenterChildren(true);
        uiContainers.add(menu);

        VerticalContainer backButton = new VerticalContainer(back);
        backButton.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        uiContainers.add(backButton);
    }

    @Override
    public void update(Game game) {
        super.update(game);
        camera.centerOnMap(currentMap);
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.goToLastState();
    }
}
