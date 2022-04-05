package main.state;

import map.GameMap;
import ui.Alignment;
import ui.UIButtonMenu;
import ui.clickable.UIButton;

import java.awt.*;

public class MainMenuState extends State{

    public MainMenuState(){
        currentMap = new GameMap(50, 50, content);

        //Använd inte new GameState();
        UIButton button = new UIButton("Hej", game -> game.setState(new EditorState()));

        button.setBackgroundColor(Color.ORANGE);
        button.setClickColor(Color.YELLOW);
        button.setHoverColor(Color.BLACK);

        UIButtonMenu buttonMenu = new UIButtonMenu(button);
        buttonMenu.setBackgroundColor(Color.ORANGE);
        buttonMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.BOTTOM));

        uiContainers.add(buttonMenu);
    }
}
