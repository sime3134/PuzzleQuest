package story;

import IO.Persistable;
import main.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest implements Persistable {
    private final int id;
    private final String name;
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

    protected Quest(String name, int id) {
        steps = new ArrayList<>();
        this.name = name;
        this.id = id;
    }

    public void update(Game game){
        steps.get(currentStep).update(game);

        if(steps.get(currentStep).shouldTransition(game)){
            game.showDialog("Finished step " + steps.get(currentStep).getName() + " in quest " + name);
            if(currentStep < steps.size()) {
                currentStep++;
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
        steps.add(step);
    }

    public boolean isActive() {
        return active;
    }

    public void initialize() {
        System.out.println("Quest '" + name  + "' started");
        active = true;
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
    }
}
