package story;

import main.Game;
import story.quests.BornAnew;

/**
 * @author Simon Jern
 * Initializes all quests in the game.
 */
public class QuestInitializer {

    public void initializeQuests(Game game, QuestManager questManager) {
        questManager.addQuest(new BornAnew(game,0));
    }
}
