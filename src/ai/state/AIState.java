package ai.state;

import ai.AITransition;
import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * An entity with an AI can be in several states representing their current action and goal.
 * This abstract class is the skeleton for the actual doable states like Wander, Stand etc.
 */
public abstract class AIState {
    private final AITransition transition;
    protected NPC currentNPC;
    protected String lastState;

    protected AIState(NPC currentNPC, String lastState) {
        this.currentNPC = currentNPC;
        this.lastState = lastState;
        this.transition = initializeTransition();
    }

    protected abstract AITransition initializeTransition();
    public abstract void update(Game game);

    public boolean shouldTransition(Game game) {
        return transition.shouldTransition(game, currentNPC);
    }

    public String getNextState(){
        return transition.getNextState();
    }
}
