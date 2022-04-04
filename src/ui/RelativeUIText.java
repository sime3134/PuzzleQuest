package ui;

import entity.NPC;
import main.state.State;
import settings.Settings;

/**
 * @author Simon Jern
 * A UI component that displays text that can be updated during the game.
 */
public class RelativeUIText extends UIText{

    private final Object obj;

    public RelativeUIText(String text, Object obj) {
        super(text);
        this.obj = obj;
    }

    @Override
    public void update(State state){
        super.update(state);
        if(obj instanceof NPC npc) {
            text = npc.getBrain().getCurrentAIState().getClass().getSimpleName();
        }
    }
}
