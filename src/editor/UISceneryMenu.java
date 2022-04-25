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
 * @author Simon Jern, Johan Salomonsson
 * Menu in the world editor that displays placeable scenery.
 */
public class UISceneryMenu extends HorizontalContainer {
    public UISceneryMenu(ContentManager content) {
        super();
        backgroundColor = Color.DARK_GRAY;

        UITabContainer tabContainer = new UITabContainer();
        tabContainer.addTab("Nature", getNatureSceneryContainer(content));
        tabContainer.addTab("Man Made", getManMadeSceneryContainer(content));
        tabContainer.addTab("Doors/Widow", getDoorAndWindowSceneryContainer(content));
        tabContainer.addTab("Sign/Symbols", getSignsSymbolContainer(content));
        tabContainer.addTab("Buildings", getBuildingSymbolContainer(content));
        addComponent(tabContainer);
    }

    private UIContainer getBuildingSymbolContainer(ContentManager content) {
        List<Scenery> sceneryToAdd = new ArrayList<>();

        sceneryToAdd.add(new Scenery(
                "house",
                288, 200,
                new Vector2D(0, 160),
                160,
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

        sceneryToAdd.add(new Scenery(
                "something_green",
                36,48,
                new Vector2D(0,0),
                60,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "something_green2",
                36,48,
                new Vector2D(0,0),
                60,
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


    private UIContainer getManMadeSceneryContainer(ContentManager content) {
        List<Scenery> sceneryToAdd = new ArrayList<>();

        sceneryToAdd.add(new Scenery(
                "barrel",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bench",
                78, 60,
                new Vector2D(0, 0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bench2",
                36, 96,
                new Vector2D(0, 0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bench3",
                36, 96,
                new Vector2D(0, 0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bench4",
                78, 42,
                new Vector2D(0, 0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "board",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "board2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bucket",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "bucket_with_water",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "chest",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "chest_open",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "chest2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "chest2_open",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "chest3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "chest3_open",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "double_pole",
                36,144,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "double_pole2",
                36,144,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "empty_well",
                96,82,
                new Vector2D(0,14),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "well",
                96,82,
                new Vector2D(0,14),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "well_with_roof",
                130,74,
                new Vector2D(4,70),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "fire",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "fire_wood",
                42,24,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "headstone",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "headstone2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "large_pole",
                18,144,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "large_pole2",
                18,144,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "light",
                30,81,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "lit_light",
                30,81,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "torch",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "lit_torch",
                24,42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "not_sure_what_this_is",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "opening",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "opening1",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "opening2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pot",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pot2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rip",
                42, 49,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rip2",
                42, 49,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "rip2",
                45, 33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_pole",
                18, 117,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_pole2",
                18, 117,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "statue",
                48, 93,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "statue2",
                48, 96,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "table",
                48, 144,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "table2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "table3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "table4",
                144, 48,
                new Vector2D(0,0),
                20,
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

    private UIContainer getDoorAndWindowSceneryContainer(ContentManager content) {
        List<Scenery> sceneryToAdd = new ArrayList<>();

        sceneryToAdd.add(new Scenery(
                "blue_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_door3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_open_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "blue_open_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "door3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "open_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "open_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orangeish_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orangeish_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orangeish_door3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orangeish_open_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "orangeish_open_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pink_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pink_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pink_door3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pink_open_door",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "pink_open_door2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window5",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window6",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window7",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window8",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window9",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window10",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window11",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window12",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window13",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window14",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window15",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window16",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window17",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window18",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window19",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_window20",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window1",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window2",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window3",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window4",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window5",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window6",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window7",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window8",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window9",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window10",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window11",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window12",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window13",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window14",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window15",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window16",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window17",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window18",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window19",
                51,33,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "window20",
                51,33,
                new Vector2D(0,0),
                20,
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

    private UIContainer getSignsSymbolContainer(ContentManager content) {
        List<Scenery> sceneryToAdd = new ArrayList<>();

        sceneryToAdd.add(new Scenery(
                "blue_banner",
                48, 87,
                new Vector2D(0, 0),
                20,
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "green_banner",
                48, 87,
                new Vector2D(0, 0),
                20,
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "idk",
                48,30,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "post_sign",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "post_sign2",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "post_sign3",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "post_sign4",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "post_sign5",
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "purple_banner",
                48, 87,
                new Vector2D(0,0),
                20,
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "red_yellow_banner",
                48, 87,
                new Vector2D(0,0),
                20,
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "sign",
                144, 42,
                new Vector2D(0,0),
                20,
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "sign2",
                144, 42,
                new Vector2D(0,0),
                20,
                true,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_sign",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "small_sign2",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol2",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol3",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol4",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol5",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol6",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol7",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol8",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol9",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol10",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol11",
                48, 42,
                new Vector2D(0,0),
                20,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "symbol12",
                48, 42,
                new Vector2D(0,0),
                20,
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
