package ai;

import entity.NPC;
import main.state.State;

public interface AICondition {
    boolean isMet(State state, NPC currentCharacter);
}
