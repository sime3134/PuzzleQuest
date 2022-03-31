package entity;

import content.SpriteSet;
import controller.EntityController;
import main.state.State;
import settings.GameSettings;

import java.util.Comparator;
import java.util.Optional;

/**
 * The player of the game.
 */
public class Player extends MovingEntity {

    private GameSettings settings = GameSettings.getInstance();

    private NPC target;
    private double targetRange;
    private SelectionCircle selectionCircle;

    public Player(EntityController entityController, SpriteSet spriteSet, int spriteWidth, int spriteHeight,
                  SelectionCircle selectionCircle){
        super(entityController, 4, spriteSet, spriteWidth, spriteHeight);
        this.selectionCircle = selectionCircle;
        this.targetRange = settings.getSpriteSize();
    }

    @Override
    public void update(State state) {
        super.update(state);
        handleTarget(state);
    }

    private void handleTarget(State state) {
        Optional<NPC> closestNPC = findClosestNPC(state);

        if(closestNPC.isPresent()){
            NPC npc = closestNPC.get();
            if(!npc.equals(target)){
                selectionCircle.setParent(npc);
                target = npc;
            }
        }else{
            selectionCircle.clearParent();
            target = null;
        }
    }

    private Optional<NPC> findClosestNPC(State state) {
        return state.getGameObjectsOfClass(NPC.class).stream()
                .filter(npc -> getPosition().distanceTo(npc.getPosition()) < targetRange)
                .filter(npc -> isFacing(npc.getPosition()))
                .min(Comparator.comparingDouble(npc -> position.distanceTo(npc.getPosition())));
    }

    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof NPC) {
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
