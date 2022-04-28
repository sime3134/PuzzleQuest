package main.state;

import content.ContentManager;
import controller.NPCController;
import controller.PlayerController;
import core.Direction;
import core.Vector2D;
import entity.NPC;
import entity.Player;
import main.Game;
import story.Quest;
import story.QuestManager;
import story.quests.GoToTwoPositions;
import ui.Alignment;
import ui.HorizontalContainer;
import ui.UIContainer;
import ui.UIText;
import ui.clickable.UIButton;

import java.awt.*;
import java.security.SecureRandom;

/**
 * @author Simon Jern, Johan Salomonsson
 * The class containing all the front logic for the game. This class should not contain any complicated or overly long
 * methods but instead pair together several components of the game such as the player, NPCs, content etc.
 */
public class GameState extends State{

    private Player player;

    private QuestManager quests;

    private boolean repeatMaps;

    private String[][] worldMap = {
            {"map1", "map2", "map3", "map4", "map5"},
            {"map6", "map7", "map8", "map9", "map10"},
            {"map11", "map12", "map13", "map14", "map15"},
            {"map16", "map17", "map18", "map19", "map20"},
            {"map21", "map22", "map23", "map24", "map25"}
    };

    private Vector2D worldMapPosition;

    public Player getPlayer() {
        return player;
    }

    public GameState(ContentManager content){
        super(content);
        quests = new QuestManager();
        worldMapPosition = new Vector2D(0,0);
        loadMap(worldMap[worldMapPosition.intX()][worldMapPosition.intY()], false);
        initializeEntities();
        NPC npc = new NPC(new NPCController(), content.getSpriteSet("villager1"));
        Quest goToTwoPositions = new GoToTwoPositions("Your first quest!");
        npc.addQuest(goToTwoPositions);
        quests.addQuest(goToTwoPositions);
        gameObjects.add(npc);
    }

    @Override
    public void update(Game game) {
        super.update(game);

        handleWorldMapLocation();
        quests.update(game);
    }

    private void handleWorldMapLocation() {
        Direction direction = player.findDirectionToMapBorder(this);

        if (direction != Direction.NULL) {

            Vector2D directionValue = Direction.toVelocity(direction);

            if (!repeatMaps) {

                if (worldMapPosition.intX() + directionValue.intX() < worldMap.length
                        && worldMapPosition.intX() + directionValue.intX() >= 0
                        && worldMapPosition.intY() + directionValue.intY() < worldMap[0].length
                        && worldMapPosition.intY() + directionValue.intY() >= 0) {

                    worldMapPosition = new Vector2D(worldMapPosition.intX() + directionValue.intX(),
                            worldMapPosition.intY() + directionValue.intY());

                        loadMap(worldMap[worldMapPosition.intX()]
                                [worldMapPosition.intY()], false);

                    setPlayerPositionFromDirectionToMapBorder(direction);
                }
            }else{
                loadMap(worldMap[0][0], false);
                setPlayerPositionFromDirectionToMapBorder(direction);
            }
        }
    }

    private void setPlayerPositionFromDirectionToMapBorder(Direction direction) {
        switch (direction) {
            case RIGHT -> player.setPosition(new Vector2D(0, player.getPosition().getY()));
            case LEFT -> player.setPosition(new Vector2D(currentMap.getWidth() - player.getWidth(),
                    player.getPosition().getY()));
            case UP -> player.setPosition(new Vector2D(player.getPosition().getX(),
                    currentMap.getHeight() - player.getHeight()));
            case DOWN -> player.setPosition(new Vector2D(player.getPosition().getX(), 0));
        }
    }

    /**
     * Not used at the moment but saved for future use.
     */
    private Vector2D calculatePositionOnNewMap(Direction direction, double oldMapWidth, double oldMapHeight) {
        double heightRatio = calculateMapHeightRatio(oldMapHeight);
        double widthRatio = calculateMapWidthRatio(oldMapWidth);

        return switch (direction) {
            case RIGHT -> new Vector2D(0, player.getPosition().getY() * heightRatio);
            case LEFT -> new Vector2D(currentMap.getWidth() - player.getWidth(), player.getPosition().getY() * heightRatio);
            case UP -> new Vector2D(player.getPosition().getX() * widthRatio,
                        currentMap.getHeight() - player.getHeight());
            case DOWN -> new Vector2D(player.getPosition().getX() * widthRatio, 0);
            case NULL -> null;
        };
    }

    private double calculateMapWidthRatio(double oldMapWidth) {
        double biggestMapWidth = Math.max(oldMapWidth, currentMap.getWidth());
        double smallestMapWidth = Math.min(oldMapWidth, currentMap.getWidth());

        if(biggestMapWidth == oldMapWidth){
            return smallestMapWidth / biggestMapWidth;
        }else{
            return biggestMapWidth / smallestMapWidth;
        }
    }

    private double calculateMapHeightRatio(double oldMapHeight) {
        double biggestMapHeight = Math.max(oldMapHeight, currentMap.getHeight());
        double smallestMapHeight = Math.min(oldMapHeight, currentMap.getHeight());

        if(biggestMapHeight == oldMapHeight){
            return smallestMapHeight / biggestMapHeight;
        }else{
            return biggestMapHeight / smallestMapHeight;
        }
    }

    @Override
    protected void setupUI() {
        super.setupUI();

        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        container.addComponent(new UIText("Puzzle Quest 2.0"));
        container.setBackgroundColor(new Color(0,0,0,0));

        UIButton pauseMenuButton = new UIButton("pause", game -> game.pauseGame());
        HorizontalContainer buttonMenu = new HorizontalContainer(pauseMenuButton);
        pauseMenuButton.setWidth(70);
        uiContainers.add(buttonMenu);

        uiContainers.add(container);
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.pauseGame();
    }

    private void initializeEntities() {
        player = new Player(PlayerController.getInstance(),
                content.getSpriteSet("player"));
        player.setPosition(new Vector2D(1300,1000));

        gameObjects.add(player);
        camera.focusOn(player);

        initializeNPCs(20);
    }

    private void initializeNPCs(int numberToAdd) {
        SecureRandom randomizer = new SecureRandom();
        for(int i = 0; i < numberToAdd; i++){

            Vector2D spawnPosition = currentMap.getRandomAvailablePositionOnMap(gameObjects);

            NPC npc = new NPC(new NPCController(),
                    content.getSpriteSet("villager" + randomizer.nextInt(5)));
            npc.setPosition(new Vector2D(1400, 1000));
            gameObjects.add(npc);

        }
    }

    public void startAvailableQuest() {

    }
}
