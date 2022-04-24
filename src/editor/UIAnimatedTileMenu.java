package editor;

import content.ContentManager;
import input.mouse.action.TilePlacer;
import map.AnimatedTile;
import ui.*;
import ui.clickable.UIToolToggle;

import java.awt.*;

/**
 * @author Simon Jern
 * Menu in the world editor to display placeable tiles.
 */
public class UIAnimatedTileMenu extends HorizontalContainer {

    public UIAnimatedTileMenu(ContentManager content) {
        super();
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));

        UITabContainer tileContainer = new UITabContainer();
        tileContainer.addTab("water/mountain1",
                getColumn(content, "water_left_bottom1"));

        addComponent(tileContainer);
    }

    private UIContainer getColumn(ContentManager content, String... tileSheets) {
        UIContainer column = new VerticalContainer();
        column.setMargin(new Spacing(0));
        column.setPadding(new Spacing(0));

        for(String tileSheet : tileSheets){
            AnimatedTile tile = new AnimatedTile(content, tileSheet);
            AnimatedTile indexedTile = tile.getCopy();
            indexedTile.setAnimationManager(tile.getAnimationManager().getCopy(tile.getTileSheetName()));
            indexedTile.setTileIndex(0);
            UIToolToggle toggle = new UIToolToggle(indexedTile.getSprite(), new TilePlacer(indexedTile), 16, 16);
            column.addComponent(toggle);
        }

        return column;
    }
}
