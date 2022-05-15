package story.quest_steps;

import core.Action;
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

    private Action action;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
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
