package ai.state;

import ai.AITransition;
import entity.NPC;
import main.Game;

public class ChooseNextAction extends AIState {
    public ChooseNextAction(NPC currentNPC, String lastState){
        super(currentNPC, lastState);
    }
    @Override
    protected AITransition initializeTransition() {
        return new AITransition(currentNPC.getActivity(), (state, currentNPC) -> true);
    }

    @Override
    public void update(Game game) {}
}
