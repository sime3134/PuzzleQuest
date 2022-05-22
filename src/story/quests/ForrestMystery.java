package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;

public class ForrestMystery extends Quest{

    private final NPC akira;
    private final NPC komako;

    public ForrestMystery(Game game, int id) {
        super("Forrest Mystery", "", id);
        akira = (NPC) game.getGameObjectById(89759);
        komako = (NPC) game.getGameObjectById(90121);

        initializeQuest(game);
        initializeSteps(game);
    }
    @Override
    public void prepare(Game game) {

    }

    private void initializeQuest(Game game) {
        Dialog dialog89759_11 = new Dialog();
        dialog89759_11.addLine(new DialogLine("Are you ready to continue the search for the missing parts?"));
        dialog89759_11.addLine(new DialogLine("That's good to hear, I have some ideas about where the next\n" +
                "part could be. We have long been hearing odd sounds\nfrom the forrest. We suspect that might" +
                " be connected to a part of the medallion."));
        dialog89759_11.addLine(new DialogLine("You need to go and see Komako. He's been searching in the\n" +
                "forrest longer than anyone. He might be able to give you some help"));
        dialog89759_11.addLine(new DialogLine("He lives down by the marketplace in the south,\n" +
                "you should be able to find him down there."));
        akira.addDialog(dialog89759_11);

        Dialog dialog90121_12 = new Dialog();
        dialog90121_12.addLine(new DialogLine("Yes, my name is Komako, and who are you?\n" +
                "And why have you come to see me?\""));
        dialog90121_12.addLine(new DialogLine("Well you are not the first one who have come asking\n" +
                "for information. And if Akira sent you she must think you\nhave a chance of finding a piece of the " +
                "medallion."));
        dialog90121_12.addLine(new DialogLine("I don't mind giving you some information but I must\nwarn you that " +
                "it might not lead you anywhere. But it is true\nI suspect that there is a piece of the medallion " +
                "somewhere in\n the forrest. Why?"));
        dialog90121_12.addLine(new DialogLine("Ever since group19 split the medallion there has been weird things happening\n " +
                "in the forrest. Mainly odd noises but we have also observed\nodd glows of light at night. We have not" +
                " been able to determine where\nthe noises come from. Most of us agree that it seems to be coming from\n" +
                "the east part of the forrest but there are also reports of noises\n from other parts of the forrest."));
        dialog90121_12.addLine(new DialogLine("I wish I could help you more but if I knew where to find it,\n" +
                "I would've gone and collected it myself. But I hope you do try to\nsearch the forrest." +
                " A fresh pair of eyes might have more luck.\nNow I will have to ask you to leave," +
                " I have other business to attend. Good luck!"));
        komako.addDialog(dialog90121_12);
    }

    private void initializeSteps(Game game) {
        QuestStep step1 = new InteractWithGameObject("Talk to Akira", "",game.getGameObjectById(89759));
        QuestStep step2 = new InteractWithGameObject("Find Komako", "Komako has a lot of knowledge about\n" +
                "what's been going on in\nthe forrest. Talk to him and gather information",game.getGameObjectById(90121));
        QuestStep step3 = new InteractWithGameObject("Search the northern forrest", "Komako is sure there is\n" +
                "a part of the medallion\nsomewhere in the forrest.", game.getGameObjectById(10656));
        QuestStep step4 = new InteractWithGameObject("Tell Akira you found a medallion", "You found a part of\n" +
                "the medallion", game.getGameObjectById(89759));
        addQuestStep(step1,step2,step3,step4);
    }
}
