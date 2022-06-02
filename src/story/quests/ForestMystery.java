package story.quests;

import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;
import story.quest_steps.WaitForExternalCompletion;

/**
 * @author Johan Salomonsson, Simon Jern
 * Implements a quest where you look for a medallion piece in a forest.
 */
public class ForestMystery extends Quest{
    private final NPC komako;

    public ForestMystery(Game game, int id) {
        super("Forest Mystery", "Komako has a lot of knowledge about" +
                "what's been going on in the forest.\nI should talk to him. ", id);
        komako = (NPC) game.getGameObjectById(90121);

        initializeQuest(game);
        initializeSteps(game);
    }
    @Override
    public void prepare(Game game) {

    }

    private void initializeQuest(Game game) {

        Dialog inactiveDialog = new Dialog();
        inactiveDialog.addLine(new DialogLine(""));
        inactiveDialog.setActive(false);
        komako.addDialog(inactiveDialog);

        Dialog komakoDialog = new Dialog(ignore -> this.goToNextStep(game));
        komakoDialog.addLine(new DialogLine("Hi, my name is Komako, and who are you?\n" +
                "And why have you come to see me?\""));
        komakoDialog.addLine(new DialogLine("Well you are not the first one who has come asking\n" +
                "for information. And if Akira sent you she must think you\nhave a chance of finding the pieces " +
                "of the " +
                "medallion."));
        komakoDialog.addLine(new DialogLine("I don't mind giving you some information but I must\nwarn you that " +
                "it might not lead you anywhere. But it is true\nI suspect that there is a piece of the medallion " +
                "somewhere in\nthe forest."));
        komakoDialog.addLine(new DialogLine("Ever since group19 split the medallion there has been weird things\nhappening" +
                " in the forest. Mainly odd noises but we have\nalso observed odd glows of light at night."));
        komakoDialog.addLine(new DialogLine("We have not been able to determine where\nthe noises come from." +
                " Most of us agree that it seems to be coming from\nthe east part of the forest" +
                " but there are also reports of noises\nfrom other parts of the forest."));
        komakoDialog.addLine(new DialogLine("I wish I could help you more but if I knew where to find it,\n" +
                "I would've gone and collected it myself. But I hope you do try to\nsearch the forest." +
                " A fresh pair of eyes might have better luck.\nNow I will have to ask you to leave," +
                " I have other business to attend. Good luck!"));
        komako.addDialog(komakoDialog);
    }

    private void initializeSteps(Game game) {
        QuestStep step1 = new WaitForExternalCompletion("Find Komako near the southern marketplace.", "");
        QuestStep step2 = new InteractWithGameObject("Search the northern forest", "Komako is sure there is" +
                " a piece of the medallion\nsomewhere in the forest, I'll go search the area.",
                game.getGameObjectById(10656));
        addQuestStep(step1, step2);
    }
}
