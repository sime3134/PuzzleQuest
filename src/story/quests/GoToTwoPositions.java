package story.quests;

import core.Vector2D;
import story.Quest;
import story.QuestStep;
import story.quest_steps.GoToTarget;

public class GoToTwoPositions extends Quest {

    public GoToTwoPositions(String name) {
        super(name);
        initializeSteps();
    }

    private void initializeSteps() {
        QuestStep step1 = new GoToTarget(new Vector2D(40, 40), "Go to position 100 x 100");
        QuestStep step2 = new GoToTarget(new Vector2D(0,0), "Go to position 300 x 300");
        currentStep = step1;
        addQuestStep(step2);

    }



}
