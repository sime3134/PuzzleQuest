package ai.state;

import ai.AITransition;
import core.Time;
import entity.NPC;
import main.state.State;

public class Stand extends AIState {
    private int updatesAlive;

    public Stand(NPC currentCharacter, String lastState) {
        super(currentCharacter, lastState);
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("choose_next_action", ((state, currentCharacter) -> updatesAlive >= Time.getNumberOfUpdatesFromSeconds(3)));
    }

    public void update(State state) {
        updatesAlive++;
    }
}
