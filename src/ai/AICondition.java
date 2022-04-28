package ai;

import entity.NPC;
import main.Game;

/**
 * @author Simon Jern
 * An interface for a condition that will make an entity go to his next state.
 */
public interface AICondition {
    boolean isMet(Game game, NPC currentCharacter);
}
