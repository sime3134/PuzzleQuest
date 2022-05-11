package ai.state;

import entity.NPC;
import main.Game;

import java.security.SecureRandom;

/**
 * @author Simon Jern
 * Implements a state where the entity will choose his next action/state.
 */
public class RandomAction extends AIState{

    public RandomAction(NPC currentNPC, String lastState){
        super(currentNPC, lastState);
    }
    @Override
    protected AIStateTransition initializeTransition() {
        SecureRandom random = new SecureRandom();

            int num = random.nextInt(0, 20);

            if (num > 0 && num < 15) {
                return new AIStateTransition("stand", (state, currentNPC) -> true);
            } else if (num > 14) {
                return new AIStateTransition("wander_random", (state, currentNPC) -> true);
            } else {
                return new AIStateTransition("random_action", (state, currentNPC) -> true);
            }

    }

    @Override
    public void update(Game game) {}
}
