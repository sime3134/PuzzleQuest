package dialog;

import core.Action;
import main.Game;

import java.util.ArrayList;
import java.util.List;

public class Dialog {
    private final List<DialogLine> lines;
    private int currentLineIndex;

    private final Action action;

    public Dialog(){
        this(null);
    }

    public Dialog(Action action) {
        lines = new ArrayList<>();
        currentLineIndex = 0;
        this.action = action;
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
            line.executeAction(game);
            return line;
        }
    }

    public void addLine(DialogLine line) {
        lines.add(line);
    }
}
