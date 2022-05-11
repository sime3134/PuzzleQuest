package ai.task;

import ai.AICondition;
import core.Action;
import entity.NPC;
import main.Game;

public abstract class AITask {

    private final Action action;
    protected AICondition condition;

    protected final NPC currentCharacter;

    protected AITask(NPC currentCharacter, Action action) {
        this.action = action;
        this.currentCharacter = currentCharacter;
    }

    public abstract void update(Game game);

    public boolean finished(Game game, NPC npc) {
        if(condition != null) {
            return condition.isMet(game, npc);
        }else{
            return false;
        }
    }

    public void executeEndTask(Game game) {
        action.execute(game);
    }
}
