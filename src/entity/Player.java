package entity;

import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;
import entity.humanoid.Humanoid;
import main.state.State;
import settings.Settings;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author Simon Jern
 * The player of the game.
 */
public class Player extends Humanoid {

    private NPC target;
    private final double targetRange;
    private final SelectionCircle selectionCircle;

    public Player(EntityController entityController, SpriteSet spriteSet){
        super(entityController, spriteSet);
        this.selectionCircle = new SelectionCircle(38, 22);
        this.selectionCircle.setRenderOffset(new Vector2D(5, selectionCircle.getHeight() + 9f));
        this.targetRange = Settings.getSpriteSize();
    }

    @Override
    public void update(State state) {
        super.update(state);

        handleTarget(state);
        handlePlayerSpecificInput();
    }

    private void handlePlayerSpecificInput(){
        if(getController().requestedAction() && target != null){
            target.getBrain().transitionTo("wander", target);
        }
    }

    private void handleTarget(State state) {
        Optional<NPC> closestNPC = findClosestNPC(state);

        if(closestNPC.isPresent()){
            NPC npc = closestNPC.get();
            if(!npc.equals(target)){
                if(target != null){
                    target.detach(selectionCircle);
                }
                npc.attach(selectionCircle);
                target = npc;
            }
        }else{
            if(target != null){
                target.detach(selectionCircle);
                target = null;
            }
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
        if(other instanceof NPC
                || other instanceof Scenery && !((Scenery)other).isWalkable()) {
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
