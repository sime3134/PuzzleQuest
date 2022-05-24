package ui.containers;

import core.Vector2D;
import display.Camera;
import entity.NPC;
import main.Game;
import ui.UIComponent;

import java.awt.*;

/**
 * @author Simon Jern
 * An UI container that follows a game object on the screen.
 */
public class RelativeContainer extends HorizontalContainer{

    private final NPC objectToFollow;
    private final Camera camera;

    private boolean shouldUpdate;

    public RelativeContainer(NPC objectToFollow, Camera camera){
        super();
        this.objectToFollow = objectToFollow;
        this.camera = camera;
        setBackgroundColor(Color.DARK_GRAY);
    }

    @Override
    public void calculatePosition(){
        if(objectToFollow != null && shouldUpdate) {
            this.absolutePosition = objectToFollow.getRenderPosition(camera);
            absolutePosition.subtract(new Vector2D((width / 4f) - 5, 20));
            calculateContentPosition();
        }
    }

    @Override
    public void draw(Game game, Graphics g) {
        if(visible && objectToFollow.getCurrentMapName().equals(game.getCurrentMap().getName())
                && !game.isShowBlackScreen()) {
            shouldUpdate = true;
            g.drawImage(
                    getSprite(),
                    absolutePosition.intX(),
                    absolutePosition.intY(),
                    null
            );

            for (UIComponent component : children) {
                component.draw(game, g);
            }
        }else{
            shouldUpdate = false;
        }
    }
}
