package story.quests;

import ai.task.GoToPosition;
import core.Vector2D;
import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;

public class CatchUpWithBill extends Quest {
    private final NPC bill;
    private final NPC akira;

    public CatchUpWithBill(Game game, int id) {
        super("Catch up with Bill","",id);
        bill = (NPC)game.getGameObjectById(19554);
        akira = (NPC) game.getGameObjectById(89759);

        initializeQuest(game);
        initializeSteps(game);
    }

    @Override
    public void prepare(Game game) {

    }

    private void initializeQuest(Game game) {
        Dialog dialog89759_13 = new Dialog();
        dialog89759_13.addLine(new DialogLine("You want to search for more parts already?\n" +
                "I do like your eagerness but I think it's time for you to take a\n" +
                "small break. Why don't you go and have a chat with bill?"));
        dialog89759_13.addLine(new DialogLine("I'm sure he would like to hear how you are getting on.\n" +
                "You'll find him somewhere around the area where you met him\n" +
                "when you first arrive. He refuses to leave his home even if the\n" +
                "surrounding land has died."));
        akira.addDialog(dialog89759_13);
        Dialog dialog19554_14 = new Dialog();
        dialog19554_14.addLine(new DialogLine("Ah, hi there. I see you are still here,\n" +
                "I thought you would've left the island by now.\n" +
                "How come you have stuck around?"));
        dialog19554_14.addLine(new DialogLine("Oh you've been searching for the parts of the medallion?\n" +
                "Any luck so far?"));
        dialog19554_14.addLine(new DialogLine("You found two parts?! That is excellent news!\n" +
                "Where did you find them?"));
        dialog19554_14.addLine(new DialogLine("A maze? I knew there must be something down there but\n" +
                "a maze? It must have been built a long time ago. Did you find anything\n" +
                "that revealed what happened to the people that has disappeared while\n" +
                "searching in that part the island?"));
        dialog19554_14.addLine(new DialogLine("Oh well, I guess the fact that you found the medallion is the most important part.\n" +
                "Come with me, I want to show you something"));
        bill.addDialog(dialog19554_14);
        //TODO Make Bill go to another position
        // bill.getBrain().addTask(new GoToPosition(bill, new Vector2D(655,1019),));
        Dialog dialog10554_15 = new Dialog(ignore -> {
            bill.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });
        dialog10554_15.addLine(new DialogLine("This is, or used to be one of my favorite spots.\n" +
                "This is where I fish, I spent the last two days fishing here and caught nothing."));
        dialog10554_15.addLine(new DialogLine("I used to be able to feed me and my son with the fish I caught from here.\n" +
                "But now even the water is dead. As you know by now this will happen to\n" +
                "the rest of the island soon if we are not able to reunite the medallion pieces."));
        dialog10554_15.addLine(new DialogLine("I don't like leaving my home, but with the current state\n" +
                "of the island, I am not able to feed myself without travelling further away to catch food.\n" +
                "Have I understood correctly if I say that you will keep searching for the missing parts?"));
        dialog10554_15.addLine(new DialogLine("Very good, the fact that you have already found two parts has\n" +
                "given me some hope. And if there is anything I can help you with do not hesitate\n" +
                "in letting me know. I will do anything to reunite the medallion and awake the Island."));
        bill.addDialog(dialog10554_15);
    }

    private void initializeSteps(Game game) {
        QuestStep step1 = new InteractWithGameObject("Talk to Akira","", game.getGameObjectById(89759));
        QuestStep step2 = new InteractWithGameObject("Catch up with Bill","",game.getGameObjectById(19554));

        addQuestStep(step1,step2);

    }


}
