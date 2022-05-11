package ai.state;

import entity.NPC;
import main.Game;

public class ChooseNextAction extends AIState {
    public ChooseNextAction(NPC currentNPC, String lastState){
        super(currentNPC, lastState);
    }
    @Override
    protected AIStateTransition initializeTransition() {
        return new AIStateTransition(currentNPC.getActivity(), (state, currentNPC) -> true);
    }

    @Override
    public void update(Game game) {}
}
