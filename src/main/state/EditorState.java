package main.state;

import editor.UISettingsContainer;
import editor.UITileMenu;
import map.GameMap;
import IO.MapIO;
import ui.Alignment;
import ui.UIButtonMenu;
import ui.UIContainer;
import ui.UITabContainer;
import ui.clickable.UIButton;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    public EditorState(){
        currentMap = new GameMap(16, 16, content);
        UIContainer container = new UISettingsContainer(currentMap);
        uiContainers.add(container);

        UITabContainer toolsContainer = new UITabContainer();
        toolsContainer.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));
        toolsContainer.addTab("Scenery", new UISettingsContainer(currentMap));
        toolsContainer.addTab("Tiles", new UITileMenu(content));
        uiContainers.add(toolsContainer);


        UIButton saveButton = new UIButton("SAVE", state -> MapIO.save(state.getCurrentMap()));
        UIButton loadButton = new UIButton("LOAD", state -> state.loadMap());
        UIButtonMenu buttonMenu = new UIButtonMenu(saveButton, loadButton);
        uiContainers.add(buttonMenu);
    }
}
