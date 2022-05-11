package dialog;

import entity.NPC;
import main.Game;

public class DialogInitializer {

    public DialogInitializer(Game game) {
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
