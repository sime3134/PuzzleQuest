package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;

public class OceanChest extends Quest{

    private final NPC mensah;
    private final NPC luna;

    public OceanChest(Game game, int id) {
        super("Ocean Chest","I was told to go to an ocean restaurant in the west.\nto get more information about" +
                " the location of" +
                " a potential medallion piece.\n", id);
        mensah = (NPC) game.getGameObjectById(90144);
        luna = (NPC) game.getGameObjectById(89736);

        initializeQuest(game);
        initializeSteps(game);
    }
    @Override
    public void prepare(Game game) {

    }

    private void initializeQuest(Game game){

        Dialog inactiveDialog = new Dialog();
        inactiveDialog.addLine(new DialogLine(""));
        inactiveDialog.setActive(false);
        luna.addDialog(inactiveDialog);

        Dialog lunaDialog = new Dialog();
        lunaDialog.addLine(new DialogLine("Hi, how can I help you?"));
        lunaDialog.addLine(new DialogLine("Oh, you're looking for the missing pieces?\n" +
                "Well I only know about one possible location. There is what we call\n" +
                "the floating island. It's a small island that floats around the island.\n"));
        lunaDialog.addLine(new DialogLine("I have no idea where it is located at the moment but I heard rumours\n" +
                "that it's somewhere along the northern coast at the moment"));
        lunaDialog.addLine(new DialogLine("There is a man called Mensah that has taken it upon\n" +
                "himself to follow the island so we don't lose it. If you find him\n" +
                "he will be able to give you more information."));
        luna.addDialog(lunaDialog);

        Dialog mensahDialog = new Dialog();
        mensahDialog.addLine(new DialogLine("That's right. I'm Mensah, if you have come to search for\n" +
                "the moving island, it's right there."));
        mensahDialog.addLine(new DialogLine("I'm certain that there is a part of the lost medallion out there.\n" +
                "the problem is how to get to it. There is a way out to it and back but we've\n" +
                "had issues with finding it. Some have made it far out but had to turn back\n" +
                "to avoid drowning."));
        mensahDialog.addLine(new DialogLine("I don't have any hints for you on where the way out is,\n" +
                "you will simply have to go and search for it yourself."));
        mensahDialog.addLine(new DialogLine("Good luck and I truly hope you'll be successful."));
        mensah.addDialog(mensahDialog);
    }

    private void initializeSteps(Game game){
        QuestStep step1 = new InteractWithGameObject("Talk to the owner of the ocean restaurant", "",
                game.getGameObjectById(89736));
        QuestStep step2 = new InteractWithGameObject("Find Mensah somewhere along the coastline", "Luna told me " +
                "about a floating island that might have a medallion piece hidden on it.\nI should talk to a guy" +
                " called Mensah for more information.\n",
                game.getGameObjectById(90144));
        QuestStep step3 = new InteractWithGameObject("Find a way out to the island","Apparently there is a " +
                "hidden path to the island.\n I'll just have to try my luck.",game.getGameObjectById(10061));
        addQuestStep(step1,step2,step3);

    }
}
