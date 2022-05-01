package main;

import IO.MapIO;
import IO.ProgressIO;
import audio.AudioPlayer;
import content.ContentManager;
import controller.GameController;
import core.CollisionBox;
import core.Time;
import core.Vector2D;
import display.Camera;
import display.Debug;
import display.GameFrame;
import editor.UISettingsContainer;
import entity.GameObject;
import entity.Player;
import entity.Scenery;
import main.state.*;
import map.GameMap;
import map.MapManager;
import settings.Settings;
import ui.UIContainer;

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
    private final GameController gameController;

    private final AudioPlayer audioPlayer;

    private final Camera camera;
    private final Time time;

    private final StateManager stateManager;
    private final MapManager maps;

    private final UIContainer debugSettingsContainer;

    //region Getters & Setters (Click to view)

    public State getCurrentState() {
        return stateManager.getCurrentState();
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public GameState getGameState() {
        return stateManager.getGameState();
    }

    public ContentManager getContent() {
        return content;
    }

    public Camera getCamera() {
        return camera;
    }

    public GameMap getCurrentMap() {
        return maps.getCurrent();
    }

    public void toggleAudio() {
        if(Settings.getAudioOn().getValue()){
            audioPlayer.playLastMusic();
        }else{
            audioPlayer.clear();
        }
    }

    public List<GameObject> getCollidingBoxes(CollisionBox box) {
        return maps.getCurrent().getGameObjects().stream()
                .filter(other -> other.collidingWith(box))
                .toList();
    }

    public <T extends GameObject> List<T> getGameObjectsOfClass(Class<T> clazz) {
        return maps.getCurrent().getGameObjects().stream()
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

    public List<GameObject> getGameObjects() {
        return maps.getCurrent().getGameObjects();
    }

    public MapManager getMapManager() {
        return maps;
    }

    //endregion

    public Game(){
        content = new ContentManager();
        content.loadContent();
        stateManager = new StateManager(this);
        stateManager.goToMainMenuState();
        maps = new MapManager();
        maps.loadAll(content, "/maps");
        maps.initialize(this);
        camera = new Camera();
        time = new Time();
        gameController = new GameController();
        debug = new Debug();
        audioPlayer = new AudioPlayer();
        audioPlayer.playMusic("menu.wav");
        goToMainMenu();
        debugSettingsContainer = new UISettingsContainer(maps.getCurrent(), content);
        gameFrame = new GameFrame(this);
    }

    public void update() {
        gameController.update(this);
        camera.update(maps.getCurrent());
        maps.update(this);
        stateManager.update(this);
        time.update();
        audioPlayer.update();

        if(Settings.isDebugMode()){
            debug.update(this);
            debugSettingsContainer.update(this);
        }
    }

    public void draw(Graphics g) {
        maps.draw(g, camera);
        stateManager.draw(g);

        if(Settings.isDebugMode()){
            debug.draw(this, g);
            debugSettingsContainer.draw(g);
        }
    }

    public void resumeGame() {
        stateManager.goToGameState();
    }

    public void saveGame() {
        ProgressIO.save(stateManager.getGameState(),"./save_file.txt");
    }

    public void loadGame() {
        //TODO: implement
    }

    public void goToMainMenu() {
        loadMap("main_menu_map");
        stateManager.goToMainMenuState();
        Settings.reset();
        audioPlayer.playMusic("menu.wav");
    }

    public void pauseGame() {
        stateManager.goToPauseState();
    }

    public void goToSettingsMenu() {
        stateManager.goToSettingsState();
    }

    public void startNewGame() {
        stateManager.newGameState(this);
        loadMap(stateManager.getGameState().getWorldMap()[0][0]);
        audioPlayer.playMusic("suburbs.wav");
    }

    public void enterUsername() {
        stateManager.goToSetupNameState();
    }

    public void goToWorldEditor() {
        stateManager.goToEditorState();
        createNewMap(64, 64, content);
        Settings.setDebugMode(true);
    }

    public void goToLastState() {
        stateManager.goToLastState();
    }

    public void showDialog(String text) {
        System.out.println(text);
    }

    public void loadMapFromPath(String path) {
        maps.setCurrent(MapIO.loadFromPath(content, path));
    }

    public void loadMap(String name) {
        maps.setCurrent(maps.getByName(name));
    }

    public void saveMap(String filePath) {
        maps.getCurrent().setSceneryList(getGameObjectsOfClass(Scenery.class));
        MapIO.save(maps.getCurrent(), filePath);
    }


    public void createNewMap(int width, int height, ContentManager content) {
        maps.setCurrent(new GameMap(width, height, content));
    }

    public void spawn(GameObject gameObject) {
        maps.getCurrent().addGameObject(gameObject);
    }

    public void despawn(GameObject gameObject) {
        maps.getCurrent().removeGameObject(gameObject);
    }
}
