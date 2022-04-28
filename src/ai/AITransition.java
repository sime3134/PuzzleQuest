package ai;

import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * Implements a transition between two AI states.
 */
public final class AITransition {
    private final String nextState;
    private final AICondition condition;

    /**
     * @param nextState the state to transfer to.
     * @param condition the condition for moving to the next state.
     */
    public AITransition(String nextState, AICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    public String getNextState() {
        return nextState;
    }

    public boolean shouldTransition(Game game, NPC currentCharacter) {
        return condition.isMet(game, currentCharacter);
    }


}
