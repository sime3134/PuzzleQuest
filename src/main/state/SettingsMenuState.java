package main.state;

import settings.Settings;
import ui.*;
import ui.clickable.UIButton;
import ui.clickable.UICheckbox;

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

        UICheckbox audio = new UICheckbox("ON/OFF", Settings.getAudioMode());
        UIButton increase = new UIButton("+", game -> Settings.increaseVolume());
        UIButton decrease = new UIButton("-", game -> Settings.decreaseVolume());
        UIButton back = new UIButton("Back", game -> game.goToLastState());

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
        back.setWidth(80);

        VerticalContainer settingsMenu = new VerticalContainer(audio, increase, decrease);
        settingsMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        settingsMenu.setCenterChildren(true);
        uiContainers.add(settingsMenu);

        VerticalContainer backButton = new VerticalContainer(back);
        backButton.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        uiContainers.add(backButton);

    }
}
