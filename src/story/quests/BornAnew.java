package story.quests;

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
import story.quest_steps.InteractWithGameObject;
import story.quest_steps.QuestStep;
import story.quest_steps.WaitForExternalCompletion;

import java.util.concurrent.TimeUnit;

/**
 * @author Simon Jern, Johan Salomonsson
 * Implements a quest where the player should go to two different locations.
 */
public class BornAnew extends Quest {

    private final NPC bill;
    private final NPC lordGodfrey;
    private final NPC isak;

    public BornAnew(Game game, int id) {
        super("Born anew", "I woke up on an unknown island and can't even remember my own name.\n" +
                "An old man helped me after I woke up.", id);
        bill = (NPC)game.getGameObjectById(19554);
        lordGodfrey = (NPC) game.getGameObjectById(89742);
        isak = (NPC) game.getGameObjectById(89743);
        initializeQuest(game);
        initializeSteps(game);
    }

    public void prepare(Game game){
        game.setShowBlackScreen(true);
        game.getPauseState().setCanSave(false);
        Dialog intro = new Dialog(ignore -> {
            game.getGameState().getDialogManager().clear();
            bill.getBrain().addTask(new GoToPosition(bill, new Vector2D(613, 1968),

                    ignore2 -> {
                        bill.getBrain().addTask(new GoToPositionWithoutPathFinding(bill,
                                new Vector2D(576, 1948),

                                ignore3 -> {
                                    moveNpcToOtherMap(game, bill, "house1");
                                    bill.setActivity("wander_random");
                                    bill.getDialogManager().getCurrentDialog().setActive(true);
                                }
                        ));
                        changeScenerySprite(game, 29880, "blue_open_door2");
                        activateTeleportScenery(game, 29877);
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

    private void initializeQuest(Game game) {
        Dialog dialog19554_1 = new Dialog(ignore -> {
            bill.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });

        dialog19554_1.addLine(new DialogLine("My name is Bill by the way. \nWelcome to my home."));
        dialog19554_1.addLine(new DialogLine("I can see that you are exhausted.. \nGet some rest in the bed " +
                "over there and we will talk tomorrow."));
        dialog19554_1.setActive(false);

        bill.addDialog(dialog19554_1);

        Dialog dialog19554_2 = new Dialog();
        dialog19554_2.addLine(new DialogLine("I can see that you are exhausted.. \nGet some rest in the bed " +
                "over there and we will talk tomorrow."));

        bill.addDialog(dialog19554_2);

        Dialog dialog19554_3 = new Dialog(ignore -> {
            bill.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });

        dialog19554_3.addLine(new DialogLine("Good morning. Did you sleep well?"));
        dialog19554_3.addLine(new DialogLine("You have bad luck washing ashore on this island.\n" +
                "Did you notice how the nature has died outside?"));
        dialog19554_3.addLine(new DialogLine("The island is dying after Group19 stole\n" +
                "the medallion keeping it in balance."));
        dialog19554_3.addLine(new DialogLine("What medallion you say?"));
        dialog19554_3.addLine(new DialogLine("The medallion contains a stone of life, I'm sure you have heard " +
                "of them?"));
        dialog19554_3.addLine(new DialogLine("The legends say that this island was originally just dead rock."));
        dialog19554_3.addLine(new DialogLine("But one day an adventurer found a stone of life " +
                "\nand with it, created life on the island."));
        dialog19554_3.addLine(new DialogLine("And now, without it, it is once again dying."));
        dialog19554_3.addLine(new DialogLine("If you want to hear more about it, talk to the king in his\n" +
                "castle south-east of here."));
        dialog19554_3.addLine(new DialogLine("It looks you are ready to go. Come visit me sometimes.", ignore -> {
            bill.setActivity("wander_random");
            game.getPauseState().setCanSave(true);
            TeleportScenery teleport = (TeleportScenery) game.getGameObjectById(39578);
            teleport.setCollisionAction(ignore2 -> {
                teleport.setCollisionAction(null);
                game.getAudioPlayer().playMusic("suburbs.wav", 0);
            }
            );
        }
        ));

        bill.addDialog(dialog19554_3);

        Dialog dialog19554_4 = new Dialog();

        dialog19554_4.addLine(new DialogLine("If you want to hear more about the missing medallion\n" +
                "talk to the king in his castle south-east of here."));

        bill.addDialog(dialog19554_4);

        Dialog dialog89742_5 = new Dialog();
        dialog89742_5.addLine(new DialogLine("I am Lord Joffrey!" + " Who am I speaking to?"));
        dialog89742_5.addLine(new DialogLine("Oh you have come here to find out about the mediallion?\n" +
                "Well without the medallion we will not last long\n" + "If you go to the north east corner of the island\n" +
                "you will see that the Island has started to die."));
        dialog89742_5.addLine(new DialogLine("That will happen to the whole island soon if we are not able to\n"
                + "find the missing medallion pieces."));
        dialog89742_5.addLine(new DialogLine("But we still have hope, you see we believe\n" + "that the pieces are" +
                " scattered around the island.\n" + "The problem is we have not been able to locate them"));
        dialog89742_5.addLine(new DialogLine("We have sent out people to search for them of course but\n" + "we've " +
                "had no success as of yet." + " Some people have even gone missing."));
        dialog89742_5.addLine(new DialogLine("A few people has disappeared when searching for the\n" + "pieces in the " +
                "south western corner of the map."));
        dialog89742_5.addLine(new DialogLine("We have since forbidden people to search in that\n" + "part of the island" +
                " if you'd like to know more about it\n" + "I suggest you pay Isak a " +
                "visit who spends a lot of time down there"));
        lordGodfrey.addDialog(dialog89742_5);
        NPC gran = (NPC) game.getGameObjectById(10059);

        Dialog dialog10059_1 = new Dialog();

        dialog10059_1.addLine(new DialogLine("HEY!!"));

        gran.addDialog(dialog10059_1);
    }

    private void initializeSteps(Game game) {
        QuestStep step0 = new WaitForExternalCompletion("Talk to the old man in his house.", "");
        step0.setActionAtFinish(ignore -> {
            Scenery bed = (Scenery) game.getGameObjectById(89555);
            bed.setActionWhenInteractedWith(ignore4 -> { game.showBlackScreen(2000, game,
                    ignore5 -> {
                        bed.setActionWhenInteractedWith(null);
                        bill.getDialogManager().nextDialog();
                        Player player = game.getGameState().getPlayer();
                        player.setPosition(new Vector2D(111, 165));
                        player.setDirection("RIGHT");
                        bill.setActivity("stand");
                        bill.setCanMove(false);
                        bill.getBrain().transitionTo("stand", bill, game);
                        bill.setPosition(new Vector2D(145, 165));
                        bill.setDirection("LEFT");
                        bill.executePlayerAction(game);
                    }
            );
                game.getAudioPlayer().playMusic("dungeon.wav", TimeUnit.SECONDS.toMicros(30));
            });
        });
        QuestStep step1 = new WaitForExternalCompletion("Get some rest in the bed in the old man's house.", "");
        QuestStep step2 = new InteractWithGameObject("Go to the king's " +
                "castle to get more information\nabout the missing medallion",
                " He told me about the history\nof this island." +
                        " Apparently a group called group19 recently stole a\n" +
                        "medallion keeping the balance on the island.", game.getGameObjectById(10059));
        System.out.println(game.getGameObjectById(10059));
        addQuestStep(step0, step1, step2);
    }

    private static void changeScenerySprite(Game game, long id, String newSpriteName) {
        Scenery door = (Scenery) game.getGameObjectById(id);
        door.setSprite(game.getContent().getImage(newSpriteName));
        door.setName(newSpriteName);
        game.addSceneryToOverwrite(door);
    }

    private static void activateTeleportScenery(Game game, long id) {
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
}
