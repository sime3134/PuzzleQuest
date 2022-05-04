package main;

import IO.MapIO;
import IO.Persistable;
import IO.ProgressIO;
import audio.AudioPlayer;
import content.ContentManager;
import controller.GameController;
import controller.NPCController;
import core.CollisionBox;
import core.Time;
import core.Vector2D;
import display.Camera;
import display.Debug;
import display.GameFrame;
import editor.UISettingsContainer;
import entity.GameObject;
import entity.NPC;
import entity.Player;
import entity.Scenery;
import main.state.*;
import map.GameMap;
import map.MapManager;
import map.PathFinder;
import settings.Settings;
import ui.UIContainer;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Simon Jern, Johan Salomonsson
 * Main class for the game controlling the current state of the game.
 */
public class Game implements Persistable {

    private final ContentManager content;

    private final GameFrame gameFrame;

    private final List<GameObject> gameObjects;

    private final Debug debug;
    private final GameController gameController;

    private final AudioPlayer audioPlayer;

    private final Camera camera;
    private final Time time;

    private final StateManager stateManager;
    private final MapManager maps;

    private String shouldChangeTo;

    private final UIContainer debugSettingsContainer;

    //region Getters & Setters (Click to view)

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

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
        if(Boolean.TRUE.equals(Settings.getAudioOn().getValue())){
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
                .toList();
    }

    public Time getTime() {
        return time;
    }

    public void setFullScreen() {
        gameFrame.toggleFullScreen();
    }

    public MapManager getMapManager() {
        return maps;
    }

    //endregion

    public Game(){
        PathFinder.NodeBuffer.initialize();
        content = new ContentManager();
        content.loadContent();
        camera = new Camera();
        gameObjects = new ArrayList<>();
        stateManager = new StateManager(this);
        maps = new MapManager();
        maps.loadAll(content, "/maps");
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
        stateManager.update(this);
        maps.update(this);
        camera.update(maps.getCurrent());
        time.update();
        audioPlayer.update();

        if(Settings.isDebugMode()){
            debug.update(this);
            debugSettingsContainer.update(this);
        }

        updateObjectsDrawOrder();
        if(!(stateManager.getCurrentState() instanceof PauseMenuState)) {
            gameObjects.forEach(gameObject -> gameObject.update(this));
        }

        if(shouldChangeTo != null){
            getGameState().getPlayer().setPosition(new Vector2D(0,0));
            loadMap(shouldChangeTo);
            shouldChangeTo = null;
        }
    }

