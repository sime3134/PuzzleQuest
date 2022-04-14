package ui;

import ui.clickable.UIButton;

import java.awt.*;

/**
 * @author Johan Salomonsson
 */
public class VerticalUIButtonMenu extends VerticalContainer {

    public VerticalUIButtonMenu() {
        setBackgroundColor(Color.DARK_GRAY);
    }

    public VerticalUIButtonMenu(UIButton... buttons) {
        for (UIButton button : buttons) {
            addComponent(button);
        }
    }
}
