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

    protected String name;

    protected String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    protected QuestStep(String name, String description) {
        transition = initializeTransition();
        this.name = name;
        this.description = description;
    }

    public boolean shouldTransition(Game game) {
        return transition.shouldTransition(game);
    }

    protected abstract QuestStepTransition initializeTransition();

    public abstract void update(Game game);
}
