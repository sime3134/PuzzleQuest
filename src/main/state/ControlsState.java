package main.state;

import main.Game;
import ui.Alignment;
import ui.clickable.UIButton;
import ui.clickable.UIText;
import ui.containers.HorizontalContainer;
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

        UIContainer container = new VerticalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        UIText title = new UIText("Controls");
        title.setFontSize(28);
        container.addComponent(title);
        uiContainers.add(container);

        UIText wasd = new UIText("[W] Move up\n[A] Move left\n[S] Move down\n[D] Move right");
        UIText interact = new UIText("[E] Interact");
        UIText quest = new UIText("[Q] View questlog");
        UIText esc = new UIText("[Esc] Pause/Go back");
        UIText mouse = new UIText("\nUse your mouse to navigate through menus and the questlog.");

        UIContainer controls = new VerticalContainer(wasd, interact, quest, esc, mouse);
        controls.setBackgroundColor(Color.DARK_GRAY);
        controls.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.CENTER));
        controls.setCenterChildren(true);
        controls.setPadding(10);
        controls.setBorderColor(Color.WHITE);

        uiContainers.add(controls);

        UIButton back = new UIButton("Back", game -> game.goToLastState());
        back.setBackgroundColor(Color.GRAY);
        back.setClickColor(Color.YELLOW);
        back.setHoverColor(Color.lightGray);
        back.setWidth(80);

        HorizontalContainer backButton = new HorizontalContainer(back);
        backButton.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        uiContainers.add(backButton);
    }
}
