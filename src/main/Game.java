package main;

import IO.MapIO;
import IO.Persistable;
import IO.ProgressIO;
import audio.AudioPlayer;
import content.ContentManager;
import controller.GameController;
import core.Action;
import core.CollisionBox;
import core.Time;
import core.Vector2D;
import display.Camera;
import display.Debug;
import display.GameFrame;
import editor.UISettingsContainer;
import entity.*;
import main.state.*;
import map.GameMap;
import map.MapManager;
import map.PathFinder;
import settings.Settings;
import ui.NotificationManager;
import ui.containers.UIContainer;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @author Simon Jern, Johan Salomonsson
 * Main class for the game controlling the current state of the game.
 */
public class Game implements Persistable {

    private final ContentManager content;

    private final GameFrame gameFrame;

    private final List<GameObject> gameObjects;

    private final List<GameObject> gameObjectsToRemove;

    private final List<Scenery> sceneryToOverwrite;

    private final Map<NPC, GameMap> npcToMoveToAnotherMap;

    private final Debug debug;
    private final GameController gameController;

    private final AudioPlayer audioPlayer;

    private final Camera camera;
    private final Time time;

    private final StateManager stateManager;
    private final MapManager maps;

    private String shouldChangeToMap;

    private Vector2D shouldChangeToPosition;

    private final UIContainer debugSettingsContainer;

    private final NotificationManager notificationManager;

    private boolean canPause;
    private boolean showBlackScreen;
    private boolean showingBlackScreenWithTimer;

    //region Getters & Setters (Click to view)

    public void setShowBlackScreen(boolean showBlackScreen) {
        this.showBlackScreen = showBlackScreen;
    }

    public void setShouldChangeToMap(String name) {
        shouldChangeToMap = name;
    }

    public void setShouldChangeToPosition(Vector2D shouldChangeToPosition) {
        this.shouldChangeToPosition = shouldChangeToPosition;
    }

    public void setCanPause(boolean canPause) {
        this.canPause = canPause;
    }

    public EditorState getEditorState() {
        return stateManager.getEditorState();
    }

    public PauseMenuState getPauseState() {
        return stateManager.getPauseMenuState();
    }

