package ai.state;

import ai.AITransition;
import entity.NPC;
import main.Game;

import java.security.SecureRandom;

/**
 * @author Simon Jern
 * Implements a state where the entity will choose his next action/state.
 */
public class ChooseNextAction extends AIState{

    public ChooseNextAction(NPC currentNPC, String lastState){
        super(currentNPC, lastState);
    }
    @Override
    protected AITransition initializeTransition() {
        SecureRandom random = new SecureRandom();

        if(currentNPC.getDoRandomAction().getValue()) {
            int num = random.nextInt(0, 20);

            if (num > 0 && num < 15) {
                return new AITransition("stand", (state, currentNPC) -> true);
            } else if (num > 14) {
                return new AITransition("wander", (state, currentNPC) -> true);
            } else {
                return new AITransition("choose_next_action", (state, currentNPC) -> true);
            }
        }

        return new AITransition(lastState, (state, currentNPC) -> true);
    }

    @Override
    public void update(Game game) {}
}
