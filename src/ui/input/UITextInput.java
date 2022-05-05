package ui.input;

import input.KeyInputConsumer;
import main.Game;
import ui.HorizontalContainer;
import ui.Spacing;
import ui.UIContainer;
import ui.UIText;
import ui.clickable.UIClickable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class UITextInput extends UIClickable implements KeyInputConsumer {

    private UIContainer container;

    private UIText inputText;

    private int minLength;
    private int maxLength;

    public String getText() {
        return inputText.getText();
    }

    public UITextInput(int minLength, int maxLength) {
        this(minLength, maxLength, "");
    }

    public UITextInput(int minLength, int maxLength, String placeholder) {
        margin = new Spacing(0);
        padding = new Spacing(0);
        this.minLength = minLength;
        this.maxLength = maxLength;

        container = new HorizontalContainer();
        container.setCenterChildren(true);
        container.setMargin(new Spacing(0));
        container.setBorderColor(Color.BLACK);
        container.setBackgroundColor(Color.DARK_GRAY);
        container.setFixedWidth(200);
        container.setFixedHeight(30);
        inputText = new UIText(placeholder);
        container.addComponent(inputText);
    }

    @Override
    public void update(Game game) {
        super.update(game);
        container.update(game);
        inputText.update(game);
        setWidth(container.getWidth());
        setHeight(container.getHeight());
    }

    @Override
    public void onKeyPressed(int key) {
        String currentValue = inputText.getText();

        if(key == KeyEvent.VK_BACK_SPACE){
            if(!currentValue.isEmpty()) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }
        }else if(key == KeyEvent.VK_SPACE && currentValue.length() < maxLength){
            currentValue += " ";
        }else if(key == KeyEvent.VK_UNDERSCORE && currentValue.length() < maxLength){
            currentValue += "_";
        }else if(currentValue.length() < maxLength) {
            String keyText = KeyEvent.getKeyText(key);
            if(keyText.length() == 1){
                currentValue += keyText;
            }
        }

        inputText.setText(currentValue);
    }

    @Override
    public void onClick(Game game) {
        game.getCurrentState().setKeyInputConsumer(this);
        container.setBorderColor(Color.LIGHT_GRAY);
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );

        g.drawImage(inputText.getSprite(),
                absolutePosition.intX() + inputText.getRelativePosition().intX(),
                absolutePosition.intY() + inputText.getRelativePosition().intY(),
                null
        );
    }

    public void clear() {
        inputText.setText("");
    }

    public boolean validate() {
        boolean followsRules = inputText.getText().length() >= minLength && inputText.getText().length() <= maxLength;

        if(!followsRules){
            container.setBorderColor(Color.RED);
        }

        return followsRules;
    }
}
