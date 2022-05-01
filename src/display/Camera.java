package display;

import core.CollisionBox;
import core.Vector2D;
import entity.GameObject;
import map.GameMap;
import settings.Settings;

import java.awt.*;
import java.util.Optional;

/**
 *
 * @author Simon Jern
 * Implements a camera that can be used to follow game objects or restrict what is drawn to the screen etc.
 */
public class Camera {

    private Vector2D position;

    private Rectangle currentView;

    private Optional<GameObject> objectInFocus;

    private GameObject lastFocusedObject;

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    private void setCurrentView() {
        currentView = new Rectangle(
                position.intX(),
                position.intY(),
                Settings.getScreenWidth(),
                Settings.getScreenHeight());
    }

    public Camera() {
        position = new Vector2D(0,0);
        this.objectInFocus = Optional.empty();
        lastFocusedObject = null;
        setCurrentView();
    }

    public void focusOn(GameObject object){
        objectInFocus = Optional.of(object);
    }

    public void removeFocus(){
        if(objectInFocus.isPresent()) {
            lastFocusedObject = objectInFocus.get();
            objectInFocus = Optional.empty();
        }
    }

    public void resetLastFocus() {
        if(lastFocusedObject != null) {
            objectInFocus = Optional.of(lastFocusedObject);
        }
    }

    public void centerOnMap(GameMap map){
        Vector2D centerPosition = new Vector2D(map.getWidth() / 2f, map.getHeight() / 2f);
        centerPosition.subtract(new Vector2D(Settings.getScreenWidth() / 2f, Settings.getScreenHeight() / 2f));

        setPosition(centerPosition);
    }

    public void update(GameMap currentMap){
        if(objectInFocus.isPresent()){
            Vector2D objectPosition = objectInFocus.get().getPosition();

            position.setX(objectPosition.getX() - Settings.getScreenWidth() / 2f);
            position.setY(objectPosition.getY() - Settings.getScreenHeight() / 2f);

            clamp(currentMap);
        }
        setCurrentView();
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
        if(position.getX() + Settings.getScreenWidth() > currentMap.getWidth()){
            position.setX(currentMap.getWidth() - (double)Settings.getScreenWidth());
        }
        if(position.getY() + Settings.getScreenHeight() > currentMap.getHeight()){
            position.setY(currentMap.getHeight() - (double)Settings.getScreenHeight());
        }
    }

    /**
     * Checks if an object is in view.
     */
    public boolean isObjectInView(GameObject gameObject) {
        return currentView.intersects(
                CollisionBox.of(gameObject.getPosition(), gameObject.getWidth(), gameObject.getHeight()).getBounds()
        );
    }
}
