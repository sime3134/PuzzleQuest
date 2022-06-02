package story.quest_steps;

import main.Game;
import story.QuestStepTransition;

/**
 * @author Simon Jern, Johan Salomonsson
 * Implements the logic that a quest step that waits for another class or method to contiune the quest,
 * instead of having a specifik action to go to the next step.
 */
public class WaitForExternalCompletion extends QuestStep{

    public WaitForExternalCompletion(String name, String description) {
        super(name, description);
    }

    @Override
    protected QuestStepTransition initializeTransition() {
        return new QuestStepTransition(game -> false);
    }

    @Override
    public void update(Game game) {

    }

}
