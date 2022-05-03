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
public class WanderRandom extends AIState{

    private List<Vector2D> path;

    private PathFindingThread thread;
    private Vector2D target;

    public WanderRandom(NPC currentNPC, String lastState) {
        super(currentNPC, lastState);
        path = new ArrayList<>();
        currentNPC.setPath(path);
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("choose_next_action", ((state, currentNPC) -> arrivedAtTarget()));
    }

    @Override
    public void update(Game game) {
        if(target == null && thread == null) {
            thread = new PathFindingThread(game);
            thread.start();
        }

        NPCController controller = (NPCController) currentNPC.getController();

        if(arrivedAtTarget()) {
            controller.stop();
            thread = null;
        }

        if(!path.isEmpty() && currentNPC.getPosition().isInRangeOf(path.get(0))){
            path.remove(0);
        }

        if(!path.isEmpty()){
            controller.moveToTarget(path.get(0), currentNPC.getPosition());
        }
    }

    private boolean arrivedAtTarget(){
        return target != null && currentNPC.getPosition().isInRangeOf(target);
    }

    public class PathFindingThread extends Thread{
        Game game;
        public PathFindingThread(Game game){
            this.game = game;
        }
        @Override
        public void run() {
            List<Vector2D> foundPath = PathFinder.findPath(currentNPC.getCollisionBoxGridPosition(),
                    game.getCurrentMap().getRandomAvailablePositionOnMap(),
                    game.getCurrentMap());
            if(!foundPath.isEmpty()){
                target = foundPath.get(foundPath.size() - 1);
                path.addAll(foundPath);
            }
        }
    }
}