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

    public Debug(State state){
    }

    public void update(State state){
        if(Settings.isDebugMode()) {
        }
    }

    public void draw(State state, Graphics g){
        if(Settings.isDebugMode() || Settings.getShouldRenderCollisionBox().getValue()){
            drawCollisionBoxes(state, g);
        }
    }

    private void drawCollisionBoxes(State state, Graphics g) {
        Camera camera = state.getCamera();
        state.getGameObjects().stream()
                .filter(gameObject -> camera.isObjectInView(gameObject)
                        && !(gameObject instanceof SelectionCircle))
                .map(gameObject -> gameObject.getCollisionBox())
                .forEach(collisionBox -> collisionBox.draw(g, camera));
    }
}
