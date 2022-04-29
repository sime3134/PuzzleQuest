package main.state;

import controller.NPCController;
import controller.PlayerController;
import core.Direction;
import core.Vector2D;
import entity.NPC;
import entity.Player;
import main.Game;
import map.GameMap;
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

    public GameState(Game game){
        super(game);
        quests = new QuestManager();
        worldMapPosition = new Vector2D(0,0);
        game.loadMap(worldMap[worldMapPosition.intX()][worldMapPosition.intY()]);
        initializeEntities(game);
        NPC npc = new NPC(new NPCController(), game.getContent().getSpriteSet("villager1"));
        Quest goToTwoPositions = new GoToTwoPositions("Your first quest!");
        npc.addQuest(goToTwoPositions);
        quests.addQuest(goToTwoPositions);
        game.addGameObject(npc);
    }

    @Override
    public void update(Game game) {
        super.update(game);

        handleWorldMapLocation(game);
        quests.update(game);
    }

    private void handleWorldMapLocation(Game game) {
        Direction direction = player.findDirectionToMapBorder(game.getCurrentMap());

        if (direction != Direction.NULL) {

            Vector2D directionValue = Direction.toVelocity(direction);

            if (!repeatMaps) {

                if (worldMapPosition.intX() + directionValue.intX() < worldMap.length
                        && worldMapPosition.intX() + directionValue.intX() >= 0
                        && worldMapPosition.intY() + directionValue.intY() < worldMap[0].length
                        && worldMapPosition.intY() + directionValue.intY() >= 0) {

                    worldMapPosition = new Vector2D(worldMapPosition.intX() + directionValue.intX(),
                            worldMapPosition.intY() + directionValue.intY());

                        game.loadMap(worldMap[worldMapPosition.intX()]
                                [worldMapPosition.intY()]);

                    setPlayerPositionFromDirectionToMapBorder(game, direction);
                }
            }else{
                game.loadMap(worldMap[0][0]);
                setPlayerPositionFromDirectionToMapBorder(game, direction);
            }
        }
    }

    private void setPlayerPositionFromDirectionToMapBorder(Game game, Direction direction) {
        switch (direction) {
            case RIGHT -> player.setPosition(new Vector2D(0, player.getPosition().getY()));
            case LEFT -> player.setPosition(new Vector2D(game.getCurrentMap().getWidth() - player.getWidth(),
                    player.getPosition().getY()));
            case UP -> player.setPosition(new Vector2D(player.getPosition().getX(),
                    game.getCurrentMap().getHeight() - player.getHeight()));
            case DOWN -> player.setPosition(new Vector2D(player.getPosition().getX(), 0));
        }
    }

    /**
     * Not used at the moment but saved for future use.
     */
    private Vector2D calculatePositionOnNewMap(GameMap currentMap, Direction direction, double oldMapWidth,
                                               double oldMapHeight) {
        double heightRatio = calculateMapHeightRatio(currentMap, oldMapHeight);
        double widthRatio = calculateMapWidthRatio(currentMap, oldMapWidth);

        return switch (direction) {
            case RIGHT -> new Vector2D(0, player.getPosition().getY() * heightRatio);
            case LEFT -> new Vector2D(currentMap.getWidth() - player.getWidth(),
                    player.getPosition().getY() * heightRatio);
            case UP -> new Vector2D(player.getPosition().getX() * widthRatio,
                        currentMap.getHeight() - player.getHeight());
            case DOWN -> new Vector2D(player.getPosition().getX() * widthRatio, 0);
            case NULL -> null;
        };
    }

    private double calculateMapWidthRatio(GameMap currentMap, double oldMapWidth) {
        double biggestMapWidth = Math.max(oldMapWidth, currentMap.getWidth());
        double smallestMapWidth = Math.min(oldMapWidth, currentMap.getWidth());

        if(biggestMapWidth == oldMapWidth){
            return smallestMapWidth / biggestMapWidth;
        }else{
            return biggestMapWidth / smallestMapWidth;
        }
    }

    private double calculateMapHeightRatio(GameMap currentMap, double oldMapHeight) {
        double biggestMapHeight = Math.max(oldMapHeight, currentMap.getHeight());
        double smallestMapHeight = Math.min(oldMapHeight, currentMap.getHeight());

        if(biggestMapHeight == oldMapHeight){
            return smallestMapHeight / biggestMapHeight;
        }else{
            return biggestMapHeight / smallestMapHeight;
        }
    }

    @Override
    protected void setupUI(Game game) {
        super.setupUI(game);

        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        container.addComponent(new UIText("Puzzle Quest 2.0"));
        container.setBackgroundColor(new Color(0,0,0,0));

        UIButton pauseMenuButton = new UIButton("pause", () -> game.pauseGame());
        HorizontalContainer buttonMenu = new HorizontalContainer(pauseMenuButton);
        pauseMenuButton.setWidth(70);
        uiContainers.add(buttonMenu);

        uiContainers.add(container);
    }

    @Override
    public void escapeButtonPressed(Game game) {
        game.pauseGame();
    }

    private void initializeEntities(Game game) {
        player = new Player(PlayerController.getInstance(),
                game.getContent().getSpriteSet("player"));
        player.setPosition(new Vector2D(200,200));

        game.getGameObjects().add(player);
        game.getCamera().focusOn(player);

        initializeNPCs(20, game);
    }

    private void initializeNPCs(int numberToAdd, Game game) {
        SecureRandom randomizer = new SecureRandom();
        for(int i = 0; i < numberToAdd; i++){

            Vector2D spawnPosition = game.getCurrentMap().getRandomAvailablePositionOnMap(game.getGameObjects());

            NPC npc = new NPC(new NPCController(),
                    game.getContent().getSpriteSet("villager" + randomizer.nextInt(5)));
            npc.setPosition(new Vector2D(1400, 1000));
            game.addGameObject(npc);

        }
    }

    public void startAvailableQuest() {

    }
}
