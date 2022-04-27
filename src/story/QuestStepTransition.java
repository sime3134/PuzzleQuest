package story;

import main.Game;

public class QuestStepTransition {

    private final QuestStepCondition condition;

    public QuestStepTransition(QuestStepCondition condition) {
        this.condition = condition;
    }

    public boolean shouldTransition(Game game){
        return condition.isMet(game);
    }
}
