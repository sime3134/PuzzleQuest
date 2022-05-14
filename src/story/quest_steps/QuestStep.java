package story.quest_steps;

import main.Game;
import story.QuestStepTransition;

/**
 * @author Simon Jern
 * Implements a step in a quest that the player has to complete to
 * proceed in the quest.
 */
public abstract class QuestStep {
    private final QuestStepTransition transition;

    protected final String name;

    public String getName() {
        return name;
    }

    protected QuestStep(String name) {
        transition = initializeTransition();
        this.name = name;
    }

    public boolean shouldTransition(Game game) {
        return transition.shouldTransition(game);
    }

    protected abstract QuestStepTransition initializeTransition();

    public abstract void update(Game game);
}
