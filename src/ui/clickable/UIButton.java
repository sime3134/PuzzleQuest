package ui.clickable;

import main.state.State;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

import java.awt.*;

public class UIButton extends UIClickable{

    private final UIContainer buttonContainer;
    private final UIText buttonText;

    private final Runnable clickEvent;

    public UIButton(String buttonText, Runnable clickEvent) {
        this.buttonText = new UIText(buttonText);
        this.clickEvent = clickEvent;

        buttonContainer = new VerticalContainer();
        buttonContainer.addComponent(this.buttonText);
        buttonContainer.setFixedWidth(150);
    }

    @Override
    public void update(State state) {
        super.update(state);
        buttonContainer.update(state);
        width = buttonContainer.getWidth();
        height = buttonContainer.getHeight();

        buttonContainer.setBackgroundColor(Color.GRAY);

        if(hasFocus){
            buttonContainer.setBackgroundColor(Color.LIGHT_GRAY);
        }

        if(isPressed){
            buttonContainer.setBackgroundColor(Color.DARK_GRAY);
        }
    }

    @Override
    protected void onClick() {
        clickEvent.run();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );

        g.drawImage(buttonText.getSprite(),
                absolutePosition.intX() + buttonText.getRelativePosition().intX(),
                absolutePosition.intY() + buttonText.getRelativePosition().intY(),
                null
        );
    }

    @Override
    public Image getSprite() {
        return buttonContainer.getSprite();
    }
}
