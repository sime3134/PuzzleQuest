package story.quests;

import IO.Persistable;
import main.Game;
import story.quest_steps.QuestStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Simon Jern
 * Implements a quest that has one or several steps that should be completed to
 * complete the quest in whole.
 */
public abstract class Quest implements Persistable {
    private final int id;
    private final String name;

    private String description;
    private boolean active;
    private boolean finished;
    private final List<QuestStep> steps;
    protected int currentStep;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public QuestStep getCurrentStep() {
        return steps.get(currentStep);
    }

    public String getDescription() {
        return description;
    }

    protected Quest(String name, String startingDesc, int id) {
        steps = new ArrayList<>();
        this.name = name;
        this.description = startingDesc;
        this.id = id;
    }

    public void update(Game game){
        steps.get(currentStep).update(game);

        if(steps.get(currentStep).shouldTransition(game)){
            if(steps.get(currentStep).getActionAtFinish() != null){
                steps.get(currentStep).getActionAtFinish().execute(game);
            }
            if(currentStep < steps.size() - 1) {
                game.displayNotification("Quest '" + name  + "' updated.");
                currentStep++;
                description += steps.get(currentStep).getDescription();
            }else{
                disengage(game);
            }
        }
    }

    protected void disengage(Game game) {
        active = false;
        finished = true;
        game.displayNotification("Finished quest: " + name);
    }

    public void addQuestStep(QuestStep... questSteps){
        steps.addAll(Arrays.asList(questSteps));
    }

    public boolean isActive() {
        return active;
    }

    public void initialize(Game game) {
        game.displayNotification("Quest '" + name  + "' started");
        active = true;
        prepare(game);
    }

    public abstract void prepare(Game game);

    public void goToNextStep(Game game) {
        if(steps.get(currentStep).getActionAtFinish() != null){
            steps.get(currentStep).getActionAtFinish().execute(game);
        }
        game.displayNotification("Quest '" + name  + "' updated.");
        currentStep++;
        description += steps.get(currentStep).getDescription();
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
                .append(DELIMITER)
                .append(id)
                .append(DELIMITER)
                .append(name)
                .append(DELIMITER)
                .append(active)
                .append(DELIMITER)
                .append(finished)
                .append(DELIMITER)
                .append(currentStep)
                .append(DELIMITER);

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        active = Boolean.parseBoolean(tokens[3]);
        finished = Boolean.parseBoolean(tokens[4]);
        currentStep = Integer.parseInt(tokens[5]);
        for(int i = currentStep; i > 0; i--){
            description += steps.get(i).getDescription();
        }
    }
}
