package main.state;

import content.ContentManager;
import core.CollisionBox;
import display.Camera;
import entity.GameObject;
import input.Input;
import map.GameMap;
import ui.UIContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for the several states in the game. Ex: in-game, pause or menu states.
 */
public abstract class State {

    protected List<UIContainer> uiContainers;

    protected Camera camera;
    protected ContentManager content;
    protected Input input;
    protected GameMap currentMap;
    protected final List<GameObject> gameObjects;

    public Camera getCamera() {
        return camera;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    protected State(){
        input = Input.getInstance();
        content = new ContentManager();
        content.loadContent();
        camera = new Camera();
        gameObjects = new ArrayList<>();
        uiContainers = new ArrayList<>();
    }

    public void update(){
        uiContainers.forEach(uiContainer -> uiContainer.update(this));
        camera.update(currentMap);
        handleMouseInput();
    }

    private void handleMouseInput() {

        if(input.isMouseClicked()){
            System.out.println("MOUSE CLICKED AT POSITION: " + input.getMousePosition().getX() + " x " + input.getMousePosition().getY());
        }

        input.clearMouseClick();
    }

    public abstract void draw(Graphics g);

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
}
