package story;

import ai.task.GoToPosition;
import ai.task.GoToPositionWithoutPathFinding;
import core.Value;
import core.Vector2D;
import dialog.Dialog;
import dialog.DialogLine;
import entity.NPC;
import entity.Player;
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
            NPC npcBill = (NPC)game.getGameObjectById(19554);
            npcBill.getBrain().addTask(new GoToPosition(npcBill, new Vector2D(613, 1968),

                    ignore2 -> {
                        npcBill.getBrain().addTask(new GoToPositionWithoutPathFinding(npcBill,
                                new Vector2D(576, 1948),

                                ignore3 -> {
                                    moveNpcToOtherMap(game, npcBill, "house1");
                                    npcBill.setActivity("wander_random");
                                    addBillFirstDialog(game, npcBill);
                                }
                        ));
                        changeScenerySprite(game, 29880, "blue_open_door2");
                        activeTeleportScenery(game, 29877);
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

    private static void changeScenerySprite(Game game, long id, String newSpriteName) {
        Scenery door = (Scenery) game.getGameObjectById(id);
        door.setSprite(game.getContent().getImage(newSpriteName));
        door.setName(newSpriteName);
        game.addSceneryToOverwrite(door);
    }

    private static void activeTeleportScenery(Game game, long id) {
        TeleportScenery teleport = (TeleportScenery) game.getGameObjectById(id);
        teleport.setActive(new Value<>(true));
        game.addSceneryToOverwrite(teleport);
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
    }

    private static void addBillFirstDialog(Game game, NPC npcBill){
        Dialog dialog19554_1 = new Dialog();

        dialog19554_1.addLine(new DialogLine("My name is Bill by the way. \nWelcome to my home."));
        dialog19554_1.addLine(new DialogLine("I can see that you are exhausted.. \nGet some rest in the bed " +
                "over there and we will talk tomorrow.", ignore -> {
            Scenery bed = (Scenery) game.getGameObjectById(89555);
            bed.setAction(ignore4 -> game.showBlackScreen(2000, game,
                    ignore5 -> {
                        bed.setAction(null);
                        npcBill.getDialogManager().nextDialog();
                        Player player = game.getGameState().getPlayer();
                        player.setPosition(new Vector2D(111, 165));
                        player.setDirection("RIGHT");
                        npcBill.setActivity("stand");
                        npcBill.setCanMove(false);
                        npcBill.getBrain().transitionTo("stand", npcBill, game);
                        npcBill.setPosition(new Vector2D(145, 165));
                        npcBill.setDirection("LEFT");
                        npcBill.executePlayerAction(game);
                    }
            ));
        }));

        npcBill.addDialog(dialog19554_1);

        Dialog dialog19554_2 = new Dialog(ignore -> npcBill.getDialogManager().nextDialog());

        dialog19554_2.addLine(new DialogLine("Good morning. Did you sleep well?"));
        dialog19554_2.addLine(new DialogLine("You have bad luck washing ashore on this island.\n" +
                "Did you notice how the nature has died outside?"));
        dialog19554_2.addLine(new DialogLine("The island is dying after Group19 stole\n" +
                "the medallion keeping it in balance."));
        dialog19554_2.addLine(new DialogLine("What medallion you say?"));
        dialog19554_2.addLine(new DialogLine("The medallion contains a stone of life, I'm sure you have heard " +
                "of them?"));
        dialog19554_2.addLine(new DialogLine("The legends say that this island was originally just dead rock."));
        dialog19554_2.addLine(new DialogLine("But one day an adventurer found the stone of life in the " +
                "medallion\nand with it, created life on the island."));
        dialog19554_2.addLine(new DialogLine("And now, without it, it is once again dying."));
        dialog19554_2.addLine(new DialogLine("If you want to hear more about it, talk to the king in his\n" +
                "castle south-east of here.", ignore -> npcBill.setActivity("wander_random")
        ));

        npcBill.addDialog(dialog19554_2);

        Dialog dialog19554_3 = new Dialog();

        dialog19554_3.addLine(new DialogLine("If you want to hear more about the missing medallion\n" +
                "talk to the king in his castle south-east of here."));

        npcBill.addDialog(dialog19554_3);
    }
}
