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
                "small_tree1",
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
                "tree1",
                113, 40,
                new Vector2D(0, 80),
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

        for(int i = 0; i < sceneryToAdd.size(); i++){
            column.addComponent(new UIToolToggle(content.getImage(sceneryToAdd.get(i).getName()),
                    new SceneryPlacer(sceneryToAdd.get(i)), sceneryToAdd.get(i).getWidth() / 2, sceneryToAdd.get(i).getHeight() / 2));

            if(i % 2 == 1){
                column = new VerticalContainer();

                main.addComponent(column);
            }
        }

        return main;
    }
}
