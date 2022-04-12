package entity;

import ai.AIManager;
import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;
import entity.humanoid.Humanoid;
import main.state.State;
import utilities.Buffer;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements living entities that are not controlled by the player.
 */
public class NPC extends Humanoid {

    private final AIManager brain;

    private final Buffer<Vector2D> targets;

    public AIManager getBrain() {
        return brain;
    }

    public Buffer<Vector2D> getTargets() {
        return targets;
    }

    public NPC(EntityController entityController, SpriteSet spriteSet) {
        super(entityController, spriteSet);
        this.targets = new Buffer<>();
        brain = new AIManager(this);
    }

    @Override
    public void update(State state){
        super.update(state);
        brain.update(state);
    }

    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof NPC && other != this
                || other instanceof Player
                || other instanceof Scenery && !((Scenery)other).isWalkable()){
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
