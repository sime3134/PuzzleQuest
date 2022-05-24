package story.quests;

import ai.task.GoToPositionWithoutPathFinding;
import core.Vector2D;
import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import entity.TeleportScenery;
import main.Game;
import settings.Settings;
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;
import story.quest_steps.WaitForExternalCompletion;

/**
 * @author Johan Salomonsson
 */
public class Maze extends Quest {
    private final NPC mazer;
    private final NPC john;
    private final NPC isak;

    public Maze(Game game, int id) {
        super("Maze", "I should find the hole Isak " +
                "was talking about and make my way down.\n", id);
        mazer = (NPC) game.getGameObjectById(89755);
        john = (NPC) game.getGameObjectById(100002);
        isak = (NPC) game.getGameObjectById(89743);

        initializeQuest(game);
        initializeSteps(game);
    }

    @Override
    public void prepare(Game game) {
        game.getGameState().getDialogManager().clear();
    }

    private void initializeQuest(Game game) {

        Dialog mazerDialog = new Dialog();
        mazerDialog.addLine(new DialogLine("Hey, I think this is the way out of the maze.\n" +
                "It says on this statue that the statue will get you out of the maze."));
        mazerDialog.addLine(new DialogLine("But it doesn't work. It seems like it wants me\n" +
                "to do something else first."));
        mazer.addDialog(mazerDialog);

        Dialog johnDialog = new Dialog(ignore -> {
            game.getPauseState().setCanSave(false);
            Vector2D johnNewPosition = john.getPosition();
            johnNewPosition.add(new Vector2D(5 * Settings.getTileSize(), 0));
            john.getBrain().addTask(new GoToPositionWithoutPathFinding(john, johnNewPosition,
                    ignore1 -> {
                        john.setDirection("LEFT");
                        game.getPauseState().setCanSave(true);
                    }));
            this.goToNextStep(game);
        });
        johnDialog.addLine(new DialogLine("Finally! I have been stuck down here for ages."));
        johnDialog.addLine(new DialogLine("I'm John. An explorer of kinds."));
        johnDialog.addLine(new DialogLine("Who built this maze and for what purpose?"));
        johnDialog.addLine(new DialogLine("I came down here to find answers but instead I'm left\nwith more " +
                "questions."));
        johnDialog.addLine(new DialogLine("How can nature grow underground?"));
        johnDialog.addLine(new DialogLine("Maybe you will find the answers..."));

        john.addDialog(johnDialog);

        TeleportScenery teleportToMaze = (TeleportScenery) game.getGameObjectById(89744);
        teleportToMaze.setCollisionAction(ignore -> {
            this.goToNextStep(game);
            teleportToMaze.setCollisionAction(null);
        });
    }

    private void initializeSteps(Game game) {
        QuestStep step0 = new WaitForExternalCompletion("Find the mysterious hole", "");
        QuestStep step1 = new WaitForExternalCompletion("Explore the maze",
                "The hole led to an underground maze. I should explore it.\n");
        QuestStep step2 = new InteractWithGameObject("Find out why nature is growing underground",
                "A man called John told me that nature is growing even down here.\n" +
                        "What could be the reason?", game.getGameObjectById(34000));
        QuestStep step3 = new WaitForExternalCompletion("Find your way out",
                "I found a piece of the medallion.\n" +
                        "It's power probably made nature flourish in the maze." +
                        "\nI don't know how it got here " +
                        "but I guess I will know with time.\nTime to get out!");
        step3.setActionAtFinish(ignore -> isak.getDialogManager().nextDialog());
        addQuestStep(step0,step1,step2,step3);
    }
}
