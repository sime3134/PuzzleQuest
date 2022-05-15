package story.quests;

import core.Vector2D;
import story.quest_steps.QuestStep;
import story.quest_steps.GoToTarget;
import story.quest_steps.WaitForExternalCompletion;

/**
 * @author Simon Jern
 * Implements a quest where the player should go to two different locations.
 */
public class ANewBeginning extends Quest {

    public ANewBeginning(int id) {
        super("A new beginning", "I woke up on an unknown island and can't even remember my own name.\n" +
                "An old man helped me after I woke up. I should talk with him to\nstart with.", id);
        initializeSteps();
    }

    private void initializeSteps() {
        QuestStep step1 = new WaitForExternalCompletion("Talk to the old man in his house.", "");
        QuestStep step2 = new WaitForExternalCompletion("Get some rest in the bed in the old man's house.", "");
        QuestStep step3 = new GoToTarget(new Vector2D(2200,1100), new Vector2D(0.0,0.0), "Go to the king's " +
                "castle to get more information\nabout the missing medallion", "I was told about the" +
                " history of this island and the\nrecent theft of the medallion keeping the balance on the " +
                "island." +
                "\nI should go speak to the king next.");
        addQuestStep(step1, step2, step3);
    }
}
