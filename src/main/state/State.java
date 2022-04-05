package main.state;

import content.ContentManager;
import core.CollisionBox;
import core.Time;
import display.Camera;
import entity.GameObject;
import input.Input;
import input.mouse.MouseHandler;
import main.Game;
import map.GameMap;
import IO.MapIO;
import ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Simon Jern
 * Base class for the several states in the game. Ex: in-game, pause or menu states.
 */
public abstract class State {

    protected List<UIContainer> uiContainers;

    protected Camera camera;
    protected ContentManager content;
    protected Input input;
    protected MouseHandler mouseHandler;
    protected GameMap currentMap;
    protected Time time;
    protected final List<GameObject> gameObjects;

    //region Getts and Setters (click to view)

    public Camera getCamera() {
        return camera;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    protected void setCurrentMap(GameMap newMap) {
        this.currentMap = newMap;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public Time getTime() {
        return time;
    }
    //endregion

    protected State(){
        input = Input.getInstance();
        mouseHandler = new MouseHandler();
        content = new ContentManager();
        content.loadContent();
        camera = new Camera();
        time = new Time();
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
    }

    public void update(Game game){
        uiContainers.forEach(uiContainer -> uiContainer.update(this));
        camera.update(currentMap);

        time.update();
        mouseHandler.update(game);

        updateObjectsDrawOrder();
        gameObjects.forEach(gameObject -> gameObject.update(this));
    }

    public void draw(Graphics g){
        currentMap.draw(g, camera);
        gameObjects.stream()
                .filter(gameObject -> camera.isObjectInView(gameObject))
                .forEach(gameObject -> gameObject.draw(g, camera));
        mouseHandler.draw(g);
        uiContainers.forEach(uiContainer -> uiContainer.draw(g));
    }

    public List<GameObject> getCollidingBoxes(CollisionBox box) {
        return gameObjects.stream()
                .filter(other -> other.collidingWith(box))
                .toList();
    }

    private void updateObjectsDrawOrder() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }

    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> clazz) {
        return gameObjects.stream()
                .filter(clazz::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    protected void loadMap() {
        currentMap = MapIO.load(content);
    }
}
