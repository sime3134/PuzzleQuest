package main.state;

import main.Game;
import settings.Settings;
import ui.*;
import ui.clickable.UIButton;
import ui.clickable.UICheckbox;

import java.awt.*;

/**
 * @Author Johan Salomonsson, Sara Persson
 * Menu for settings.
 */
public class SettingsMenuState extends State{

    private final GameState gameState;

    public SettingsMenuState(GameState gameState) {
        super();
        this.gameState = gameState;
        loadMap("map", false);
    }

    @Override
    protected void setupUI() {
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

        audioTxt.setFontSize(30);
        audio.setFontSize(20);
        audioTxt.getPadding().setBottom(20);

        increase.setBackgroundColor(Color.GRAY);
        increase.setClickColor(Color.YELLOW);
        increase.setHoverColor(Color.lightGray);
        increase.setPadding(10);
        increase.setFontSize(30);

        decrease.setBackgroundColor(Color.GRAY);
        decrease.setClickColor(Color.YELLOW);
        decrease.setHoverColor(Color.lightGray);
        decrease.setPadding(10);
        decrease.setFontSize(30);

        UIText displayText = new UIText("Display");
        UICheckbox fullscreen = new UICheckbox("Fullscreen", Settings.getFullScreenSetting(),
                game -> game.setFullScreen());

        displayText.setFontSize(30);
        fullscreen.setFontSize(20);
        displayText.getPadding().setBottom(20);
        displayText.getPadding().setTop(20);

        back.setBackgroundColor(Color.GRAY);
        back.setClickColor(Color.YELLOW);
        back.setHoverColor(Color.lightGray);
        back.setWidth(80);

        VerticalContainer settingsMenu = new VerticalContainer(audioTxt, audio, increase, decrease, displayText,
                fullscreen);
        settingsMenu.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        settingsMenu.setCenterChildren(true);
        uiContainers.add(settingsMenu);

        VerticalContainer backButton = new VerticalContainer(back);
        backButton.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        uiContainers.add(backButton);
    }

    @Override
    public void draw(Graphics g) {
        currentMap.draw(g, camera, gameObjects);
        gameObjects.forEach(gameObject -> gameObject.draw(g, camera));
        if(gameState != null) {
            gameState.getGameObjects().forEach(gameObject -> gameObject.draw(g, camera));
        }
        mouseHandler.draw(g);
        uiContainers.forEach(uiContainer -> uiContainer.draw(g));
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.goToLastState();
    }
}
