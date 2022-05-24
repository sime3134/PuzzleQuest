package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;

public class OceanChest extends Quest{

    private final NPC mensah;
    private final NPC akira;
    private final NPC luna;

    public OceanChest(Game game, int id) {
        super("Ocean Chest","",id);
        mensah = (NPC) game.getGameObjectById(90144);
        akira = (NPC) game.getGameObjectById(89759);
        luna = (NPC) game.getGameObjectById(89736);

        initializeQuest(game);
        initializeSteps(game);
    }
    @Override
    public void prepare(Game game) {

    }

    private void initializeQuest(Game game){

        Dialog dialog89736_17 = new Dialog();
        dialog89736_17.addLine(new DialogLine("Hi, how can I help you?"));
        dialog89736_17.addLine(new DialogLine("Oh, you're looking for the missing pieces?\n" +
                "Well I only know about one possible location. There is what we call\n" +
                "the floating island. It's a small island that floats around the island.\n" +
                "I have no idea where it is located at the moment."));
        dialog89736_17.addLine(new DialogLine("There is a man called Mensah that has taken it upon\n" +
                "himself to follow the island so we don't lose it. If you find him\n" +
                "he will be able to give you more information.\nHe'll be somewhere along the coast line."));
        luna.addDialog(dialog89736_17);

        Dialog dialog90144_18 = new Dialog();
        dialog90144_18.addLine(new DialogLine("That's right I'm Mensah, If you have come to search for\n" +
                "the moving island, it's right there."));
        dialog90144_18.addLine(new DialogLine("I'm certain that there is a part of the lost medallion out there.\n" +
                "the problem is how to get it. There is a way out to it and back but we've\n" +
                "had issues with finding it. Some have made it far out but had to turn back\n" +
                "to avoid drowning."));
        dialog90144_18.addLine(new DialogLine("I don't have any hints for you on where the way out is,\n" +
                "you will simply have to go and search for it yourself."));
        dialog90144_18.addLine(new DialogLine("Good luck and I truly hope you'll be successful"));
        mensah.addDialog(dialog90144_18);

        Dialog dialog89759_19 = new Dialog();
        dialog89759_19.addLine(new DialogLine("You found another one?\n" +
                "Great work! Now we are getting close to saving the island"));
        akira.addDialog(dialog89759_19);


    }

    private void initializeSteps(Game game){
        QuestStep step1 = new InteractWithGameObject("Talk to Akira","",game.getGameObjectById(89759));
        QuestStep step2 = new InteractWithGameObject("Talk to the staff at the ocean restaurant", "Go to" +
                " the ocean restaurant in the west\nand speak to the staff.",game.getGameObjectById(89736));
        QuestStep step3 = new InteractWithGameObject("Find Mensah", " Find Mensah somewhere along\n" +
                "the coastline and speak to him.",game.getGameObjectById(90144));
        QuestStep step4 = new InteractWithGameObject("Get to the island"," Find a way out to the small\n" +
                "Island and open the chest",game.getGameObjectById(10061));
        QuestStep step5 = new InteractWithGameObject("Talk to Akira", " Get back to the main" +
                "island and\n talk to Akira",game.getGameObjectById(89759));
        addQuestStep(step1,step2,step3,step4,step5);

    }
}
