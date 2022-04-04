package main.state;

import editor.UIRenderSettingsContainer;
import editor.UITileMenu;
import map.GameMap;
import IO.MapIO;
import ui.UIButtonMenu;
import ui.UIContainer;
import ui.clickable.UIButton;

/**
 * @author Simon Jern
 * Implements a state for editing and saving new maps.
 */
public class EditorState extends State {

    public EditorState(){
        currentMap = new GameMap(16, 16, content);
        UIContainer container = new UIRenderSettingsContainer(currentMap);
        uiContainers.add(container);
        uiContainers.add(new UITileMenu(content));

        UIButton saveButton = new UIButton("SAVE", state -> MapIO.save(state.getCurrentMap()));
        UIButton loadButton = new UIButton("LOAD", state -> state.loadMap());
        UIButtonMenu buttonMenu = new UIButtonMenu(saveButton, loadButton);
        uiContainers.add(buttonMenu);
    }
}
