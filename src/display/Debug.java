package display;

import entity.SelectionCircle;
import main.Game;
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

    public Debug(){
        uiContainers = new ArrayList<>();
    }

    public void update(Game game){
        uiContainers.forEach(container -> container.update(game));
    }

    public void draw(Game game, Graphics g){
        if(Settings.getRenderCollisionBox().get()){
            drawCollisionBoxes(game, g);
        }
        uiContainers.forEach(container -> container.draw(g));
    }

    private void drawCollisionBoxes(Game game, Graphics g) {
        game.getGameObjects().stream()
                .filter(gameObject -> game.getCamera().isObjectInView(gameObject)
                        && !(gameObject instanceof SelectionCircle))
                .map(gameObject -> gameObject.getStaticCollisionBox())
                .forEach(collisionBox -> collisionBox.draw(g, game.getCamera()));
    }
}
