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
    private final NPC lordJoffrey;
    private final NPC isak;
    private final NPC akira;
    private final NPC komako;
    private final NPC luna;

    public BornAnew(Game game, int id) {
        super("Born anew", "I woke up on an unknown island and can't even remember my own name.\n" +
                "An old man helped me after I woke up.", id);
        bill = (NPC)game.getGameObjectById(19554);
        bill.setName("???");
        lordJoffrey = (NPC) game.getGameObjectById(89742);
        isak = (NPC) game.getGameObjectById(89743);
        akira = (NPC) game.getGameObjectById(89759);
        luna = (NPC) game.getGameObjectById(89736);
        komako = (NPC) game.getGameObjectById(90121);
        initializeQuest(game);
        initializeSteps(game);
    }

    public void prepare(Game game){
        game.setShowBlackScreen(true);
        Dialog intro = new Dialog(ignore -> {
            game.getPauseState().setCanSave(false);
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
        initializeAkiraDialog(game);

        initializeIsakDialog(game);

        initializeJoffreyDialog();

        initializeBillDialogs(game);
    }

    private void initializeBillDialogs(Game game) {
        Dialog billDialog = new Dialog(ignore -> {
            bill.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });

        billDialog.addLine(new DialogLine("My name is Bill by the way. \nWelcome to my home.",
                ignore -> bill.setName("Bill")));
        billDialog.addLine(new DialogLine("I can see that you are exhausted.. \nGet some rest in the bed " +
                "over there and we will talk tomorrow."));
        billDialog.setActive(false);

        bill.addDialog(billDialog);

        Dialog billDialog2 = new Dialog();
        billDialog2.addLine(new DialogLine("I can see that you are exhausted.. \nGet some rest in the bed " +
                "over there and we will talk tomorrow."));

        bill.addDialog(billDialog2);

        Dialog billDialog3 = new Dialog(ignore -> {
            lordJoffrey.getDialogManager().nextDialog();
            bill.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });

        billDialog3.addLine(new DialogLine("Good morning. Did you sleep well?"));
        billDialog3.addLine(new DialogLine("You have bad luck washing ashore on this island.\n" +
                "Did you notice how the nature has died outside?"));
        billDialog3.addLine(new DialogLine("The island is dying after Group19 stole\n" +
                "the medallion keeping it in balance."));
        billDialog3.addLine(new DialogLine("What medallion you say?"));
        billDialog3.addLine(new DialogLine("The medallion contains a stone of life, I'm sure you have heard " +
                "of them?"));
        billDialog3.addLine(new DialogLine("The legends say that this island was originally just dead rock."));
        billDialog3.addLine(new DialogLine("But one day an adventurer found a stone of life " +
                "\nand with it, created life on the island."));
        billDialog3.addLine(new DialogLine("And now, without it, it is once again dying."));
        billDialog3.addLine(new DialogLine("If you want to hear more about it, talk to Lord Joffrey in his\n" +
                "castle south-east of here."));
        billDialog3.addLine(new DialogLine("It looks you are ready to go. Come visit me sometimes.", ignore -> {
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

        bill.addDialog(billDialog3);

        Dialog billDialog4 = new Dialog();

        billDialog4.addLine(new DialogLine("Lord Joffrey is the ruler of this island. His ancestors found the\n" +
                "life of stone."));

        bill.addDialog(billDialog4);
    }

    private void initializeJoffreyDialog() {
        Dialog inactiveDialog1 = new Dialog();
        inactiveDialog1.addLine(new DialogLine(""));
        inactiveDialog1.setActive(false);
        lordJoffrey.addDialog(inactiveDialog1);

        Dialog joffreyDialog1 = new Dialog(game -> {
            isak.getDialogManager().nextDialog();
            lordJoffrey.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });
        joffreyDialog1.addLine(new DialogLine("I am Lord Joffrey!" + " Who am I speaking to?"));
        joffreyDialog1.addLine(new DialogLine("Oh you have come here to find out about the medallion?\n" +
                "The medallion contains a stone that gave this island life. Before the\n" + "medallion was " +
                "placed on this island hundreds of years ago, there was no life here.\n"
                + "The medallion awoke nature and made life flourish."));
        joffreyDialog1.addLine(new DialogLine("The word of the medallion spread and attracted all kinds " +
                "of\npeople." + "But it was when group19 showed up that trouble started.\n" + "They have always attempted to " +
                "steal it but never succeeded, until now."));
        joffreyDialog1.addLine(new DialogLine("But it didn't end the way they anticipated.\n" +
                "The medallion can only be used by someone of the finders bloodline.\n" +
                "The moment they grabbed the medallion it split into pieces\n" +
                "and now the pieces lay scattered all around the island."));
        joffreyDialog1.addLine(new DialogLine("Without the medallion life on this island will not last long.\n" +
                "The deterioration in the north east will continue\n" + "and lead to the death of this island" +
                " unless the pieces\nof the " +
                "medallion are collected and put back together again."));
        joffreyDialog1.addLine(new DialogLine("The problem is we have not been able to locate them."));
        joffreyDialog1.addLine(new DialogLine("We have sent out people to search for them but\n" + "we've " +
                "had no success as of yet." + " Some people have even gone missing."));
        joffreyDialog1.addLine(new DialogLine("They have disappeared while searching for the\n" + "missing pieces in the " +
                "south-western corner of the map."));
        joffreyDialog1.addLine(new DialogLine("We have since forbidden people to search in that\n" + "area of " +
                "the island" +
                " if you'd like to know more about it.\n" + "I suggest you pay Isak down there a visit."));
        lordJoffrey.addDialog(joffreyDialog1);

        Dialog joffreyDialog2 = new Dialog();
        joffreyDialog2.addLine(new DialogLine("We've lived here peacefully for centuries, and now this..."));
        lordJoffrey.addDialog(joffreyDialog2);
    }

    private void initializeIsakDialog(Game game) {
        Dialog inactiveDialog1 = new Dialog();
        inactiveDialog1.addLine(new DialogLine(""));
        inactiveDialog1.setActive(false);
        isak.addDialog(inactiveDialog1);

        Dialog isakDialog1 = new Dialog(ignore -> {
            game.getGameState().getQuestManager().startQuest(game, 1);
            akira.getDialogManager().nextDialog();
            isak.getDialogManager().nextDialog();
            NPC jester = (NPC) game.getGameObjectById(100000);
            NPC locky = (NPC) game.getGameObjectById(90147);
            NPC stocky = (NPC) game.getGameObjectById(90149);
            jester.setActivity("wander_random");
            jester.getDialogManager().nextDialog();
            locky.getDialogManager().nextDialog();
            stocky.getDialogManager().nextDialog();
            this.goToNextStep(game);
        });

        isakDialog1.addLine(new DialogLine("I'm Isak. What brings you here?"));
        isakDialog1.addLine(new DialogLine("Ah the missing pieces, well I'm afraid you are wasting your time.\n"
                + "I've only heard rumors and rumors are not to be trusted."));
        isakDialog1.addLine(new DialogLine("The rumors?\n" + "They are very vague and not of much value for " +
                "you I'm afraid."));
        isakDialog1.addLine(new DialogLine("There are people who has disappeared while searching for the" +
                " missing\npieces. Apparently the hole over there has something to do with it. I advise\n" +
                "you to stay away from the hole if you don't wish to disappear like they did."));
        isakDialog1.addLine(new DialogLine("One last thing, if you truly are here to help us\n" + "you should " +
                "also go talk to Akira,\nthe old mentor of group19."));
        isakDialog1.addLine(new DialogLine("You'll find her in the harbour city up north."));
        isak.addDialog(isakDialog1);

        Dialog isakDialog2 = new Dialog();
        isakDialog2.addLine(new DialogLine("Akira was the mentor of group 19. How could it end up like this?"));
        isak.addDialog(isakDialog2);

        Dialog isakDialog3 = new Dialog(ignore -> isak.getDialogManager().nextDialog());
        isakDialog3.addLine(new DialogLine("You found a piece of the medallion? Amazing."));
        isakDialog3.addLine(new DialogLine("I hope you can find the other pieces too."));
        isak.addDialog(isakDialog3);

        Dialog isakDialog4 = new Dialog();
        isakDialog4.addLine(new DialogLine("I hope you can find the other pieces too."));
        isak.addDialog(isakDialog4);
    }

    private void initializeAkiraDialog(Game game) {
        Dialog inactiveDialog1 = new Dialog();
        inactiveDialog1.addLine(new DialogLine(""));
        inactiveDialog1.setActive(false);
        akira.addDialog(inactiveDialog1);

        Dialog akiraDialog1 = new Dialog(ignore -> {
            game.getGameState().getQuestManager().startQuest(game, 2);
            game.getGameState().getQuestManager().startQuest(game, 4);
            akira.getDialogManager().nextDialog();
            luna.getDialogManager().nextDialog();
            komako.getDialogManager().nextDialog();
        });
        akiraDialog1.addLine(new DialogLine("Who are you? And why are you looking for me?"));
        akiraDialog1.addLine(new DialogLine("Isak sent you? You must have made a good\n" + "impression on him."
                + " So tell me, why have you come to see me?"));
        akiraDialog1.addLine(new DialogLine("Oh you want to help with finding the medallion pieces?\n" +
                "I don't know where the pieces are, but I do have some ideas\n" + "that might help you out in" +
                " your search."));
        akiraDialog1.addLine(new DialogLine("Talk to Luna, owner of the ocean restaurant on the west shore.\n" +
                "She has " +
                "heard rumours about a chest in the middle of ocean."));
        akiraDialog1.addLine(new DialogLine("Then there is also Komako in the southern marketplace, he has\n" +
                "explored the northern forest a lot since there are weird signs from there."));
        akira.addDialog(akiraDialog1);

        Dialog akiraDialog2 = new Dialog();
        akiraDialog2.addLine(new DialogLine("We must save the island as soon as possible."));
        akira.addDialog(akiraDialog2);
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
        QuestStep step2 = new WaitForExternalCompletion("Go to lord Joffrey's " +
                "castle to get more information\nabout the missing medallion",
                " He told me about the history\nof this island." +
                        " Apparently a group called group19 recently stole a\n" +
                        "medallion keeping the balance on the island.");
        QuestStep step3 = new WaitForExternalCompletion("Go talk to Isak in the" + " south-west\ncorner of the " +
                "island.",
                " I was advised to talk\nto a guy called Isak in the south-west.");
        QuestStep step4 = new InteractWithGameObject("Find Akira in the northern harbour city.", "The old mentor" +
                "\nof " +
                "group19 lives in the harbor city and has some rumours about the\nmissing pieces.",
                game.getGameObjectById(89759));
        addQuestStep(step0, step1, step2, step3, step4);
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
