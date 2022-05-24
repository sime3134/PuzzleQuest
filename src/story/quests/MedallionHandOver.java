package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;

public class MedallionHandOver extends Quest{

    private final NPC lordJoffrey;

    public MedallionHandOver(Game game, int id) {
        super("Medallion Hand Over", "You have collected all the medallion pieces!\n", id);

        lordJoffrey = (NPC) game.getGameObjectById(89742);

        initializeQuest(game);
        initializeSteps(game);
    }

    @Override
    public void prepare(Game game) {

    }

    public void initializeQuest(Game game) {
        Dialog joffreyDialog = new Dialog();
        joffreyDialog.addLine(new DialogLine("Greetings my friend!\nHow have you found the island?"));
        joffreyDialog.addLine(new DialogLine("You found the missing pieces?!\nHow on earth did you manage that?"));
        joffreyDialog.addLine(new DialogLine("Well we are truly grateful for your efforts, thanks to you\n" +
                "we will be able to save the island."));
        joffreyDialog.addLine(new DialogLine("Do you have the pieces on you?"));
        joffreyDialog.addLine(new DialogLine("That is great! Will you please hand them over to me?"));
        joffreyDialog.addLine(new DialogLine("Thank you sir! We won't forget this, you will always have a\n" +
                "place to stay if you wish to remain here or come visit us."));
        lordJoffrey.addDialog(joffreyDialog);

    }
    public void initializeSteps(Game game) {
        QuestStep step1 = new InteractWithGameObject("Hand the pieces over to Lord Joffrey","",
                game.getGameObjectById(89742));
        step1.setActionAtFinish(ignore -> game.displayNotification("Congratulations you saved the island!\n" +
                "Feel free to keep exploring the island."));
        addQuestStep(step1);

    }
}
