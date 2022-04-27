package story.quest_steps;

import core.Vector2D;
import main.Game;
import story.QuestStep;
import story.QuestStepTransition;

public class GoToTarget extends QuestStep {

    Vector2D target;

    public GoToTarget(Vector2D target, String name) {
        super(name);
        this.target = target;
    }

    @Override
    protected QuestStepTransition initializeTransition() {
        return new QuestStepTransition(game -> arrivedAtTarget(game));
    }

    private boolean arrivedAtTarget(Game game) {
        return game.getGameState().getPlayer().getPosition().isInRangeOf(target);
    }

    @Override
    protected void update(Game game) {

    }
}
