package ai;

import ai.state.*;
import entity.NPC;
import main.state.State;

/**
 * @author Simon Jern
 * Handles the flow during and between states.
 */
public class AIManager {

    private AIState currentAIState;
    private String lastState;
    private final NPC entity;

    public AIState getCurrentAIState() {
        return currentAIState;
    }

    public AIManager(NPC entity) {
        this.entity = entity;
        lastState = "wander";
        transitionTo("wander", entity);
    }

    public void update(State state){
        currentAIState.update(state);

        if(currentAIState.shouldTransition(state)){
            transitionTo(currentAIState.getNextState(), entity);
        }
    }

    public void transitionTo(String nextState, NPC currentCharacter) {
        switch (nextState) {
            case "choose_next_action" -> currentAIState = new ChooseNextAction(currentCharacter, lastState);
            case "wander" -> currentAIState = new Wander(currentCharacter, lastState);
            case "stand" -> currentAIState = new Stand(currentCharacter, lastState);
            default -> System.out.println("Wrong value");
        }

        lastState = nextState;
    }
}
