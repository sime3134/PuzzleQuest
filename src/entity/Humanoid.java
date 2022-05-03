package entity;

import IO.Persistable;
import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;

/**
 * @author Simon Jern
 * Implements code for humanoid game objects in the game.
 */
public abstract class Humanoid extends MovingEntity implements Persistable {

    private String name;

    private String spriteSetName;

    protected Vector2D worldMapPosition;

    public Vector2D getWorldMapPosition() {
        return worldMapPosition;
    }

    public void setWorldMapPosition(Vector2D worldMapPosition) {
        this.worldMapPosition = worldMapPosition;
    }

    protected Humanoid(EntityController entityController, SpriteSet spriteSet, String name) {
        super(entityController, spriteSet);
        this.worldMapPosition = new Vector2D(0,0);
        this.name = name;
        this.collisionBoxWidth = 24;
        this.collisionBoxHeight = 24;
        this.collisionBoxOffset = new Vector2D(collisionBoxWidth / 2f, collisionBoxHeight / 2f + 5f);
        this.selectionCircleWidth = 38;
        this.selectionCircleHeight = 22;
        this.selectionCircleRenderXOffset = 5;
        this.selectionCircleRenderYOffset = (int) (selectionCircleHeight + 9f);
    }

    @Override
    protected String decideAnimation() {
        if(isMoving()){
            return "walk";
        }
        return "stand";
    }

    @Override
    protected abstract void handleCollision(GameObject other);

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName())
                .append(DELIMITER)
                .append(name)
                .append(DELIMITER)
                .append(worldMapPosition.serialize())
                .append(DELIMITER)
                .append(position.serialize())
                .append(animationManager.getSpriteSet().getName());

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        //abstract?
    }
}
