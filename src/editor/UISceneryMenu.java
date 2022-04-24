package editor;

import content.ContentManager;
import core.Vector2D;
import entity.Scenery;
import input.mouse.action.SceneryPlacer;
import ui.*;
import ui.clickable.UIToolToggle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Menu in the world editor that displays placeable scenery.
 */
public class UISceneryMenu extends HorizontalContainer {
    public UISceneryMenu(ContentManager content){
        super();
        backgroundColor = Color.DARK_GRAY;

        UITabContainer tabContainer = new UITabContainer();
        tabContainer.addTab("Nature", getNatureSceneryContainer(content));
        addComponent(tabContainer);
    }

    private UIContainer getNatureSceneryContainer(ContentManager content) {
        List<Scenery> sceneryToAdd = new ArrayList<>();

        sceneryToAdd.add(new Scenery(
                "3_brown_shrooms",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "3_purple_shrooms",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_flower_pot",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_fruit",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_fruit2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "brown_mushroom",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bush",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bush2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bush2_with_fruit",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bush_with_fruit",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "flower_box",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "flower_pot",
                false,
                content
        ));
        sceneryToAdd.add(new Scenery(
                "flower_pot2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_plant",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_plant2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_plant3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_plant4",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_stuff",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_stuff2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orange_flowers",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orange_fruit",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orange_fruit2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "purple_flower",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "purple_flower_pot",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "purple_fruit",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "purple_fruit2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "purpleish_flower_pot",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "red_brown_mushroom",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "red_brown_mushroom2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "red_flower_pot",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "red_fruit",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "red_fruit2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks2_v2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks2_v3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks2_v4",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks3_v2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks3_v3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks3_v4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks4_v2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks4_v3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks4_v4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks5",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks5_v2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks5_v3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks5_v4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks6",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks6_v2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks6_v3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks6_v4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks_v2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks_v3",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rocks_v4",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "seedling",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "seedling2",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_tree1",
                40, 27,
                new Vector2D(5, 63),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_tree2",
                40, 27,
                new Vector2D(5, 63),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_tree2_with_fruit",
                40, 27,
                new Vector2D(5, 63),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_tree_with_fruit",
                40, 27,
                new Vector2D(5, 63),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "stump",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "stump2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "tree1",
                113, 40,
                new Vector2D(0, 80),
                60,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "tree1_v2",
                113, 40,
                new Vector2D(0, 80),
                60,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "tree2",
                113, 40,
                new Vector2D(0, 80),
                60,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "tree2_v2",
                113, 40,
                new Vector2D(0, 80),
                60,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "vines_brown",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "vines_brown_green",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "vines_brown_green_fruit",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "vines_green",
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "wood",
                false,
                content
        ));

        UIContainer main = new HorizontalContainer();
        main.setMargin(new Spacing(0));

        UIContainer column = new VerticalContainer();
        column.setMargin(new Spacing(0));
        column.setPadding(new Spacing(0));

        main.addComponent(column);

        for (Scenery scenery : sceneryToAdd) {
            column.addComponent(new UIToolToggle(content.getImage(scenery.getName()),
                    new SceneryPlacer(scenery), scenery.getWidth() / 2, scenery.getHeight() / 2));

            if (column.getHeight() > 140) {
                column = new VerticalContainer();
                main.addComponent(column);
            }
        }

        return main;
    }
}
