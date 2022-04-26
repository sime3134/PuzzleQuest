package main.state;

import IO.MapIO;
import content.ContentManager;
import core.CollisionBox;
import core.Time;
import display.Camera;
import editor.UISettingsContainer;
import entity.GameObject;
import entity.Scenery;
import input.Input;
import input.mouse.MouseHandler;
import main.Game;
import map.GameMap;
import settings.Settings;
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
    private UIContainer debugSettingsContainer;

    //region Getters and Setters (click to view)

    public Camera getCamera() {
        return camera;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public MouseHandler getMouseHandler() {
        return mouseHandler;
    }

    public Time getTime() {
        return time;
    }

    public ContentManager getContent() {
        return content;
    }

    public List<GameObject> getCollidingBoxes(CollisionBox box) {
        return gameObjects.stream()
                .filter(other -> other.collidingWith(box))
                .toList();
    }

    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> clazz) {
        return gameObjects.stream()
                .filter(clazz::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    //endregion

    protected State() {
        input = Input.getInstance();
        mouseHandler = new MouseHandler();
        content = new ContentManager();
        content.loadContent();
        camera = new Camera();
        time = new Time();
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
        loadMap("main_menu_map", false);
        setupUI();
    }

    protected void setupUI() {
        debugSettingsContainer = new UISettingsContainer(currentMap, content);
    }

    public void update(Game game) {
        currentMap.update(camera);
        uiContainers.forEach(uiContainer -> uiContainer.update(this));
        camera.update(currentMap);

        time.update();
        mouseHandler.update(game);

        updateObjectsDrawOrder();
        gameObjects.forEach(gameObject -> gameObject.update(this));

        if(Settings.isDebugMode()){
            debugSettingsContainer.update(this);
        }
    }

    public void draw(Graphics g) {
        currentMap.draw(g, camera, gameObjects);
        gameObjects.stream()
                .filter(gameObject -> camera.isObjectInView(gameObject))
                .forEach(gameObject -> renderGameObject(g, camera, gameObject));
        mouseHandler.draw(g);
        uiContainers.forEach(uiContainer -> uiContainer.draw(g));

        if(Settings.isDebugMode()){
            debugSettingsContainer.draw(g);
        }
    }

    protected void renderGameObject(Graphics g, Camera camera, GameObject gameObject) {
        gameObject.getAttachments().forEach(attachment -> renderGameObject(g, camera, attachment));
        gameObject.draw(g, camera);
    }

    /**
     * Updates in which order gameObjects should be rendered on the screen to give a correct
     * feeling of depth.
     */
    private void updateObjectsDrawOrder() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(
                gameObject -> gameObject.getRenderOrderComparisonYPosition()));
    }

    public void loadMap(String nameOrPath, boolean path) {
        //gameObjects.removeIf(gameObject -> !(gameObject instanceof Player));
        if(path) {
            currentMap = MapIO.loadFromPath(content, nameOrPath);
        }else {
            currentMap = MapIO.loadFromName(content, nameOrPath);
        }
        //gameObjects.addAll(currentMap.getSceneryList());
        camera.centerOnMap(currentMap);
    }

    public void saveMap(String filePath) {
        currentMap.setSceneryList(getGameObjectsOfClass(Scenery.class));
        MapIO.save(currentMap, filePath);
    }

    protected void createNewMap(int width, int height) {
        gameObjects.clear();
        currentMap = new GameMap(width, height, content);
    }

    public void spawn(GameObject gameObject) {
        gameObjects.add(gameObject);
        currentMap.getSceneryList().add((Scenery) gameObject);
    }

    public void despawn(GameObject gameObject) {
        gameObjects.remove(gameObject);
        currentMap.getSceneryList().remove((Scenery)gameObject);
    }

    public abstract void escapeButtonPressed(Game game);
}