    /**
     * Updates in which order gameObjects should be rendered on the screen to give a correct
     * feeling of depth.
     */
    public void updateObjectsDrawOrder() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(
                gameObject -> gameObject.getRenderOrderComparisonYPosition()));
    }

    public void draw(Graphics g) {
        maps.draw(g, camera);

        gameObjects.stream()
                .filter(gameObject -> camera.isObjectInView(gameObject))
                .forEach(gameObject -> renderGameObject(g, camera, gameObject));

        stateManager.draw(g);

        if(Settings.isDebugMode()){
            debug.draw(this, g);
            debugSettingsContainer.draw(g);
        }
    }

    private void renderGameObject(Graphics g, Camera camera, GameObject gameObject) {
        gameObject.getAttachments().forEach(attachment -> renderGameObject(g, camera, attachment));
        gameObject.draw(g, camera);
    }

    public void resumeGame() {
        stateManager.goToGameState();
    }

    public void saveGame() {
        ProgressIO.save(this,"./save_file.txt");
    }

    public void loadGame() {
        ProgressIO.load(this, "./save_file.txt");
        addGameObject(getGameState().getPlayer());
        camera.focusOn(stateManager.getGameState().getPlayer());
        loadMap(stateManager.getGameState().getWorldMap()[getGameState().getPlayer().getWorldMapPosition().intX()]
                [getGameState().getPlayer().getWorldMapPosition().intY()]);
        stateManager.goToGameState();
        audioPlayer.playMusic("suburbs.wav");
    }

    public void goToMainMenu() {
        gameObjects.clear();
        camera.removeFocus();
        loadMap("main_menu_map");
        camera.centerOnMap(maps.getCurrent());
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
        getGameState().resetPlayerPosition();
        addGameObject(stateManager.getGameState().getPlayer());
        camera.focusOn(stateManager.getGameState().getPlayer());
        loadMap(stateManager.getGameState().getWorldMap()[0][0]);
        audioPlayer.playMusic("suburbs.wav");
    }

    public void enterUsername() {
        stateManager.goToSetupNameState();
    }

    public void goToWorldEditor() {
        stateManager.goToEditorState();
        createNewMap(64, 64, content);
        camera.removeFocus();
        Settings.setDebugMode(true);
    }

    public void goToLastState() {
        stateManager.goToLastState();
    }

    public void showDialog(String text) {
        System.out.println(text);
    }

    public void initializeNPCs(int numberToAdd) {
        SecureRandom randomizer = new SecureRandom();
        for(int i = 0; i < numberToAdd; i++){

            Vector2D spawnPosition = getCurrentMap().getRandomAvailablePositionOnMap();

            NPC npc = new NPC(new NPCController(),
                    content.getSpriteSet("villager" + randomizer.nextInt(5)), "default", getCurrentMap().getName());
            npc.setPosition(spawnPosition);
            addNPC(npc);
        }
    }

    public void loadMapFromPath(String path) {
        gameObjects.clear();
        maps.setCurrent(MapIO.loadFromPath(content, path));
        gameObjects.addAll(maps.getCurrent().getSceneryList());
        gameObjects.addAll(maps.getCurrent().getNPCList());
    }

    public void loadMap(String name) {
        gameObjects.removeIf(gameObject -> !(gameObject instanceof Player));
        maps.setCurrent(maps.getByName(name));
        gameObjects.addAll(maps.getCurrent().getSceneryList());
        gameObjects.addAll(maps.getCurrent().getNPCList());
        //initializeNPCs(20);
    }

    public void saveMap(String filePath) {
        MapIO.save(maps.getCurrent(), filePath);
    }


    public void createNewMap(int width, int height, ContentManager content) {
        gameObjects.clear();
        maps.setCurrent(new GameMap(width, height, content));
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
        if(gameObject instanceof Scenery scenery){
            getCurrentMap().removeScenery(scenery);
        }else if(gameObject instanceof NPC npc){
            getCurrentMap().removeNPC(npc);
        }
    }

    public void addNPC(NPC npc){
        maps.getCurrent().addNPC(npc);
        gameObjects.add(npc);
    }

    public void addScenery(Scenery scenery){
        maps.getCurrent().addScenery(scenery);
        gameObjects.add(scenery);
    }

    public EditorState getEditorState() {
        return stateManager.getEditorState();
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(stateManager.getGameState().serialize());
        sb.append(SECTION_DELIMETER);
        maps.getMaps().forEach((mapName, map) -> map.getNPCList().forEach(npc -> {
            sb.append(npc.serialize());
            sb.append(COLUMN_DELIMETER);
        }));

        return sb.toString();

    }

    @Override
    public void applySerializedData(String serializedData) {
        maps.getMaps().forEach((mapName, map) -> map.getNPCList().clear());

        String[] sections = serializedData.split(SECTION_DELIMETER);

        getGameState().applySerializedData(sections[1]);

        String npcSection = sections[2];

        String[] NPCs = npcSection.split(COLUMN_DELIMETER);

        for(String npcString : NPCs){
            NPC npc = new NPC();
            npc.applySerializedData(npcString);
            npc.applyGraphics(content);
            maps.getByName(npc.getMapName()).addNPC(npc);
        }
    }
    public void setShouldChangeMap(String name) {
        shouldChangeTo = name;
    }

}
