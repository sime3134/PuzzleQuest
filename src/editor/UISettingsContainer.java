package editor;

import map.GameMap;
import settings.Settings;
import ui.Alignment;
import ui.VerticalContainer;
import ui.clickable.UICheckbox;
import ui.clickable.UIMinimap;

/**
 * @author Simon Jern
 * Implements a UI container used in the tile editor to display settings and mini map.
 */
public class UISettingsContainer extends VerticalContainer {

    public UISettingsContainer(GameMap currentMap) {
        setAlignment(new Alignment(Alignment.Horizontal.RIGHT, Alignment.Vertical.TOP));
        addComponent(new UIMinimap(currentMap));
        addComponent(new UICheckbox("GRID", Settings.getShouldRenderGrid()));
    }
}
