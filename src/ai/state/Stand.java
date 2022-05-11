package ai.state;

import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * Implements a state where the entity will simply stand still for a set amount of time.
 */
public class Stand extends AIState {
    private int numberOfUpdatesWaiting;

    public Stand(NPC currentCharacter, String lastState) {
        super(currentCharacter, lastState);
    }

    @Override
    protected AIStateTransition initializeTransition() {
        return new AIStateTransition("choose_next_action",
                ((game, currentCharacter) -> numberOfUpdatesWaiting >= game.getTime().getUpdatesFromSeconds(3)));
    }

    public void update(Game game) {
        numberOfUpdatesWaiting++;
    }
}
