package story.quest_steps;

import main.Game;
import story.QuestStepTransition;

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
