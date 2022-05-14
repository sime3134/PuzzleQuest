package dialog;

import core.Action;
import main.Game;

/**
 * @author Simon Jern
 * Implements a dialog line to be used in a dialog. A dialog can have an action occur
 * when the line is shown in the dialog.
 */
public class DialogLine {
    private final String line;
    private final Action action;


    public DialogLine(String line){
        this(line, null);
    }

    public DialogLine(String line, Action action) {
        this.line = line;
        this.action = action;
    }

    public void executeAction(Game game) {
        if(action != null) {
            action.execute(game);
        }
    }

    public String get() {
        return line;
    }
}
