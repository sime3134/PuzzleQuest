package ai.state;

import ai.AITransition;
import controller.NPCController;
import core.Vector2D;
import entity.NPC;
import main.state.State;
import utilities.Buffer;

public class Wander extends AIState{

    Buffer<Vector2D> targets;

    public Wander(NPC currentCharacter, String lastState) {
        super(currentCharacter, lastState);
        targets = currentCharacter.getTargets();
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("choose_next_action", ((state, currentCharacter) -> arrivedAtTarget()));
    }

    @Override
    public void update(State state) {
        if(targets.isEmpty()) {
            targets.add(state.getCurrentMap().getRandomPositionOnMap());
        }

        NPCController controller = (NPCController) currentCharacter.getController();
        controller.moveToTarget(targets.peek(), currentCharacter.getPosition());

        if(arrivedAtTarget()) {
            targets.get();
            controller.stop();
        }
    }

    private boolean arrivedAtTarget(){
        return currentCharacter.getPosition().isInRangeOf(targets.peek());
    }
}
