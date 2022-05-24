package story;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import entity.Scenery;
import main.Game;
import story.quests.*;

/**
 * @author Simon Jern
 * Initializes all quests in the game.
 */
public class LoreInitializer {

    public void initializeQuests(Game game, QuestManager questManager) {
        questManager.addQuest(new BornAnew(game,0));
        questManager.addQuest(new Maze(game, 1));
        questManager.addQuest(new ForestMystery(game,2));
        questManager.addQuest(new CatchUpWithBill(game,3));
        questManager.addQuest(new OceanChest(game,4));

        game.addSceneryToOverwrite((Scenery) game.getGameObjectById(34000));
        game.addSceneryToOverwrite((Scenery) game.getGameObjectById(10061));
        game.addSceneryToOverwrite((Scenery) game.getGameObjectById(10656));

        NPC jester = (NPC) game.getGameObjectById(100000);
        NPC locky = (NPC) game.getGameObjectById(90147);
        NPC stocky = (NPC) game.getGameObjectById(90149);
        Dialog holeGuardiansDialog = new Dialog();

        holeGuardiansDialog.addLine(new DialogLine("You can't go down here, a lot of people has gotten lost here."));

        jester.addDialog(holeGuardiansDialog);
        locky.addDialog(holeGuardiansDialog);
        stocky.addDialog(holeGuardiansDialog);

        Dialog lockyDialog = new Dialog();
        lockyDialog.addLine(new DialogLine("If you're really going down here, be careful."));
        locky.addDialog(lockyDialog);

        Dialog stockyDialog = new Dialog();
        stockyDialog.addLine(new DialogLine("Jester went on a lunch break.."));
        stocky.addDialog(stockyDialog);

        Dialog emptyDialog = new Dialog();
        emptyDialog.addLine(new DialogLine(""));
        emptyDialog.setActive(false);
        jester.addDialog(emptyDialog);
    }
}
