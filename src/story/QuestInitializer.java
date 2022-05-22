package story;

import main.Game;
import story.quests.*;

/**
 * @author Simon Jern
 * Initializes all quests in the game.
 */
public class QuestInitializer {

    public void initializeQuests(Game game, QuestManager questManager) {
        questManager.addQuest(new BornAnew(game,0));
        questManager.addQuest(new Maze(game, 1));
        questManager.addQuest(new ForrestMystery(game,2));
        questManager.addQuest(new CatchUpWithBill(game,3));
        questManager.addQuest(new OceanChest(game,4));
    }
}
