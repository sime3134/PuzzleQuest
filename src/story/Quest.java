package story;

import main.Game;

import java.util.LinkedList;

public abstract class Quest {

    private int id;
    private String name;
    private boolean active;
    private boolean finished;
    private final LinkedList<QuestStep> steps;
    protected QuestStep currentStep;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    protected Quest(String name, int id) {
        steps = new LinkedList<>();
        this.name = name;
        this.id = id;
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

    protected void disengage(Game game) {
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
        System.out.println("Quest '" + name  + "' started");
        active = true;
    }
}
