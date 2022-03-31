package ai;

import ai.state.*;
import entity.NPC;
import main.state.State;

/**
 * Handles the flow during and between states.
 */
public class AIManager {

    private AIState currentAIState;
    private String lastState;
    private final NPC entity;

    public AIState getCurrentAIState() {
        return currentAIState;
    }

    public void setCurrentAIState(AIState currentAIState) {
        this.currentAIState = currentAIState;
    }

    public AIManager(NPC entity) {
        this.entity = entity;
        lastState = "choose_next_action";
        transitionTo("choose_next_action", entity);
    }

    public void update(State state){
        currentAIState.update(state);

        if(currentAIState.shouldTransition(state)){
            transitionTo(currentAIState.getNextState(), entity);
        }
    }

    private void transitionTo(String nextState, NPC currentCharacter) {
        switch (nextState) {
            case "choose_next_action" -> {
                currentAIState = new ChooseNextAction(currentCharacter, lastState);
                return;
            }
            case "wander" -> {
                currentAIState = new Wander(currentCharacter, lastState);
                return;
            }
            default -> {
                currentAIState = new Stand(currentCharacter, lastState);
            }
        }
        lastState = nextState;
    }
}
