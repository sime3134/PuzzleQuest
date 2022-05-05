package story;

import main.Game;

import java.util.LinkedList;

public abstract class Quest {

    String name;

    private boolean active;
    private boolean finished;
    private LinkedList<QuestStep> steps;
    protected QuestStep currentStep;

    public String getName() {
        return name;
    }

    public boolean isFinished() {
        return finished;
    }

    public LinkedList<QuestStep> getSteps() {
        return steps;
    }

    public QuestStep getCurrentStep() {
        return currentStep;
    }

    protected Quest(String name) {
        steps = new LinkedList<>();
        this.name = name;
    }

    public void update(Game game){
        currentStep.update(game);

        if(currentStep.shouldTransition(game)){
            game.showDialog("Finished step " + currentStep.getName() + " in quest " + name);
            if(!steps.isEmpty()) {
                currentStep = steps.getFirst();
                steps.removeFirst();
            }else{
                disengage(game);
            }
        }
    }

    private void disengage(Game game) {
        active = false;
        finished = true;
        game.showDialog("Finished the quest: " + name);
    }

    public void addQuestStep(QuestStep step){
        steps.addLast(step);
    }

    public boolean isActive() {
        return active;
    }

    public void initialize() {
        System.out.println("Quest activated");
        active = true;
    }
}
