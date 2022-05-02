package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Vector2D;
import entity.NPC;
import main.Game;
import map.PathFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a state where the entity will walk towards a goal.
 */
public class WanderLoop extends AIState{

    List<Vector2D> path;

    private Vector2D otherTarget;

    private Vector2D currentTarget;

    private boolean leaveLoop;

    public WanderLoop(Game game, NPC currentNPC, String lastState, Vector2D firstTarget, Vector2D secondTarget) {
        super(currentNPC, lastState);
        path = new ArrayList<>();
        currentNPC.setPath(path);
        this.currentTarget = firstTarget;
        this.otherTarget = secondTarget;
        List<Vector2D> foundPath = PathFinder.findPath(currentNPC.getCollisionBoxGridPosition(),
                currentTarget,
                game.getCurrentMap());
        if(!foundPath.isEmpty()){
            currentTarget = foundPath.get(foundPath.size() - 1);
            this.path.addAll(foundPath);
        }
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("wander_random", ((state, currentNPC) -> leaveLoop));
    }

    @Override
    public void update(Game game) {
        NPCController controller = (NPCController) currentNPC.getController();

        if(path.isEmpty()){
            leaveLoop = true;
        }

        if(arrivedAtTarget()) {
            controller.stop();
            Vector2D reachedTarget = currentTarget;
            currentTarget = otherTarget;
            otherTarget = reachedTarget;
            List<Vector2D> foundPath = PathFinder.findPath(currentNPC.getCollisionBoxGridPosition(),
                    currentTarget,
                    game.getCurrentMap());
            if(!foundPath.isEmpty()){
                currentTarget = foundPath.get(foundPath.size() - 1);
                this.path.addAll(foundPath);
            }
        }

        if(!path.isEmpty() && currentNPC.getPosition().isInRangeOf(path.get(0))){
            path.remove(0);
        }

        if(!path.isEmpty()){
            controller.moveToTarget(path.get(0), currentNPC.getPosition());
        }
    }

    private boolean arrivedAtTarget(){
        return currentTarget != null && currentNPC.getPosition().isInRangeOf(currentTarget);
    }
}