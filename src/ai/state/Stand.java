package ai.state;

import ai.AITransition;
import entity.NPC;
import main.state.State;

/**
 * @author Simon Jern
 * Implements a state where the entity will simply stand still for a set amount of time.
 */
public class Stand extends AIState {
    private int numberOfUpdatesWaiting;

    public Stand(NPC currentCharacter, String lastState) {
        super(currentCharacter, lastState);
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("choose_next_action", ((state, currentCharacter) -> numberOfUpdatesWaiting >= state.getTime().getUpdatesFromSeconds(3)));
    }

    public void update(State state) {
        numberOfUpdatesWaiting++;
    }
}
