package dialog;

import IO.Persistable;
import entity.NPC;
import main.Game;

import java.util.ArrayList;
import java.util.List;

public class DialogManager implements Persistable {

    private final NPC speaker;
    private final List<Dialog> dialogs;

    private int currentDialogIndex;

    public DialogManager(NPC speaker) {
        this.speaker = speaker;
        this.dialogs = new ArrayList<>();
        this.currentDialogIndex = 0;
    }

    public DialogManager() {
        this.speaker = null;
        this.dialogs = new ArrayList<>();
        this.currentDialogIndex = 0;
    }

    public void handleDialog(Game game) {
        DialogLine line = dialogs.get(currentDialogIndex).getCurrentLine(game);

        if(line != null) {
            if(speaker != null) {
                speaker.setCanMove(false);
            }
            game.getGameState().getPlayer().setCanMove(false);
            game.setCanPause(false);
            game.getGameState().showDialog();
            if(speaker != null) {
                game.getGameState().getDialogText()
                        .setText("[" + speaker.getName() + "] " + line.get());
                line.executeAction(game);
            }else{
                game.getGameState().getDialogText()
                        .setText("[???] " + line.get());
                line.executeAction(game);
            }
        }else{
            game.getGameState().hideDialog();
            game.setCanPause(true);
            if(speaker != null) {
                speaker.setCanMove(false);
            }
            game.getGameState().getPlayer().setCanMove(true);
        }
    }

    public void addDialog(Dialog dialog) {
        dialogs.add(dialog);
    }

    public boolean hasDialog() {
        return !dialogs.isEmpty();
    }

    public void clear() {
        dialogs.clear();
    }

    public void nextDialog() {
        if(dialogs.size() > currentDialogIndex + 1) {
            currentDialogIndex++;
        }
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();

        sb.append(currentDialogIndex);

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        currentDialogIndex = Integer.parseInt(tokens[0]);
    }
}