    public GameObject getGameObjectById(long id) {
        for(GameMap map : maps.getMaps().values()){
            GameObject obj = map.findGameObjectById(id);
            if(obj != null){
                return obj;
            }
        }
        return null;
    }

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
        if(Boolean.TRUE.equals(Settings.getAudioOn().get())){
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

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    //endregion

    public Game(){
        PathFinder.NodeBuffer.initialize();
        content = new ContentManager();
        content.loadContent();
        camera = new Camera();
        gameObjects = new ArrayList<>();
        gameObjectsToRemove = new ArrayList<>();
        sceneryToOverwrite = new ArrayList<>();
        npcToMoveToAnotherMap = new HashMap<>();
        maps = new MapManager();
        maps.loadAll(content, "/maps");
        stateManager = new StateManager(this);
        notificationManager = new NotificationManager();
        time = new Time();
        gameController = new GameController();
        debug = new Debug();
        audioPlayer = new AudioPlayer();
        goToMainMenu();
        debugSettingsContainer = new UISettingsContainer(maps.getCurrent(), content);
        gameFrame = new GameFrame(this);
        canPause = true;
    }

    public void update() {
        gameController.update(this);
        stateManager.update(this);
        notificationManager.update(this);
        maps.update(this);
        camera.update(maps.getCurrent());
        time.update();
        audioPlayer.update();

        if(Settings.isDebugMode()){
            debug.update(this);
            debugSettingsContainer.update(this);
        }

        updateObjectsDrawOrder();
        if(!(stateManager.getCurrentState() instanceof PauseMenuState) && !showingBlackScreenWithTimer) {
            gameObjects.forEach(gameObject -> gameObject.update(this));
        }

        cleanUp();

        if(shouldChangeToMap != null){
            loadMap(shouldChangeToMap);
            if(shouldChangeToPosition != null) {
                getGameState().getPlayer().setPosition(shouldChangeToPosition.getCopy());
            }else if(getCurrentMap().getStartingPosition() != null) {
                getGameState().getPlayer().setPosition(getCurrentMap().getStartingPosition());
            }
            shouldChangeToMap = null;
            shouldChangeToPosition = null;
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
        if(!showBlackScreen) {
            maps.draw(g, camera);

            if(stateManager.getCurrentState() instanceof EditorState) {
                gameObjects.stream()
                        .filter(gameObject -> camera.isObjectInView(gameObject))
                        .forEach(gameObject -> renderGameObject(g, camera, gameObject));
            }else {
                gameObjects.stream()
                        .filter(gameObject -> camera.isObjectInView(gameObject))
                        .filter(gameObject -> !(gameObject instanceof TeleportScenery))
                        .forEach(gameObject -> renderGameObject(g, camera, gameObject));
            }

        }

        stateManager.draw(this, g);
        notificationManager.draw(this, g);

        if(Settings.isDebugMode()){
            debug.draw(this, g);
            debugSettingsContainer.draw(this, g);
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
        getGameState().getPlayer().setLastInteractedWith(new NPC());
        getGameState().getQuestManager().initializeQuests(this);
        ProgressIO.load(this, "./save_file.txt");
        addGameObject(getGameState().getPlayer());
        camera.focusOn(stateManager.getGameState().getPlayer());
        updateNPCMapsFromLoad();
        loadMap(getGameState().getPlayer().getCurrentMapName());
        stateManager.goToGameState();
        audioPlayer.playMusic("suburbs.wav", 0);
        getGameState().initializeNameTags(this);
    }

    private void updateNPCMapsFromLoad() {
        for(Map.Entry<NPC, GameMap> entry : npcToMoveToAnotherMap.entrySet()){
            entry.getValue().removeNPC(entry.getKey());
            maps.getByName(entry.getKey().getCurrentMapName()).addNPC(entry.getKey());
        }
        npcToMoveToAnotherMap.clear();
    }

    public void startNewGame(String playerName) {
        sceneryToOverwrite.clear();
        maps.loadAll(content, "/maps");
        getGameState().initializeNewGame(this, playerName);
        addGameObject(getGameState().getPlayer());
        camera.focusOn(getGameState().getPlayer());
        loadMap(getGameState().getWorldMap()
                [getGameState().getPlayer().getWorldMapPosition().intX()]
                [getGameState().getPlayer().getWorldMapPosition().intY()]);
        audioPlayer.playMusic("suburbs.wav", 0);
        stateManager.goToGameState();
        getGameState().getQuestManager().startQuest(this, 0);
        getGameState().handleNonNpcDialog(this);
        getGameState().initializeNameTags(this);
    }

    public void goToMainMenu() {
        gameObjects.clear();
        camera.removeFocus();
        loadMap("main_menu_map");
        camera.removeFocus();
        camera.centerOnMap(maps.getCurrent());
        stateManager.goToMainMenuState();
        Settings.reset();
        audioPlayer.playMusic("menu.wav", 0);
    }

    public void pauseGame() {
        if(canPause) {
            stateManager.goToPauseState();
        }
    }

    public void goToQuestViewState() {
        if(canPause) {
            stateManager.goToQuestViewState();
        }
    }

    public void goToSettingsMenu() {
        stateManager.goToSettingsState();
    }

    public void enterUsername() {
        stateManager.goToSetupNameState();
        ((SetupNameState)stateManager.getCurrentState()).getNameInput().activate(this);
    }

    public void goToWorldEditor() {
        stateManager.goToEditorState(content);
        createNewMap(12, 8, content);
        camera.removeFocus();
        Settings.setDebugMode(true);
    }

    public void goToLastState() {
        stateManager.goToLastState();
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
        getGameState().getPlayer().setCurrentMapName(maps.getCurrent().getName());
        gameObjects.addAll(maps.getCurrent().getSceneryList());
        gameObjects.addAll(maps.getCurrent().getNPCList());
        if(getCurrentMap().getWidth() < Settings.getScreenWidth() || getCurrentMap().getHeight() < Settings.getScreenHeight()){
            camera.removeFocus();
            camera.centerOnMap(getCurrentMap());
        }else{
            camera.focusOn(getGameState().getPlayer());
        }
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

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(stateManager.getGameState().serialize());
        sb.append(SECTION_DELIMETER);
        maps.getMaps().forEach((mapName, map) -> map.getNPCList().forEach(npc -> {
            sb.append(npc.serialize());
            sb.append(COLUMN_DELIMETER);
        }));

        if(!sceneryToOverwrite.isEmpty()) {
            sb.append(SECTION_DELIMETER);
            for (Scenery scenery : sceneryToOverwrite) {
                sb.append(scenery.serialize());
                sb.append(COLUMN_DELIMETER);
            }
        }

        return sb.toString();

    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] sections = serializedData.split(SECTION_DELIMETER);

        getGameState().applySerializedData(sections[1]);

        String npcSection = sections[2];

        String[] NPCs = npcSection.split(COLUMN_DELIMETER);

        for(String npcString : NPCs){
            if(!npcString.equals("###") && !npcString.isEmpty()) {
                String[] npcTokens = npcString.split(DELIMITER);
                long npcId = Long.parseLong(npcTokens[2]);
                for (GameMap map : maps.getMaps().values()) {
                    for (NPC n : map.getNPCList()) {
                        if (n.getId() == npcId) {
                            n.applySerializedData(npcString);
                            n.applyGraphics(content);
                            npcToMoveToAnotherMap.put(n, map);
                        }
                    }
                }
            }
        }

        if(sections.length > 3) {
            String scenerySection = sections[3];

            String[] sceneries = scenerySection.split(COLUMN_DELIMETER);

            sceneryToOverwrite.clear();

            for (String sceneryString : sceneries) {
                String[] sceneryTokens = sceneryString.split(DELIMITER);
                long sceneryId = Long.parseLong(sceneryTokens[2]);
                for (GameMap map : maps.getMaps().values()) {
                    for (Scenery s : map.getSceneryList()) {
                        if (s.getId() == sceneryId) {
                            s.applySerializedData(sceneryString);
                            s.loadGraphics(content);
                            sceneryToOverwrite.add(s);
                        }
                    }
                }
            }

            System.out.println("Game 480: " + sceneryToOverwrite);
        }
    }

    public void addGameObjectToRemove(GameObject obj) {
        gameObjectsToRemove.add(obj);
    }

    private void cleanUp(){
        for(GameObject object : gameObjectsToRemove){
            gameObjects.remove(object);
        }
        gameObjectsToRemove.clear();
    }

    public void addSceneryToOverwrite(Scenery scenery) {
        sceneryToOverwrite.add(scenery);
    }

    public void showBlackScreen(long delay, Game game, Action action) {
        setShowBlackScreen(true);
        showingBlackScreenWithTimer = true;
        canPause = false;
        Timer timer = new Timer("BlackoutTimer");
        timer.schedule(new TimerTask() {
            public void run() {
                setShowBlackScreen(false);
                canPause = true;
                showingBlackScreenWithTimer = false;
                action.execute(game);
            }
        }, delay);
    }

    public void displayNotification(String notification) {
        notificationManager.displayNotification(notification);
    }
}
