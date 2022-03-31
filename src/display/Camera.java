package display;

import core.Vector2D;
import entity.GameObject;
import map.GameMap;
import settings.GameSettings;

import java.awt.*;
import java.util.Optional;

/**
 * Implements a camera that can be used to follow game objects or restrict what is drawn to the screen etc.
 */
public class Camera {

    private final GameSettings settings = GameSettings.getInstance();
    private final Vector2D position;
    private final int windowWidth;
    private final int windowHeight;

    private Rectangle currentView;

    private Optional<GameObject> objectInFocus;

    public Vector2D getPosition() {
        return position;
    }

    private void setCurrentView() {
        currentView = new Rectangle(
                position.intX(),
                position.intY(),
                windowWidth + settings.getRenderMargin(),
                windowHeight + settings.getRenderMargin());
    }

    public Camera() {
        position = new Vector2D(0,0);
        this.windowWidth = settings.getScreenWidth();
        this.windowHeight = settings.getScreenHeight();
        setCurrentView();
    }

    public void focusOn(GameObject object){
        objectInFocus = Optional.of(object);
    }

    public void update(GameMap currentMap){
        if(objectInFocus.isPresent()){
            Vector2D objectPosition = objectInFocus.get().getPosition();

            position.setX(objectPosition.getX() - windowWidth / 2f);
            position.setY(objectPosition.getY() - windowHeight / 2f);

            clamp(currentMap);
            setCurrentView();
        }
    }

    /**
     * Makes sure the camera doesn't go outside the map.
     */
    private void clamp(GameMap currentMap) {
        if(position.getX() < 0){
            position.setX(0);
        }
        if(position.getY() < 0){
            position.setY(0);
        }
        if(position.getX() + windowWidth > currentMap.getWidth()){
            position.setX(currentMap.getWidth() - (double)windowWidth);
        }
        if(position.getY() + windowHeight > currentMap.getHeight()){
            position.setY(currentMap.getHeight() - (double)windowHeight);
        }
    }

    /**
     * Checks if an object is in view.
     */
    public boolean isObjectInView(GameObject gameObject) {
        return currentView.intersects(
            gameObject.getCollisionBox().getBounds()
            );
    }
}
