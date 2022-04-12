package main.state;

import map.GameMap;
import ui.Alignment;
import ui.UIButtonMenu;
import ui.clickable.UIButton;

import java.awt.*;

public class MainMenuState extends State{

    public MainMenuState(){
        loadMap();
        UIButton editorButton = new UIButton("Editor", game -> game.setState(new EditorState()));
        UIButton startGameButton = new UIButton("Start Game", game -> game.setState(new GameState()));

        startGameButton.setBackgroundColor(Color.ORANGE);
        startGameButton.setClickColor(Color.YELLOW);
        startGameButton.setHoverColor(Color.RED);

        UIButtonMenu buttonMenu = new UIButtonMenu(startGameButton, editorButton);
        buttonMenu.setBackgroundColor(Color.ORANGE);
        buttonMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.BOTTOM));

        uiContainers.add(buttonMenu);
    }
}
