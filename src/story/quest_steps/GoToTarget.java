package story.quest_steps;

import core.Vector2D;
import main.Game;
import story.QuestStep;
import story.QuestStepTransition;

public class GoToTarget extends QuestStep {

    private Vector2D positionTarget;
    private Vector2D worldMapTarget;

    public GoToTarget(Vector2D positionTarget, Vector2D worldMapTarget, String name) {
        super(name);
        this.positionTarget = positionTarget;
        this.worldMapTarget = worldMapTarget;
    }

    @Override
    protected QuestStepTransition initializeTransition() {
        return new QuestStepTransition(game -> arrivedAtTarget(game));
    }

    private boolean arrivedAtTarget(Game game) {
        return game.getGameState().getPlayer().getWorldMapPosition().equals(worldMapTarget) &&
                game.getGameState().getPlayer().getPosition().isInQuestRangeOf(positionTarget);
    }

    @Override
    protected void update(Game game) {

    }
}
