package ai;

import ai.state.AIState;
import ai.state.ChooseNextAction;
import ai.state.Stand;
import ai.state.WanderLoop;
import entity.NPC;
import main.Game;

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
        lastState = "stand";
        currentAIState = new Stand(entity, lastState);
    }

    public void update(Game game){
        currentAIState.update(game);

        if(currentAIState.shouldTransition(game)){
            transitionTo(currentAIState.getNextState(), entity, game);
        }
    }

    public void transitionTo(String nextState, NPC currentCharacter, Game game) {
        switch (nextState) {
            case "choose_next_action" -> currentAIState = new ChooseNextAction(currentCharacter, lastState);
            case "wander" -> currentAIState = new WanderLoop(game, currentCharacter, lastState,
                    currentCharacter.getFirstLoopTarget(), currentCharacter.getSecondLoopTarget());
            case "stand" -> currentAIState = new Stand(currentCharacter, lastState);
            default -> System.out.println("Wrong value");
        }

        lastState = nextState;
    }
}
