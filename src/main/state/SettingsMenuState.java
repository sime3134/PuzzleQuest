package main.state;

import core.Vector2D;
import settings.Settings;
import ui.*;
import ui.clickable.UIButton;

import java.awt.*;

/**
 * @Author Johan Salomonsson
 */
public class SettingsMenuState extends State{

    public SettingsMenuState() {
        loadMap();

        UIContainer container = new VerticalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Audio");
        title.setFontSize(38);
        container.addComponent(title);
        uiContainers.add(container);

        UIButton on = new UIButton("ON", game -> game.setCurrentState(game.getGameState()));
        UIButton off = new UIButton("OFF", game -> game.setCurrentState(game.getGameState()));
        UIButton increase = new UIButton("+", game -> game.setCurrentState(game.getGameState()));
        UIButton decrease = new UIButton("-", game -> game.setCurrentState(game.getGameState()));
        UIButton back = new UIButton("Back", game -> game.setCurrentState(new MainMenuState()));

        on.setBackgroundColor(Color.GRAY);
        on.setClickColor(Color.YELLOW);
        on.setHoverColor(Color.lightGray);
        on.setPadding(25);
        on.setFontSize(20);

        off.setBackgroundColor(Color.GRAY);
        off.setClickColor(Color.YELLOW);
        off.setHoverColor(Color.lightGray);
        off.setPadding(25);
        off.setFontSize(20);

        increase.setBackgroundColor(Color.GRAY);
        increase.setClickColor(Color.YELLOW);
        increase.setHoverColor(Color.lightGray);
        increase.setPadding(25);
        increase.setFontSize(30);

        decrease.setBackgroundColor(Color.GRAY);
        decrease.setClickColor(Color.YELLOW);
        decrease.setHoverColor(Color.lightGray);
        decrease.setPadding(25);
        decrease.setFontSize(30);

        back.setBackgroundColor(Color.GRAY);
        back.setClickColor(Color.YELLOW);
        back.setHoverColor(Color.lightGray);
        back.setWith(80);

        VerticalUIButtonMenu settingsMenu = new VerticalUIButtonMenu(on, off, increase, decrease);
        settingsMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        uiContainers.add(settingsMenu);

        VerticalUIButtonMenu backButton = new VerticalUIButtonMenu(back);
        backButton.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        uiContainers.add(backButton);

    }

    @Override
    public void prepare() {

    }
}
