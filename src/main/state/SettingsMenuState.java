package main.state;

import main.Game;
import settings.Settings;
import ui.*;
import ui.clickable.UIButton;
import ui.clickable.UICheckbox;
import ui.clickable.UIText;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;
import utilities.WorldMapDrawer;

import java.awt.*;

/**
 * @Author Johan Salomonsson, Sara Persson, Simon Jern
 * Menu for settings.
 */
public class SettingsMenuState extends State{

    public SettingsMenuState() {
        super();
    }

    @Override
    public void setupUI() {
        super.setupUI();
        UIContainer container = new VerticalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Settings");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIText audioTxt= new UIText("Audio");
        UICheckbox audio = new UICheckbox("ON/OFF", Settings.getAudioOn(), game -> game.toggleAudio());
        UIButton increase = new UIButton("+", game -> Settings.increaseVolume());
        UIButton decrease = new UIButton("-", game -> Settings.decreaseVolume());
        UIButton back = new UIButton("Back", game -> game.goToLastState());
        UIButton saveMap = new UIButton("Save world map to file",
                game -> WorldMapDrawer.generateFullWorldMap(game.getMapManager().getWorldMap(), 3200, 5));

        audioTxt.setFontSize(30);
        audio.setFontSize(20);
        audioTxt.getPadding().setBottom(20);
        audioTxt.setUnderlined(true);

        increase.setBackgroundColor(Color.GRAY);
        increase.setClickColor(Color.YELLOW);
        increase.setHoverColor(Color.lightGray);
        increase.getMargin().setLeft(10);
        increase.setFontSize(30);
        increase.setWidth(60);
        increase.setHeight(50);

        decrease.setBackgroundColor(Color.GRAY);
        decrease.setClickColor(Color.YELLOW);
        decrease.setHoverColor(Color.lightGray);
        decrease.setFontSize(30);
        decrease.setWidth(60);
        decrease.setHeight(50);

        UIContainer increaseDecrease = new HorizontalContainer(decrease, increase);

        UIText displayText = new UIText("Display");
        UICheckbox fullscreen = new UICheckbox("Fullscreen", Settings.getFullScreenSetting(),
                game -> game.setFullScreen());

        displayText.setFontSize(30);
        fullscreen.setFontSize(20);
        displayText.getPadding().setBottom(20);
        displayText.getPadding().setTop(20);
        displayText.setUnderlined(true);

        back.setBackgroundColor(Color.GRAY);
        back.setClickColor(Color.YELLOW);
        back.setHoverColor(Color.lightGray);
        back.setWidth(80);

        saveMap.setBackgroundColor(Color.GRAY);
        saveMap.setClickColor(Color.YELLOW);
        saveMap.setHoverColor(Color.lightGray);
        saveMap.setWidth(400);
        saveMap.getMargin().setLeft(10);

        VerticalContainer settingsMenu = new VerticalContainer(audioTxt, audio, increaseDecrease, displayText,
                fullscreen);
        settingsMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        settingsMenu.setCenterChildren(true);
        uiContainers.add(settingsMenu);

        HorizontalContainer backButton = new HorizontalContainer(back, saveMap);
        backButton.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        uiContainers.add(backButton);
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.goToLastState();
    }
}
