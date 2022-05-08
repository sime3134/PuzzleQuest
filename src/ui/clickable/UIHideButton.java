package ui.clickable;

import core.Action;
import main.Game;
import ui.UIComponent;
import ui.UIContainer;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements the UI for a button to hide content.
 */
public class UIHideButton extends UIButton{

    private final UIContainer parentContainer;
    private final UIComponent componentToHide;

    public UIHideButton(UIContainer parentContainer, UIComponent componentToHide) {
        super("↓", addOrRemove(parentContainer, componentToHide));
        this.parentContainer = parentContainer;
        this.componentToHide = componentToHide;
        buttonContainer.setFixedWidth(30);
        setBackgroundColor(Color.DARK_GRAY);
    }

    private static Action addOrRemove(UIContainer parentContainer, UIComponent componentToHide){
        return (game) -> {
            if(parentContainer.hasComponent(componentToHide)) {
                parentContainer.removeComponent(componentToHide);
            }else {
                parentContainer.addComponent(componentToHide);
            }
        };
    }

    @Override
    public void update(Game game) {
        super.update(game);
        buttonText.setText("↓");

        if(!parentContainer.hasComponent(componentToHide)){
            buttonText.setText("↑");
        }
    }

    @Override
    public void onRelease(Game game) {

    }
}
