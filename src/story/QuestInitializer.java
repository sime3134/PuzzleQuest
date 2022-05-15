package story;

import main.Game;
import story.quests.ANewBeginning;
import story.quests.Quest;

/**
 * @author Simon Jern
 * Initializes all quests in the game.
 */
public class QuestInitializer {

    public void initializeQuests(Game game, QuestManager questManager) {
        Quest aNewBeginning = new ANewBeginning(0);
        questManager.addQuest(aNewBeginning);
    }
}
