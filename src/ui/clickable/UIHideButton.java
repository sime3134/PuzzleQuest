package ui.clickable;

import main.Game;
import main.state.State;
import ui.UIComponent;
import ui.UIContainer;

import java.awt.*;

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

    private static ClickAction addOrRemove(UIContainer parentContainer, UIComponent componentToHide){
        return state -> {
            if(parentContainer.hasComponent(componentToHide)) {
                parentContainer.removeComponent(componentToHide);
            }else {
                parentContainer.addComponent(componentToHide);
            }
        };
    }

    @Override
    public void update(State state) {
        super.update(state);
        buttonText.setText("↓");

        if(!parentContainer.hasComponent(componentToHide)){
            buttonText.setText("↑");
        }
    }

    @Override
    public void onRelease(Game game) {

    }
}
