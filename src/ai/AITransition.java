package ai;

import entity.NPC;
import main.state.State;

public record AITransition(String nextState, AICondition condition) {
    public String getNextState() {
        return nextState;
    }

    public boolean shouldTransition(State state, NPC currentCharacter) {
        return condition.isMet(state, currentCharacter);
    }
}
