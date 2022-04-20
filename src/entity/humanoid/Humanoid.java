package entity.humanoid;

import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;
import entity.GameObject;
import entity.MovingEntity;

/**
 * @author Simon Jern
 * Implements code for humanoid game objects in the game.
 */
public abstract class Humanoid extends MovingEntity {

    protected Humanoid(EntityController entityController, SpriteSet spriteSet) {
        super(entityController, spriteSet);
        this.collisionBoxWidth = 24;
        this.collisionBoxHeight = 24;
        this.collisionBoxOffset = new Vector2D(collisionBoxWidth / 2f, collisionBoxHeight / 2f + 11f);
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
}
