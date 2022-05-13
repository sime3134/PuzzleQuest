package story;

import main.Game;

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
