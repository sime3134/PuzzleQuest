package ai.state;

import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * Implements a state where the entity will transition to its main activity.
 */
public class DoBaseActivity extends AIState {
    public DoBaseActivity(NPC currentNPC, String lastState){
        super(currentNPC, lastState);
    }
    @Override
    protected AIStateTransition initializeTransition() {
        return new AIStateTransition(currentNPC.getActivity(), (state, currentNPC) -> true);
    }

    @Override

    public void update(Game game) {}
}
