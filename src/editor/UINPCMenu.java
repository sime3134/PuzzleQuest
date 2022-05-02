package editor;

import content.ContentManager;
import controller.NPCController;
import entity.NPC;
import input.mouse.action.NPCPlacer;
import ui.*;
import ui.clickable.UIToolToggle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern, Johan Salomonsson
 * Menu in the world editor that displays placeable scenery.
 */
public class UINPCMenu extends HorizontalContainer {
    public UINPCMenu(ContentManager content) {
        super();
        backgroundColor = Color.DARK_GRAY;

        UITabContainer tabContainer = new UITabContainer();
        tabContainer.addTab("Humans", getHumansContainer(content));
        addComponent(tabContainer);
    }

    private UIContainer getHumansContainer(ContentManager content) {
        List<NPC> NPCToAdd = new ArrayList<>();

        NPCToAdd.add(new NPC(new NPCController(), content.getSpriteSet("villager0"), "villager0"));

        NPCToAdd.add(new NPC(new NPCController(), content.getSpriteSet("villager1"), "villager1"));

        NPCToAdd.add(new NPC(new NPCController(), content.getSpriteSet("villager2"), "villager2"));

        NPCToAdd.add(new NPC(new NPCController(), content.getSpriteSet("villager3"), "villager3"));

        NPCToAdd.add(new NPC(new NPCController(), content.getSpriteSet("villager4"), "villager4"));

        UIContainer main = new HorizontalContainer();
        main.setMargin(new Spacing(0));

        UIContainer column = new VerticalContainer();
        column.setMargin(new Spacing(0));
        column.setPadding(new Spacing(0));

        main.addComponent(column);

        for (NPC npc : NPCToAdd) {
            column.addComponent(new UIToolToggle(npc.getSprite(),
                    new NPCPlacer(npc), npc.getWidth(), npc.getHeight()));

            if (column.getHeight() > 140) {
                column = new VerticalContainer();
                main.addComponent(column);
            }
        }

        return main;
    }
}
