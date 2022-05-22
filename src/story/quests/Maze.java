package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;

/**
 * @author Johan Salomonsson
 */
public class Maze extends Quest {

    private final NPC akira;
    private final NPC mazer;

    public Maze(Game game, int id) {
        super("Maze", "", id);
        akira = (NPC) game.getGameObjectById(89759);
        mazer = (NPC) game.getGameObjectById(89755);

        initializeQuest(game);
        initializeSteps(game);
    }

    @Override
    public void prepare(Game game) {
        game.getPauseState().setCanSave(false);
        game.getGameState().getDialogManager().clear();
    }

    private void initializeQuest(Game game) {
        Dialog dialog89759_8 = new Dialog(ignore -> {
            akira.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });
        dialog89759_8.addLine(new DialogLine("So you are ready to start the search? Well I am glad that\n" +
                "you are trying to help us. Like I said before I don't\n" + "know the locations of the missing pieces" +
                " but a good\n" + "place to start looking is near Isak's home."));
        dialog89759_8.addLine(new DialogLine("Search for something that can take you underground.\n" +
                "Well, get going, every second wasted costs this island life."));
        akira.addDialog(dialog89759_8);
        Dialog dialog89755_9 = new Dialog();
            dialog89755_9.addLine(new DialogLine("You have found the end off the maze.\n" +
                    "If you have found the missing medallion piece in here you are free to\n leave. If not " +
                    "I'm afraid I can't let you out until you find it."));
            dialog89755_9.addLine(new DialogLine("After all the survival of the island rests on finding the\n" +
                    "missing pieces. When you have found the piece of the medallion just\n" + "interact with this" +
                    " statue and you will be transported back to the island."));
            mazer.addDialog(dialog89755_9);
        Dialog dialog89759_10 = new Dialog(ignore -> {
            akira.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });
            dialog89759_10.addLine(new DialogLine("I'm glad to see you made it back alive, I had my doubts.\n" +
                    "We have lost a few people down there before. So tell me\n" + "did you find anything?"));
        dialog89759_10.addLine(new DialogLine("Oh you found a part of the medallion? Excellent. \n" +
                "This is a step in the right direction but just one piece will not get us far.\n" +
                "If you want to keep looking just let me know and I'll see if I can help you."));
        akira.addDialog(dialog89759_10);
    }

    private void initializeSteps(Game game) {
        QuestStep step1 = new InteractWithGameObject("Talk to Akira", "",
                game.getGameObjectById(89759));
        QuestStep step2 = new InteractWithGameObject("Go to Isak's home", "Search for a way underground\n" +
                "and look for a medallion piece.",game.getGameObjectById(19424));
        QuestStep step3 = new InteractWithGameObject("Find your way out of the maze",
                "You found a part from the missing medallion.\nNow you need to find the way out of the maze",
                game.getGameObjectById(89756));
        QuestStep step4 = new InteractWithGameObject("Talk to Akira",
                "You found a piece of the medallion, go tell Akira about it", game.getGameObjectById(89759));
        addQuestStep(step1,step2,step3,step4);
    }
}
