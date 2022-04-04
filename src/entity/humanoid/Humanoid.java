package entity.humanoid;

import content.SpriteSet;
import controller.EntityController;
import entity.GameObject;
import entity.MovingEntity;
import main.state.State;

/**
 * @author Simon Jern
 * Implements code for humanoid game objects in the game.
 */
public abstract class Humanoid extends MovingEntity {

    protected Humanoid(EntityController entityController, SpriteSet spriteSet) {
        super(entityController, spriteSet);
        this.collisionBoxWidth = 24;
        this.collisionBoxHeight = 24;
    }

    @Override
    public void update(State state){
        super.update(state);
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
