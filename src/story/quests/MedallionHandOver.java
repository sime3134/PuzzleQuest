package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.QuestStep;
import story.quest_steps.WaitForExternalCompletion;

public class MedallionHandOver extends Quest{

    private final NPC lordJoffrey;

    public MedallionHandOver(Game game, int id) {
        super("End of the journey", "I have collected all the medallion pieces.\nI should hand them over to Lord" +
                " Joffrey.", id);

        lordJoffrey = (NPC) game.getGameObjectById(89742);

        initializeQuest(game);
        initializeSteps(game);
    }

    @Override
    public void prepare(Game game) {
        lordJoffrey.getDialogManager().nextDialog();
    }

    public void initializeQuest(Game game) {
        Dialog joffreyDialog = new Dialog(ignore -> {
            this.goToNextStep(game);
            lordJoffrey.getDialogManager().nextDialog();
        });
        joffreyDialog.addLine(new DialogLine("Greetings my friend!\nHow are you finding the island?"));
        joffreyDialog.addLine(new DialogLine("You found the missing pieces?!\nHow on earth did you manage that?"));
        joffreyDialog.addLine(new DialogLine("How can we ever thank you?\nNow we will finally be able to save " +
                "the island."));
        joffreyDialog.addLine(new DialogLine("Are you carrying the pieces with you?"));
        joffreyDialog.addLine(new DialogLine("Great! Please hand them over to me and I will\nhave the " +
                "medallion restored."));
        joffreyDialog.addLine(new DialogLine("We won't ever forget this, you will always have a\n" +
                "home here if you wish to remain. I will make sure of that."));
        lordJoffrey.addDialog(joffreyDialog);

        Dialog joffreyDialog2 = new Dialog();
        joffreyDialog2.addLine(new DialogLine("We are forever in debt to you."));
        lordJoffrey.addDialog(joffreyDialog2);

    }
    public void initializeSteps(Game game) {
        QuestStep step1 = new WaitForExternalCompletion("Hand the pieces over to Lord Joffrey","");
        step1.setActionAtFinish(ignore -> game.displayNotification("Congratulations you saved the island!\n" +
                "Feel free to keep exploring the island."));
        addQuestStep(step1);
    }
}
