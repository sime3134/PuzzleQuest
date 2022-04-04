package ui;

import ui.clickable.UIButton;

import java.awt.*;
import java.util.List;

/**
 * @author Simon Jern
 */
public class UIButtonMenu extends HorizontalContainer{

    public UIButtonMenu(){
        setBackgroundColor(Color.DARK_GRAY);
    }

    public UIButtonMenu(UIButton ...buttons) {
        for(UIButton button : buttons){
            addComponent(button);
        }
    }
}
