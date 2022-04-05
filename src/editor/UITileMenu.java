package editor;

import content.ContentManager;
import map.Tile;
import settings.Settings;
import ui.*;
import ui.clickable.UITileToggle;

import java.awt.*;

public class UITileMenu extends HorizontalContainer {

    public UITileMenu(ContentManager content) {
        super();
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));

        UITabContainer tileContainer = new UITabContainer();
        tileContainer.addTab("terrain", getTileSheet(content, "terrain"));
        tileContainer.addTab("terrain", getTileSheet(content, "terrain"));
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
                UITileToggle toggle = new UITileToggle(indexedTile);
                column.addComponent(toggle);
            }

            main.addComponent(column);
        }

        return main;
    }
}
