package editor;

import content.ContentManager;
import input.mouse.action.TileWalkabilityToggle;
import map.GameMap;
import settings.Settings;
import ui.Alignment;
import ui.VerticalContainer;
import ui.clickable.UICheckbox;
import ui.clickable.UIMinimap;
import ui.clickable.UIToolToggle;

/**
 * @author Simon Jern
 * Implements a UI container used in the tile editor to display settings and mini map.
 */
public class UISettingsContainer extends VerticalContainer {

    public UISettingsContainer(GameMap currentMap, ContentManager content) {
        setAlignment(new Alignment(Alignment.Horizontal.RIGHT, Alignment.Vertical.TOP));
        addComponent(new UIMinimap(currentMap));
        addComponent(new UICheckbox("GRID", Settings.getRenderGrid()));
        addComponent(new UICheckbox("Col. box", Settings.getRenderCollisionBox()));
        addComponent(new UICheckbox("Pathable", Settings.isPathable()));
        addComponent(new UIToolToggle(content.getImage("walkable"), new TileWalkabilityToggle(), 48, 48));
    }
}
