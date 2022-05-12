package story;

import ai.task.GoToPosition;
import ai.task.GoToPositionWithoutPathFinding;
import core.Value;
import core.Vector2D;
import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import entity.Scenery;
import entity.TeleportScenery;
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

                    ignore2 -> {
                        npc.getBrain().addTask(new GoToPositionWithoutPathFinding(npc,
                                new Vector2D(576, 1948),

                                ignore3 -> {

                                    moveNpcToOtherMap(game, npc, "house1");
                                    npc.setActivity("wander_random");
                                }
                        ));
                        Scenery door = (Scenery)game.getGameObjectById(29880);
                        door.setSprite(game.getContent().getImage("blue_open_door2"));
                        door.setName("blue_open_door2");
                        game.addSceneryToOverwrite(door);
                        TeleportScenery teleport = (TeleportScenery)game.getGameObjectById(29877);
                        teleport.setActive(new Value<>(true));
                        game.addSceneryToOverwrite(teleport);
                    }));

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

    private static void moveNpcToOtherMap(Game game, NPC npc, String nameOfMapToMoveTo) {
        GameMap map = game.getMapManager().getByName(nameOfMapToMoveTo);
        map.addNPC(npc);
        game.getCurrentMap().removeNPC(npc);
        npc.setCurrentMapName(map.getName());
        game.addGameObjectToRemove(npc);
        npc.setPosition(map.getStartingPosition());
    }

    public static void initializeDialogs(Game game) {
        NPC npc = (NPC)game.getGameObjectById(19554);
        Dialog dialog19554_1 = new Dialog(ignore -> npc.getDialogManager().nextDialog());

        dialog19554_1.addLine(new DialogLine("My name is Bill by the way. \nWelcome to my home."));

        npc.addDialog(dialog19554_1);

        Dialog dialog19554_2 = new Dialog();

        dialog19554_2.addLine(new DialogLine("I can see that you are exhausted.. \nGet some rest and we will talk " +
                "tomorrow."));

        npc.addDialog(dialog19554_2);
    }
}
