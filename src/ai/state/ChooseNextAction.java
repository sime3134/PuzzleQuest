package ai.state;

import ai.AITransition;
import entity.NPC;
import main.state.State;

import java.security.SecureRandom;

public class ChooseNextAction extends AIState{

    public ChooseNextAction(NPC currentCharacter, String lastState){
        super(currentCharacter, lastState);
    }
    @Override
    protected AITransition initializeTransition() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(0, 20);

        if(num > 0 && num < 15) {
            return new AITransition("stand", (state, currentCharacter) -> true);
        }else if(num > 14) {
            return new AITransition("wander", (state, currentCharacter) -> true);
        }else {
            return new AITransition("choose_next_action", (state, currentCharacter1) -> true);
        }
    }

    @Override
    public void update(State state) {}
}
