package main.state;

import core.Vector2D;
import settings.Settings;
import ui.*;
import ui.clickable.UIButton;

public class SettingsMenuState extends State{

    public SettingsMenuState() {
        loadMap();

        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Puzzle Quest 2.0");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIContainer audioOnOff = new HorizontalContainer();
        audioOnOff.setFixedPosition(true);
        audioOnOff.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 100));
        UIText audioText = new UIText("Audio");
        audioText.setFontSize(18);
        audioOnOff.addComponent(audioText);
        uiContainers.add(audioOnOff);

        UIButton on = new UIButton("ON", game -> game.setCurrentState(game.getGameState()));
        UIButton off = new UIButton("OFF", game -> game.setCurrentState(game.getGameState()));
        UIButton increase = new UIButton("+", game -> game.setCurrentState(game.getGameState()));
        UIButton decrease = new UIButton("-", game -> game.setCurrentState(game.getGameState()));

        UIButtonMenu onButton = new UIButtonMenu(on);
        onButton.setFixedPosition(true);
        onButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2 - 150, 200));
        uiContainers.add(onButton);

        UIButtonMenu offButton = new UIButtonMenu(off);
        offButton.setFixedPosition(true);
        offButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 200));
        uiContainers.add(offButton);

        UIButtonMenu increaseButton = new UIButtonMenu(increase);
        increaseButton.setFixedPosition(true);
        increaseButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2 - 150, 400));
        uiContainers.add(increaseButton);

        UIButtonMenu decreaseButton = new UIButtonMenu(decrease);
        decreaseButton.setFixedPosition(true);
        decreaseButton.setAbsolutePosition(new Vector2D(Settings.getScreenWidth()/2, 400));
        uiContainers.add(decreaseButton);

    }

    @Override
    public void prepare() {

    }
}
