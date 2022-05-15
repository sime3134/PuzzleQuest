package story.quest_steps;

import core.Vector2D;
import main.Game;
import story.QuestStepTransition;

/**
 * @author Simon Jern
 * Implements a quest step where the player should go to a certain position.
 */
public class GoToTarget extends QuestStep {

    private final Vector2D positionTarget;
    private final Vector2D worldMapTarget;

    public GoToTarget(Vector2D positionTarget, Vector2D worldMapTarget, String name, String addToDescription) {
        super(name, addToDescription);
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
    public void update(Game game) {

    }
}
