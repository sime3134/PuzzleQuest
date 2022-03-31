package entity;

import ai.AIManager;
import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;
import main.state.State;
import utilities.Buffer;

public class NPC extends MovingEntity{

    private final AIManager brain;

    private Buffer<Vector2D> targets;

    public AIManager getBrain() {
        return brain;
    }

    public Buffer<Vector2D> getTargets() {
        return targets;
    }

    public NPC(EntityController entityController, SpriteSet spriteSet, int spriteWidth, int spriteHeight) {
        super(entityController, 4, spriteSet, spriteWidth, spriteHeight);
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
        if(other instanceof NPC && other != this || other instanceof Player){
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
