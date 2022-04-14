package editor;

import content.ContentManager;
import core.Vector2D;
import entity.Scenery;
import input.mouse.action.SceneryPlacer;
import ui.HorizontalContainer;
import ui.clickable.UIToolToggle;

import java.awt.*;

public class UISceneryMenu extends HorizontalContainer {
    public UISceneryMenu(ContentManager content){
        backgroundColor = Color.DARK_GRAY;

        addComponent(new UIToolToggle(content.getImage("tree1"),
                new SceneryPlacer(
                        new Scenery(
                        "tree1",
                        127, 77,
                        10, 78,
                        115, 40,
                        60,
                        new Vector2D(15, 80),
                        false,
                        content
        )), 74, 74));

        addComponent(new UIToolToggle(content.getImage("mini_tree1"),
                new SceneryPlacer(
                        new Scenery(
                        "mini_tree1",
                        50, 50,
                        0, 50,
                        40, 27,
                        20,
                        new Vector2D(5, 63),
                        false,
                        content
        )), 37, 74));
    }
}
