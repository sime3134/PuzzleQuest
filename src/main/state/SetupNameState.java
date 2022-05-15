package main.state;

import main.Game;
import ui.Alignment;
import ui.containers.UIContainer;
import ui.clickable.UIText;
import ui.containers.VerticalContainer;
import ui.clickable.UIButton;
import ui.input.UITextInput;

import java.awt.*;

/**
 * @author Johan Salomonsson
 * Menu that lets you enter a username before the game starts.
 * Not finished.
 */
public class SetupNameState extends State{

    private UITextInput nameInput;

    public SetupNameState() {
        super();
    }

    public UITextInput getNameInput() {
        return nameInput;
    }

    @Override
    public void setupUI() {
        super.setupUI();
        UIContainer container = new VerticalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIText enterName = new UIText("Enter your character's name");
        nameInput = new UITextInput(3, 10);
        enterName.setFontSize(24);
        UIText nameRules = new UIText("3-10 characters");
        UIButton startGame = new UIButton("Start Game", (game) -> {
            if(nameInput.validate()) {
                game.startNewGame(nameInput.getText());
                nameInput.clear();
            }
        });
        UIButton back = new UIButton("Back", (game) -> {
            nameInput.clear();
            game.goToLastState();
        }
        );

        startGame.setBackgroundColor(Color.GRAY);
        startGame.setClickColor(Color.YELLOW);
        startGame.setHoverColor(Color.lightGray);

        back.setBackgroundColor(Color.GRAY);
        back.setClickColor(Color.YELLOW);
        back.setHoverColor(Color.lightGray);
        back.setWidth(80);

        VerticalContainer menu = new VerticalContainer(enterName, nameInput, nameRules, startGame);
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
        game.getCamera().centerOnMap(game.getCurrentMap());
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.goToLastState();
    }
}
