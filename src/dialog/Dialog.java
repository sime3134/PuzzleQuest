package dialog;

import core.Action;
import main.Game;

import java.util.ArrayList;
import java.util.List;

public class Dialog {
    private final List<DialogLine> lines;
    private int currentLineIndex;

    private final Action action;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Dialog(){
        this(null);
    }

    public Dialog(Action action) {
        lines = new ArrayList<>();
        currentLineIndex = 0;
        this.action = action;
        active = true;
    }

    public DialogLine getCurrentLine(Game game) {
        if(currentLineIndex + 1 > lines.size()){
            currentLineIndex = 0;
            if(action != null) {
                action.execute(game);
            }
            return null;
        }else {
            DialogLine line = lines.get(currentLineIndex);
            currentLineIndex++;
            return line;
        }
    }

    public void addLine(DialogLine line) {
        lines.add(line);
    }
}
