package main.state;

import main.Game;
import ui.Alignment;
import ui.clickable.UIText;
import ui.containers.UIContainer;
import ui.containers.VerticalContainer;

import java.awt.*;

public class ControlsState extends State{
    @Override
    public void escapeButtonPressed(Game game) {
        game.goToLastState();
    }

    @Override
    public void setupUI() {
        super.setupUI();

        UIText wasd = new UIText("[W] Move up\n[A] Move left\n[S] Move down\n[D] Move right");
        UIText interact = new UIText("[E] Interact");
        UIText quest = new UIText("[Q] View questlog");
        UIText esc = new UIText("[Esc] Pause/Go back");
        UIText mouse = new UIText("Use your mouse to navigate through menus and the questlog");

        UIContainer controls = new VerticalContainer(wasd, interact, quest, esc, mouse);
        controls.setBackgroundColor(Color.DARK_GRAY);
        controls.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        controls.setCenterChildren(true);

        uiContainers.add(controls);
    }
}
