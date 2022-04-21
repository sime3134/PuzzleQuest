package editor;

import content.ContentManager;
import input.mouse.action.TilePlacer;
import map.Tile;
import settings.Settings;
import ui.*;
import ui.clickable.UIToolToggle;

import java.awt.*;

/**
 * @author Simon Jern
 * Menu in the world editor to display placeable tiles.
 */
public class UITileMenu extends HorizontalContainer {

    public UITileMenu(ContentManager content) {
        super();
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));

        UITabContainer tileContainer = new UITabContainer();
        tileContainer.addTab("grass",
                getTileSheet(content, "grass1"),
                getTileSheet(content, "grass2"),
                getTileSheet(content, "dirt"));

        tileContainer.addTab("lakes",
                getTileSheet(content, "lake1"),
                getTileSheet(content, "lake2"),
                getTileSheet(content, "dirt"));

        tileContainer.addTab("mountains",
                getTileSheet(content, "mountain1"),
                getTileSheet(content, "mountain2"),
                getTileSheet(content, "mountain3"),
                getTileSheet(content, "mountain4"),
                getTileSheet(content, "dirt"));

        addComponent(tileContainer);
    }

    private UIContainer getTileSheet(ContentManager content, String tileSheet) {
        UIContainer main = new HorizontalContainer();
        main.setMargin(new Spacing(0));
        Tile tile = new Tile(content, tileSheet);

        int tilesX = tile.getTileSheet().getWidth(null) / Settings.getSpriteSize();
        int tilesY = tile.getTileSheet().getHeight(null) / Settings.getSpriteSize();

        for(int x = 0; x < tilesX; x++){
            UIContainer column = new VerticalContainer();
            column.setMargin(new Spacing(0));
            column.setPadding(new Spacing(0));

            for(int y = 0; y < tilesY; y++){
                Tile indexedTile = tile.getCopy();
                indexedTile.setTileIndex(x * tilesX + y);
                UIToolToggle toggle = new UIToolToggle(indexedTile.getSprite(), new TilePlacer(indexedTile), 16, 16);
                column.addComponent(toggle);
            }

            main.addComponent(column);
        }

        return main;
    }
}
