package main.state;

import controller.NPCController;
import controller.PlayerController;
import core.Direction;
import core.Vector2D;
import entity.NPC;
import entity.Player;
import main.Game;
import ui.Alignment;
import ui.HorizontalContainer;
import ui.UIContainer;
import ui.UIText;
import ui.clickable.UIButton;

import java.awt.*;
import java.security.SecureRandom;

/**
 * @author Simon Jern
 * The class containing all the front logic for the game. This class should not contain any complicated or overly long
 * methods but instead pair together several components of the game such as the player, NPCs, content etc.
 */
public class GameState extends State{

    private Player player;

    private String[][] worldMap = {
            {"village_test", "maze"},
            {"main_menu_map", "map"}
    };

    private Vector2D worldMapPosition;

    public void setWorldMapPosition(Vector2D worldMapPosition) {
        this.worldMapPosition = worldMapPosition;
    }

    public GameState(){
        super();
        worldMapPosition = new Vector2D(0,0);
        loadMap(worldMap[worldMapPosition.intX()][worldMapPosition.intY()], false);
        initializeEntities();
    }

    @Override
    public void update(Game game) {
        super.update(game);

        handleWorldMapLocation();
    }

    private void handleWorldMapLocation() {
        Direction direction = player.findDirectionToMapBorder(this);

        System.out.println(direction);

        if(direction != Direction.NULL) {

            Vector2D directionValue = Direction.directionToVelocity(direction);

            setWorldMapPosition(new Vector2D(worldMapPosition.intX() + directionValue.intX(),
                    worldMapPosition.intY() + directionValue.intY()));

            loadMap(worldMap[worldMapPosition.intX()]
                    [worldMapPosition.intY()], false);

            switch (direction) {
                case RIGHT -> player.setPosition(new Vector2D(0, player.getPosition().getY()));
                case LEFT -> player.setPosition(new Vector2D(currentMap.getWidth() - player.getWidth(),
                        player.getPosition().getY()));
                case UP -> player.setPosition(new Vector2D(
                        player.getPosition().getX(), currentMap.getHeight() - player.getHeight()));
                case DOWN -> player.setPosition(new Vector2D(player.getPosition().getX(), 0));
            }
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
        player.setPosition(new Vector2D(5,5));

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
            npc.setPosition(spawnPosition);
            gameObjects.add(npc);

        }
    }
}
