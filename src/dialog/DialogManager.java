package dialog;

import IO.Persistable;
import entity.NPC;
import main.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a manager to keep track of a set of dialogs for an entity or for the game.
 */
public class DialogManager implements Persistable {

    private final NPC speaker;
    private final List<Dialog> dialogs;

    private int currentDialogIndex;

    public Dialog getCurrentDialog(){
        return dialogs.get(currentDialogIndex);
    }

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

    /**
     * Make the dialog continue.
     */
    public void handleDialog(Game game) {
            DialogLine line = dialogs.get(currentDialogIndex).getCurrentLine(game);

            if (line != null) {
                game.getGameState().getPlayer().setCanMove(false);
                game.setCanPause(false);
                game.getGameState().showDialog();
                if (speaker != null) {
                    game.getGameState().getDialogText()
                            .setText("[" + speaker.getName() + "] " + line.get());
                } else {
                    game.getGameState().getDialogText()
                            .setText("[???] " + line.get());
                }
                line.executeAction(game);
            } else {
                if (speaker != null) {
                    speaker.setCanMove(true);
                    game.getGameState().getPlayer().setLastInteractedWith(speaker);
                }
                game.getGameState().hideDialog();
                game.setCanPause(true);
                game.getGameState().getPlayer().setCanMove(true);
            }
    }

    public void addDialog(Dialog dialog) {
        dialogs.add(dialog);
    }

    public boolean hasDialog() {
        return !dialogs.isEmpty() && getCurrentDialog().isActive();
    }

    public void clear() {
        dialogs.clear();
    }

    /**
     * Moves to the next dialog.
     */
    public void nextDialog() {
        if(dialogs.size() > currentDialogIndex + 1) {
            currentDialogIndex++;
        }
    }

    /**
     * Returns the values needed to save this class to file.
     */
    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();

        sb.append(currentDialogIndex);

        return sb.toString();
    }

    /**
     * Restores the dialog manager from file.
     * @param serializedData the values to restore.
     */
    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        currentDialogIndex = Integer.parseInt(tokens[0]);
    }
}
