package story.quests;

import core.Vector2D;
import story.Quest;
import story.QuestStep;
import story.quest_steps.GoToTarget;

public class GoToTwoPositions extends Quest {

    public GoToTwoPositions(String name, int id) {
        super(name, id);
        initializeSteps();
    }

    private void initializeSteps() {
        QuestStep step1 = new GoToTarget(new Vector2D(2200, 700), new Vector2D(0.0, 0.0), "Go to position 2200 x" +
                " " +
                "700" +
                " on map 1");
        QuestStep step2 = new GoToTarget(new Vector2D(2200,1100), new Vector2D(0.0,0.0), "Go to position 2200 x " +
                "1100" +
                " on map 1");
        addQuestStep(step1);
        addQuestStep(step2);
    }
}
