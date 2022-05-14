package story;

import entity.GameObject;
import main.Game;
import story.quests.GoToTwoPositions;
import story.quests.Quest;

/**
 * @quest Simon Jern
 * Initializes all quests in the game.
 */
public class QuestInitializer {

    public void initializeQuests(Game game, QuestManager questManager) {
        Quest goToTwoPositions = new GoToTwoPositions("Follow the man to his house", 0);
        GameObject obj = game.getGameObjectById(19554);
        if(obj != null){
            questManager.addQuest(goToTwoPositions);
        }
    }
}
