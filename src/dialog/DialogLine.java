package dialog;

import core.Action;
import main.Game;

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
