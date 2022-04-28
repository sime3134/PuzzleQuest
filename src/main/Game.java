package main;

import IO.MapIO;
import audio.AudioPlayer;
import content.ContentManager;
import controller.GameController;
import core.CollisionBox;
import core.Time;
import display.Camera;
import display.Debug;
import display.GameFrame;
import entity.GameObject;
import entity.Player;
import entity.Scenery;
import main.state.*;
import map.GameMap;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Simon Jern, Johan Salomonsson
 * Main class for the game controlling the current state of the game.
 */
public class Game {

    private final ContentManager content;

    private final GameFrame gameFrame;

    private final Debug debug;
    GameController gameController;

    private final AudioPlayer audioPlayer;

    private final Camera camera;
    private final Time time;

    private State currentState;
    private State lastState;
    private GameState gameState;
    private EditorState editorState;

    private GameMap currentMap;

    private final List<GameObject> gameObjects;

    public State getCurrentState() {
        return currentState;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public GameState getGameState() {
        return gameState;
    }

    public ContentManager getContent() {
        return content;
    }

    public Camera getCamera() {
        return camera;
    }

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public void toggleAudio() {
        if(Settings.getAudioOn().getValue()){
            audioPlayer.playLastMusic();
        }else{
            audioPlayer.clear();
        }
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

    public Time getTime() {
        return time;
    }

    public void setFullScreen() {
        gameFrame.toggleFullScreen();
    }

    public Game(){
        gameObjects = new ArrayList<>();
        content = new ContentManager();
        camera = new Camera();
        time = new Time();
        content.loadContent();
        loadMap("main_menu_map", false);
        currentState = new MainMenuState(this);
        lastState = currentState;
        gameController = new GameController();
        debug = new Debug(currentState);
        audioPlayer = new AudioPlayer();
        audioPlayer.playMusic("menu.wav");
        gameFrame = new GameFrame(this);
    }

    public void update() {
        gameController.update(this);
        camera.update(currentMap);
        currentMap.update(camera);
        currentState.update(this);
        updateObjectsDrawOrder();
        if(!(currentState instanceof PauseMenuState)) {
            gameObjects.forEach(gameObject -> gameObject.update(this));
        }
        time.update();
        debug.update(this);
        audioPlayer.update();
    }

    public void draw(Graphics g) {
        currentMap.draw(g, camera);
        gameObjects.stream()
                .filter(gameObject -> camera.isObjectInView(gameObject))
                .forEach(gameObject -> renderGameObject(g, camera, gameObject));
        currentState.draw(g);
        debug.draw(this, g);
    }

    private void renderGameObject(Graphics g, Camera camera, GameObject gameObject) {
        gameObject.getAttachments().forEach(attachment -> renderGameObject(g, camera, attachment));
        gameObject.draw(g, camera);
    }

    public void resumeGame() {
        lastState = currentState;
        this.currentState = gameState;
    }

    public void saveGame() {
        //TODO: implement
    }

    public void loadGame() {
        //TODO: implement
    }

    public void goToMainMenu() {
        lastState = currentState;
        this.currentState = new MainMenuState(this);
        Settings.reset();
        audioPlayer.playMusic("menu.wav");
    }

    public void pauseGame() {
        lastState = currentState;
        this.currentState = new PauseMenuState(this);
    }

    public void goToSettingsMenu() {
        lastState = currentState;
        this.currentState = new SettingsMenuState(this);
    }

    public void startNewGame() {
        lastState = currentState;
        gameState = new GameState(this);
        currentState = gameState;
        audioPlayer.playMusic("suburbs.wav");
    }

    public void enterUsername() {
        lastState = currentState;
        this.currentState = new SetupNameState(this);
    }

    public void goToWorldEditor() {
        lastState = currentState;
        if(editorState == null) {
            editorState = new EditorState(this);
        }else{
            Settings.toggleDebugMode();
        }

        currentState = editorState;
    }

    public void goToLastState() {
        currentState = lastState;
    }

    public void showDialog(String text) {
        System.out.println(text);
    }

    public void loadMap(String nameOrPath, boolean path) {
        gameObjects.removeIf(gameObject -> !(gameObject instanceof Player));
        if(path) {
            currentMap = MapIO.loadFromPath(content, nameOrPath);
        }else {
            currentMap = MapIO.loadFromName(content, nameOrPath);
        }
        gameObjects.addAll(currentMap.getSceneryList());
    }

    public void saveMap(String filePath) {
        currentMap.setSceneryList(getGameObjectsOfClass(Scenery.class));
        MapIO.save(currentMap, filePath);
    }


    public void createNewMap(int width, int height, ContentManager content) {
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

    /**
     * Updates in which order gameObjects should be rendered on the screen to give a correct
     * feeling of depth.
     */
    private void updateObjectsDrawOrder() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(
                gameObject -> gameObject.getRenderOrderComparisonYPosition()));
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
