package story;

import ai.task.GoToPosition;
import ai.task.GoToPositionWithoutPathFinding;
import core.Vector2D;
import dialog.Dialog;
import dialog.DialogInitializer;
import dialog.DialogLine;
import entity.NPC;
import main.Game;
import map.GameMap;

public class StoryInitializer {

    private StoryInitializer() {}

    public static void initializeIntroDialog(Game game) {
        Dialog intro = new Dialog(ignore -> {
            game.getGameState().getDialogManager().clear();
            game.getGameState().getQuestManager().startQuest(0);
            NPC npc = (NPC)game.getGameObjectById(19554);
            npc.getBrain().addTask(new GoToPosition(npc, new Vector2D(613, 1968),

                    ignore2 -> npc.getBrain().addTask(new GoToPositionWithoutPathFinding(npc,
                            new Vector2D(576, 1948),

                            ignore3 -> {
                                GameMap map = game.getMapManager().getByName("house1");
                                map.addNPC(npc);
                                game.addGameObjectToRemove(npc);
                                npc.setPosition(map.getStartingPosition());
                                npc.setActivity("wander_random");
                            }
                    ))));

            game.getGameState().setNonNPCDialogActive(false);
        });
        intro.addLine(new DialogLine("Hey! Wake up..."));
        intro.addLine(new DialogLine("Are you alive?"));
        intro.addLine(new DialogLine("...", ignore -> game.setShowBlackScreen(false)));
        intro.addLine(new DialogLine("Ah.. You finally woke up! How are you feeling?",
                ignore -> game.getGameState().getPlayer().setDirection("DOWN")));
        intro.addLine(new DialogLine("I just found you lying here.\n" +
                "It seems like you washed ashore."));
        intro.addLine(new DialogLine("Come with me to my house and warm up to start with."));
        game.getGameState().getDialogManager().addDialog(intro);
    }

    public static void initializeDialogs(Game game) {
        new DialogInitializer(game);
    }
}
