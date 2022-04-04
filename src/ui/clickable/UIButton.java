package ui.clickable;

import main.state.State;
import ui.Spacing;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements the UI for a clickable button.
 */
public class UIButton extends UIClickable{

    private final UIContainer buttonContainer;
    private final UIText buttonText;

    private final ClickAction clickAction;

    public UIButton(String buttonText, ClickAction clickAction) {
        this.buttonText = new UIText(buttonText);
        this.clickAction = clickAction;

        setMargin(new Spacing(5, 0, 0, 0));

        buttonContainer = new VerticalContainer();
        buttonContainer.setCenterChildren(true);
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
    public void onClick(State state) {
        if(hasFocus) {
            clickAction.execute(state);
        }
    }

    @Override
    public void onDrag(State state) {

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
