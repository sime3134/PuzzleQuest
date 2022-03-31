package ai;

import entity.NPC;
import main.state.State;

/**
 * Implements a transition between two AI states.
 * @param nextState the state to transfer to.
 * @param condition the condition for moving to the next state.
 */
public record AITransition(String nextState, AICondition condition) {
    public String getNextState() {
        return nextState;
    }

    public boolean shouldTransition(State state, NPC currentCharacter) {
        return condition.isMet(state, currentCharacter);
    }
}
