package entity;

import content.SpriteSet;
import controller.EntityController;
import core.Direction;
import core.Vector2D;
import entity.humanoid.Humanoid;
import main.state.State;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author Simon Jern, Erik Larsson
 * The playable main chatacter in the game.
 */
public class Player extends Humanoid {

    private GameObject target;
    private final SelectionCircle selectionCircle;

    public Player(EntityController entityController, SpriteSet spriteSet){
        super(entityController, spriteSet);
        this.selectionCircle = new SelectionCircle(38, 22);
        this.selectionCircle.setRenderOffset(new Vector2D(5, selectionCircle.getHeight() + 9f));
    }

    @Override
    public void update(State state) {
        super.update(state);

        handleTarget(state);
        handlePlayerSpecificInput(state);
    }

    public Direction findDirectionToMapBorder(State state) {
        if(getPosition().distanceBetweenPositions(new Vector2D(state.getCurrentMap().getWidth(), 0)).getX() < 5
        && getPosition().distanceBetweenPositions(new Vector2D(state.getCurrentMap().getWidth(), 0)).getX() > -5){
            return Direction.RIGHT;
        }else if(new Vector2D(getPosition().getX() + getWidth(), 0)
                .distanceBetweenPositions(new Vector2D(0, 0)).getX() < 5
        && new Vector2D(getPosition().getX() + getWidth(), 0)
                .distanceBetweenPositions(new Vector2D(0, 0)).getX() > -5){
            return Direction.LEFT;
        }else if(getPosition().distanceBetweenPositions(new Vector2D(0, state.getCurrentMap().getHeight())).getY() < 5
        && getPosition().distanceBetweenPositions(new Vector2D(0, state.getCurrentMap().getHeight())).getY() > -5){
            return Direction.DOWN;
        }else if(new Vector2D(0, getPosition().getY() + getHeight())
                .distanceBetweenPositions(new Vector2D(0, 0)).getY() < 5
        && new Vector2D(0, getPosition().getY() + getHeight())
                .distanceBetweenPositions(new Vector2D(0, 0)).getY() > -5){
            return Direction.UP;
        }

        return Direction.NULL;
    }

    @Override
    protected void executePlayerAction(State state) {

    }

    /**
     * Check if the player has made any input except base input like
     * movement and reacts to the input accordingly.
     */
    private void handlePlayerSpecificInput(State state){
        if(getController().requestedAction() && target != null){
            target.executePlayerAction(state);
        }
    }

    /**
     * Sets and removes the current target of the player.
     */
    private void handleTarget(State state) {
        Optional<GameObject> closestGameObject = findClosestGameObject(state);

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

    private Optional<GameObject> findClosestGameObject(State state) {
        return state.getGameObjectsOfClass(GameObject.class).stream()
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
    protected void handleCollision(GameObject other) {
        if (other instanceof NPC
                || other instanceof Scenery && !other.isWalkable()) {
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
