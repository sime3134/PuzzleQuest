package ui;

import ui.clickable.UIButton;

import java.awt.*;
import java.util.List;

/**
 * @author Simon Jern
 */
public class HorizontalUIButtonMenu extends HorizontalContainer{

    public HorizontalUIButtonMenu(){
        setBackgroundColor(Color.DARK_GRAY);
    }

    public HorizontalUIButtonMenu(UIButton ...buttons) {
        for(UIButton button : buttons){
            addComponent(button);
        }
    }
}
