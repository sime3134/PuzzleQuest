package entity;

import content.SpriteSet;
import controller.EntityController;
import core.Direction;
import core.Vector2D;
import main.Game;
import map.GameMap;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author Simon Jern, Erik Larsson
 * The playable main chatacter in the game.
 */
public class Player extends Humanoid {

    private GameObject target;
    private final SelectionCircle selectionCircle;

    public Player(EntityController entityController, SpriteSet spriteSet, String name){
        super(entityController, spriteSet, name);
        this.speed = 5;
        this.selectionCircle = new SelectionCircle(38, 22);
        this.selectionCircle.setRenderOffset(new Vector2D(5, selectionCircle.getHeight() + 9f));
        this.collisionBoxOffset = new Vector2D(collisionBoxWidth / 2f, collisionBoxHeight / 2f + 11f);
    }

    @Override
    public void update(Game game) {
        super.update(game);

        handleTarget(game);
        handlePlayerSpecificInput(game);
    }

    public Direction findDirectionToMapBorder(GameMap currentMap) {
        if(getPosition().distanceBetweenPositions(new Vector2D(currentMap.getWidth(), 0)).getX() < 30
        && getPosition().distanceBetweenPositions(new Vector2D(currentMap.getWidth(), 0)).getX() > -30){
            return Direction.RIGHT;

        }else if(new Vector2D(getPosition().getX() + getWidth(), 0)
                .distanceBetweenPositions(new Vector2D(0, 0)).getX() < 30
        && new Vector2D(getPosition().getX() + getWidth(), 0)
                .distanceBetweenPositions(new Vector2D(0, 0)).getX() > -30){
            return Direction.LEFT;

        }else if(getPosition().distanceBetweenPositions(new Vector2D(0, currentMap.getHeight())).getY() < 30
        && getPosition().distanceBetweenPositions(new Vector2D(0, currentMap.getHeight())).getY() > -30){
            return Direction.DOWN;

        }else if(new Vector2D(0, getPosition().getY() + getHeight())
                .distanceBetweenPositions(new Vector2D(0, 0)).getY() < 30
        && new Vector2D(0, getPosition().getY() + getHeight())
                .distanceBetweenPositions(new Vector2D(0, 0)).getY() > -30){
            return Direction.UP;

        }

        return Direction.NULL;
    }

    @Override
    protected void executePlayerAction(Game game) {

    }

    /**
     * Check if the player has made any input except base input like
     * movement and reacts to the input accordingly.
     */
    private void handlePlayerSpecificInput(Game game){
        if(getController().requestedAction()){
            if(target != null && !game.getGameState().getNonNPCDialogActive()) {
                target.executePlayerAction(game);
            }else{
                game.getGameState().handleNonNpcDialog(game);
            }
        }
    }

    /**
     * Sets and removes the current target of the player.
     */
    private void handleTarget(Game game) {
        Optional<GameObject> closestGameObject = findClosestGameObject(game);

        if(closestGameObject.isPresent()){
            GameObject gameObject = closestGameObject.get();
            if(!gameObject.equals(target)) {
                if(target != null) {
                    target.detach(selectionCircle);
                }
                selectionCircle.setWidth(gameObject.getSelectionCircleWidth());
                selectionCircle.setHeight(gameObject.getSelectionCircleHeight());
                selectionCircle.setRenderOffset(new Vector2D(gameObject.getSelectionCircleRenderXOffset(),
                        gameObject.getSelectionCircleRenderYOffset()));
                gameObject.attach(selectionCircle);
                target = gameObject;
            }
        }else {
            if(target != null) {
                target.detach(selectionCircle);
                target = null;
            }
        }
    }

    private Optional<GameObject> findClosestGameObject(Game game) {
        return game.getGameObjectsOfClass(GameObject.class).stream()
                .filter(gameObject -> !(gameObject instanceof SelectionCircle) && !(gameObject instanceof Player))
                .filter(gameObject -> checkDistance(gameObject))
                .filter(gameObject -> isFacing(gameObject))
                .min(Comparator.comparingDouble(gameObject -> getStaticCollisionBox().getCenter()
                        .distanceTo(gameObject.getStaticCollisionBox().getCenter())));
    }

    private boolean checkDistance(GameObject gameObject) {
        Vector2D targetRange =
                new Vector2D(getStaticCollisionBox().getBounds().getWidth() / 2
                        + gameObject.getStaticCollisionBox().getBounds().getWidth() / 2 + 10,
                        getStaticCollisionBox().getBounds().getHeight() / 2
                                + gameObject.getStaticCollisionBox().getBounds().getHeight() / 2 + 10);

        Vector2D distance = getStaticCollisionBox().getCenter()
                .distanceBetweenPositions(gameObject.getStaticCollisionBox().getCenter());

        distance.makeAbsolute();

        return distance.getX() < targetRange.getX() && distance.getY() < targetRange.getY();
    }

    @Override
    protected void handleCollision(Game game, GameObject other) {
        if (other instanceof NPC) {
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }else if(other instanceof Scenery scenery){
            if(!other.isWalkable()){
                velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
            }
            if(scenery instanceof TeleportScenery tScenery){
                String nameOfMapToTeleportTo = tScenery.getMapToTeleportTo();
                game.setShouldChangeToMap(nameOfMapToTeleportTo);
                game.setShouldChangeToPosition(tScenery.getPositionToTeleportTo());
            }
        }
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName())
                .append(DELIMITER)
                .append(name)
                .append(DELIMITER)
                .append(worldMapPosition.serialize())
                .append(DELIMITER)
                .append(position.serialize());

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        name = tokens[1];
        worldMapPosition.applySerializedData(tokens[2]);
        position.applySerializedData(tokens[3]);
    }
}
