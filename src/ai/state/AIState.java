package ai.state;

import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * An entity with an AI can be in several states representing their current action and goal.
 * This abstract class is the skeleton for the actual doable states like Wander, Stand etc.
 * The difference between an AITask and AIState is that an AIState will switch to another AIState when it's
 * finished but an AITask will just end.
 */
public abstract class AIState {
    private final AIStateTransition transition;
    protected NPC currentNPC;
    protected String lastState;

    protected AIState(NPC currentNPC, String lastState) {
        this.currentNPC = currentNPC;
        this.lastState = lastState;
        this.transition = initializeTransition();
    }

    protected abstract AIStateTransition initializeTransition();
    public abstract void update(Game game);

    public boolean shouldTransition(Game game) {
        return transition.shouldTransition(game, currentNPC);
    }

    public String getNextState(){
        return transition.getNextState();
    }
}
