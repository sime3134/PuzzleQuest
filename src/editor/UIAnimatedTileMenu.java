package editor;

import content.ContentManager;
import input.mouse.action.TilePlacer;
import map.AnimatedTile;
import ui.*;
import ui.clickable.UIToolToggle;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import ui.containers.UITabContainer;
import ui.containers.VerticalContainer;

import java.awt.*;

/**
 * @author Simon Jern
 * Menu in the world editor to display placeable animated tiles.
 */
public class UIAnimatedTileMenu extends HorizontalContainer {

    public UIAnimatedTileMenu(ContentManager content) {
        super();
        setBackgroundColor(Color.DARK_GRAY);
        setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.BOTTOM));

        UITabContainer tileContainer = new UITabContainer();
        tileContainer.addTab("water/mountain1",
                getColumn(content,
                        "water_left_top1",
                        "water_left_middle1",
                        "water_left_bottom1"),
                getColumn(content,
                        "water_middle_top1",
                        "water_middle_bottom1"),
                getColumn(content,
                        "water_right_top1",
                        "water_right_middle1",
                        "water_right_bottom1"),
                getColumn(content,
                        "water_inner_left_top1",
                        "water_inner_left_bottom1"),
                getColumn(content,
                        "water_inner_right_top1",
                        "water_inner_right_bottom1"),
                getColumn(content,
                        "water_brown_top_left_5",
                        "water_brown_middle_left_5",
                        "water_brown_bottom_left_5"),
                getColumn(content,
                        "water_brown_bottom_middle_5"),
                getColumn(content,
                        "water_brown_top_right_5",
                        "water_brown_middle_right_5",
                        "water_brown_bottom_right_5"),
                getColumn(content,
                        "water_brown_inner_top_left",
                        "water_brown_inner_bottom_left"),
                getColumn(content,
                        "water_brown_inner_top_right",
                        "water_brown_inner_bottom_right"),
                getColumn(content,
                        "bridge1",
                        "bridge2_1",
                        "bridge2_2",
                        "bridge2_3"),
                getColumn(content,
                        "water1",
                        "water2"),
                getColumn(content,
                        "fish1",
                        "fish2"),
                getColumn(content,
                        "stone1",
                        "stone2",
                        "stone3",
                        "stone4"),
                getColumn(content, "fireplace"));

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
