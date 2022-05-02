package ai;

import ai.state.*;
import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * Handles the flow during and between states.
 */
public class AIManager {
    private AIState currentAIState;
    private String lastState;
    private final NPC currentNPC;

    public AIState getCurrentAIState() {
        return currentAIState;
    }

    public AIManager(NPC currentNPC) {
        this.currentNPC = currentNPC;
        lastState = "choose_next_action";
        currentAIState = new ChooseNextAction(currentNPC, lastState);
    }

    public void update(Game game){
        currentAIState.update(game);

        if(currentAIState.shouldTransition(game)){
            transitionTo(currentAIState.getNextState(), currentNPC, game);
        }
    }

    public void transitionTo(String nextState, NPC currentNPC, Game game) {
        switch (nextState) {
            case "choose_next_action" -> currentAIState = new ChooseNextAction(currentNPC, lastState);
            case "random_action" -> currentAIState = new RandomAction(currentNPC, lastState);
            case "wander_random" -> currentAIState = new WanderRandom(currentNPC, lastState);
            case "wander_loop" -> currentAIState = new WanderLoop(game, currentNPC, lastState,
                    currentNPC.getFirstLoopTarget(), currentNPC.getSecondLoopTarget());
            case "stand" -> currentAIState = new Stand(currentNPC, lastState);
            default -> System.out.println("Illegal value in AIManager");
        }

        lastState = nextState;
    }
}
