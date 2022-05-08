package dialog;

import entity.NPC;
import main.Game;

public class DialogInitializer {

    public DialogInitializer(Game game) {
        NPC npc = (NPC)game.getGameObjectById(19554);
        Dialog dialog19554_1 = new Dialog(ignore -> npc.getDialogManager().nextDialog());

        dialog19554_1.addLine(new DialogLine("This is where I live, welcome in.."));

        npc.addDialog(dialog19554_1);

        Dialog dialog19554_2 = new Dialog();

        dialog19554_2.addLine(new DialogLine("I can see that you are exhausted.. Get some rest and we will talk " +
                "tomorrow."));

        npc.addDialog(dialog19554_2);
    }

}
