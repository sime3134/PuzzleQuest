package story;

import main.Game;

/**
 * @author Simon Jern
 * Implements a condition that need to be fulfilled to finish a quest step.
 */
public interface QuestStepCondition {
    boolean isMet(Game game);
}
