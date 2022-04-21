package main.state;

import controller.NPCController;
import controller.PlayerController;
import core.Vector2D;
import entity.NPC;
import entity.Player;
import main.Game;
import ui.*;
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

    public GameState(){
        super();
        loadMap();
        initializeEntities();
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
