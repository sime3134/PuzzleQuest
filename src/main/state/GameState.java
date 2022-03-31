package main.state;

import controller.NPCController;
import controller.PlayerController;
import core.CollisionBox;
import core.Vector2D;
import entity.GameObject;
import entity.NPC;
import entity.Player;
import entity.SelectionCircle;
import map.GameMap;
import ui.*;

import java.awt.*;
import java.security.SecureRandom;
import java.util.Comparator;

/**
 * The class containing all the front logic for the game. This class should not contain any complicated or overly long
 * methods but instead pair together several components of the game such as the player, NPCs, content etc.
 */
public class GameState extends State{
    private Player player;
    private SelectionCircle selectionCircle;

    public Vector2D getPlayerPosition() {
        return player.getPosition();
    }

    public GameState(){
        super();
        currentMap = new GameMap(32, 32, content);
        initializeEntities();
        initializeUI();
    }

    private void initializeUI() {
        UIContainer container = new HorizontalContainer();
        container.setAlignment(new Alignment(Alignment.Horizontal.CENTER, Alignment.Vertical.TOP));
        container.addComponent(new UIText(1,10, "Puzzle Quest 2"));
        container.setBackgroundColor(new Color(0,0,0,0));
        uiContainers.add(container);
    }

    private void initializeEntities() {
        selectionCircle = new SelectionCircle();
        player = new Player(PlayerController.getInstance(),
                content.getSpriteSet("player"), selectionCircle);
        gameObjects.add(player);
        gameObjects.add(selectionCircle);
        camera.focusOn(player);

        initializeNPCs(50);
    }

    private void initializeNPCs(int numberToAdd) {
        SecureRandom randomizer = new SecureRandom();
        for(int i = 0; i < numberToAdd; i++){

            Vector2D spawnPosition;

            do{
                spawnPosition = currentMap.getRandomPositionOnMap();
            }while(!isValidSpawnPosition(spawnPosition));

            NPC npc = new NPC(new NPCController(),
                    content.getSpriteSet("villager" + randomizer.nextInt(5)));
            npc.setPosition(spawnPosition);
            gameObjects.add(npc);

        }
    }

    private boolean isValidSpawnPosition(Vector2D spawnPosition){
        CollisionBox box = new CollisionBox(
                new Rectangle(
                        spawnPosition.intX() - 30,
                        spawnPosition.intY() - 30,
                        80, 80
                )
        );
        return getCollidingBoxes(box).isEmpty();
    }

    @Override
    public void update(){
        super.update();
        updateObjectsDrawOrder();
        gameObjects.forEach(gameObject -> gameObject.update(this));
    }

    public void draw(Graphics g){
        currentMap.draw(g, camera);
        gameObjects.stream()
                .filter(gameObject -> camera.isObjectInView(gameObject))
                .forEach(gameObject -> gameObject.draw(g, camera));
        uiContainers.forEach(uiContainer -> uiContainer.draw(g));
    }

    private void updateObjectsDrawOrder() {
        gameObjects.sort(Comparator.comparing(GameObject::getRenderOrder).thenComparing(gameObject -> gameObject.getPosition().getY()));
    }
}
