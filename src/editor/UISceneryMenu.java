package editor;

import content.ContentManager;
import core.Vector2D;
import entity.Scenery;
import input.mouse.action.SceneryPlacer;
import ui.HorizontalContainer;
import ui.clickable.UIToolToggle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UISceneryMenu extends HorizontalContainer {
    public UISceneryMenu(ContentManager content){
        backgroundColor = Color.DARK_GRAY;

        List<Scenery> sceneryToAdd = new ArrayList<>();

        sceneryToAdd.add(new Scenery(
                "tree1",
                115, 40,
                new Vector2D(5, 80),
                60,
                false,
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
                "stump",
                48, 30,
                new Vector2D(0, 10),
                0,
                false,
                content
        ));

        sceneryToAdd.add(new Scenery(
                "3_brown_shrooms",
                true,
                content
        ));

        sceneryToAdd.forEach(scenery -> {
            addComponent(new UIToolToggle(content.getImage(scenery.getName()),
                    new SceneryPlacer(scenery), scenery.getWidth() / 2, scenery.getHeight() / 2));
        });
    }
}
