package ai.state;

import ai.AITransition;
import core.Time;
import entity.NPC;
import main.state.State;

/**
 * Implements a state where the entity will simply stand still for a set amount of time.
 */
public class Stand extends AIState {
    private int numberOfUpdatesWaiting;

    public Stand(NPC currentCharacter, String lastState) {
        super(currentCharacter, lastState);
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("choose_next_action", ((state, currentCharacter) -> numberOfUpdatesWaiting >= Time.getNumberOfUpdatesFromSeconds(3)));
    }

    public void update(State state) {
        numberOfUpdatesWaiting++;
    }
}
