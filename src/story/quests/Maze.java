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

    public Maze(Game game, int id) {
        super("Maze", "", id);
        mazer = (NPC) game.getGameObjectById(89755);
        john = (NPC) game.getGameObjectById(100002);

        initializeQuest(game);
        initializeSteps(game);
    }

    @Override
    public void prepare(Game game) {
        game.getPauseState().setCanSave(false);
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
            this.goToNextStep(game);
            Vector2D johnNewPosition = john.getPosition();
            johnNewPosition.add(new Vector2D(5 * Settings.getTileSize(), 0));
            john.getBrain().addTask(new GoToPositionWithoutPathFinding(john, johnNewPosition,
                    ignore1 -> john.setDirection("LEFT")));
        });
        johnDialog.addLine(new DialogLine("Finally! I have been stuck down here for ages."));
        johnDialog.addLine(new DialogLine("I'm John. An explorer of kinds."));
        johnDialog.addLine(new DialogLine("Who built this maze and for what purpose?"));
        johnDialog.addLine(new DialogLine("I came down here to find answers but instead I'm left\nwith more " +
                "questions."));
        johnDialog.addLine(new DialogLine("How can nature grow below earth?"));
        johnDialog.addLine(new DialogLine("Maybe you will find the answers..."));

        TeleportScenery teleportToMaze = (TeleportScenery) game.getGameObjectById(89744);
        teleportToMaze.setCollisionAction(ignore -> {
            this.goToNextStep(game);
            teleportToMaze.setCollisionAction(null);
        });
    }

    private void initializeSteps(Game game) {
        QuestStep step0 = new WaitForExternalCompletion("The guarded hole", "I should find the hole Isak" +
                " was talking" +
                " about and\nmake my way down.");
        QuestStep step1 = new WaitForExternalCompletion("Exploring the maze",
                "I found an underground maze. I should explore it.\n");
        QuestStep step2 = new InteractWithGameObject("Growing mystery",
                "A man called John told me that nature is growing even down here.\n" +
                        "What could be the reason?", game.getGameObjectById(19424));
        QuestStep step3 = new InteractWithGameObject("Finding the way out",
                "I found a piece of the medallion. I don't know how it got here\n" +
                        "but I guess I will know with time.", game.getGameObjectById(89756));
        addQuestStep(step0,step1,step2,step3);
    }
}
