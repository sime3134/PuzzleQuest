package ai.state;

import ai.AITransition;
import entity.NPC;
import main.state.State;

public abstract class AIState {
    private final AITransition transition;
    protected NPC currentCharacter;
    protected String lastState;

    protected AIState(NPC currentCharacter, String lastState) {
        this.currentCharacter = currentCharacter;
        this.lastState = lastState;
        this.transition = initializeTransition();
    }

    protected abstract AITransition initializeTransition();
    public abstract void update(State state);

    public boolean shouldTransition(State state) {
        return transition.shouldTransition(state, currentCharacter);
    }

    public String getNextState(){
        return transition.getNextState();
    }
}
