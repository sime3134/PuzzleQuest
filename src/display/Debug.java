package display;

import entity.SelectionCircle;
import main.state.State;
import settings.Settings;
import ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a debugger to draw and update components that do not have an importance in the actual game.
 */
public class Debug {

    protected List<UIContainer> uiContainers;

    public Debug(State state){
        uiContainers = new ArrayList<>();
    }

    public void update(State state){
        if(Settings.isDebugMode()) {
            uiContainers.forEach(container -> container.update(state));
        }
    }

    public void draw(State state, Graphics g){
        if(Settings.getRenderCollisionBox().getValue()){
            drawCollisionBoxes(state, g);
        }
        if(Settings.isDebugMode()) {
            uiContainers.forEach(container -> container.draw(g));
        }
    }

    private void drawCollisionBoxes(State state, Graphics g) {
        Camera camera = state.getCamera();
        state.getGameObjects().stream()
                .filter(gameObject -> camera.isObjectInView(gameObject)
                        && !(gameObject instanceof SelectionCircle))
                .map(gameObject -> gameObject.getStaticCollisionBox())
                .forEach(collisionBox -> collisionBox.draw(g, camera));
    }
}
