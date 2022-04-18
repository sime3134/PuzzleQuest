package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Vector2D;
import entity.NPC;
import main.state.State;
import map.PathFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a state where the entity will walk towards a goal.
 */
public class Wander extends AIState{

    List<Vector2D> path;
    private Vector2D target;

    public Wander(NPC currentCharacter, String lastState) {
        super(currentCharacter, lastState);
        path = new ArrayList<>();
        currentCharacter.setPath(path);
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("wander", ((state, currentCharacter) -> arrivedAtTarget()));
    }

    @Override
    public void update(State state) {
        if(target == null) {
            List<Vector2D> foundPath = PathFinder.findPath(state.getGameObjects(),
                    currentCharacter.getCollisionBoxGridPosition(),
                    state.getCurrentMap().getRandomAvailablePositionOnMap(state.getGameObjects()), state.getCurrentMap());
            if(!foundPath.isEmpty()){
                target = foundPath.get(foundPath.size() - 1);
                this.path.addAll(foundPath);
            }
        }

        NPCController controller = (NPCController) currentCharacter.getController();

        if(arrivedAtTarget()) {
            controller.stop();
        }

        if(!path.isEmpty() && currentCharacter.getPosition().isInRangeOf(path.get(0))){
            path.remove(0);
        }

        if(!path.isEmpty()){
            controller.moveToTarget(path.get(0), currentCharacter.getPosition());
        }
    }

    private boolean arrivedAtTarget(){
        return target != null && currentCharacter.getPosition().isInRangeOf(target);
    }
}
