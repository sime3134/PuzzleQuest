package display;

import entity.NPC;
import entity.SelectionCircle;
import main.state.State;
import settings.Settings;
import ui.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a debugger to draw and update components that do not have an importance in the actual game.
 */
public class Debug {

    private final List<UIContainer> uiContainers;

    public Debug(State state){

        uiContainers = new ArrayList<>();

        UIContainer container = new VerticalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP));
        container.setBackgroundColor(new Color(0,0,0,0));
        uiContainers.add(container);

        state.getGameObjects().stream()
                .filter(gameObject -> !(gameObject instanceof SelectionCircle))
                .forEach(gameObject -> {
            UIContainer entityContainer = new RelativeContainer(gameObject, state.getCamera());
            entityContainer.setBackgroundColor(Color.lightGray);
            UIText playerText;
            if(gameObject instanceof NPC npc) {
                playerText = new RelativeUIText(npc.getBrain().getCurrentAIState().getClass().getSimpleName(), npc);
            }
            else{
                playerText = new UIText(gameObject.getClass().getSimpleName());
            }
            playerText.setFontSize(12);
            entityContainer.addComponent(playerText);
            uiContainers.add(entityContainer);
        });
    }

    public void update(State state){
        uiContainers.forEach(container -> container.update(state));
    }

    public void draw(State state, Graphics g){
        Camera camera = state.getCamera();
        state.getGameObjects().stream()
                .filter(gameObject -> camera.isObjectInView(gameObject)
                        && !(gameObject instanceof SelectionCircle))
                .map(gameObject -> gameObject.getCollisionBox())
                .forEach(collisionBox -> collisionBox.draw(g, camera));
        uiContainers.forEach(container -> container.draw(g));
    }
}
