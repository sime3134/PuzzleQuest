package story;

import main.Game;

/**
 * @author Simon Jern
 * Transitions to the next quest step in a quest.
 */
public class QuestStepTransition {

    private final QuestStepCondition condition;

    public QuestStepTransition(QuestStepCondition condition) {
        this.condition = condition;
    }

    public boolean shouldTransition(Game game){
        return condition.isMet(game);
    }
}
