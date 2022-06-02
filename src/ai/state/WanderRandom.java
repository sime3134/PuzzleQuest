package ai.state;

import ai.PathFindingThread;
import controller.NPCController;
import core.Vector2D;
import entity.NPC;
import main.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a state where the entity will walk towards a target.
 */
public class WanderRandom extends AIState{

    private final List<Vector2D> path;
    private PathFindingThread thread;
    private Vector2D target;

    public WanderRandom(NPC currentNPC, String lastState) {
        super(currentNPC, lastState);
        path = new ArrayList<>();
        currentNPC.setPath(path);
    }

    @Override
    protected AIStateTransition initializeTransition() {
        return new AIStateTransition("stand", ((state, currentNPC) -> arrivedAtTarget()));
    }

    @Override
    public void update(Game game) {
        if(thread == null || !thread.getCouldFindPath()) {
            target = game.getCurrentMap().getRandomAvailablePositionOnMap();
            thread = new PathFindingThread(game, currentNPC, path, target);
            thread.start();
        }

        NPCController controller = (NPCController) currentNPC.getController();

        if(arrivedAtTarget()) {
            controller.stop();
        }

        if(!path.isEmpty() && currentNPC.getPosition().isInRangeOf(path.get(0))){
            path.remove(0);
        }

        if(!path.isEmpty()){
            if(!currentNPC.isMoving()){
                System.out.println("target: " + path.get(0) + "   npcPos: " + currentNPC.getPosition());
            }
            controller.moveToTarget(path.get(0), currentNPC.getPosition());
        }
    }

    private boolean arrivedAtTarget(){
        return target != null && currentNPC.getPosition().isInRangeOf(target);
    }
}