package ai;

import ai.state.*;
import ai.task.AITask;
import entity.NPC;
import main.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern, Erik Larsson
 * Handles the flow during and between states.
 */
public class AIManager {
    private AIState currentAIState;
    private String lastState;
    private final NPC currentNPC;
    private final List<AITask> tasks;

    public AIManager(NPC currentNPC) {
        this.currentNPC = currentNPC;
        lastState = "choose_next_action";
        currentAIState = new DoBaseActivity(currentNPC, lastState);
        tasks = new ArrayList<>();
    }

    public void update(Game game){
        if(tasks.isEmpty()) {
            currentAIState.update(game);

            if (currentAIState.shouldTransition(game)) {
                transitionTo(currentAIState.getNextState(), currentNPC, game);
            }
        }else{
            tasks.get(0).update(game);

            if(tasks.get(0).finished(game, currentNPC)) {
                tasks.get(0).executeEndTask(game);
                tasks.remove(0);
            }
        }
    }

    public void transitionTo(String nextState, NPC currentNPC, Game game) {
        switch (nextState) {
            case "choose_next_action" -> currentAIState = new DoBaseActivity(currentNPC, lastState);
            case "random_action" -> currentAIState = new RandomAction(currentNPC, lastState);
            case "wander_random" -> currentAIState = new WanderRandom(currentNPC, lastState);
            case "wander_loop" -> currentAIState = new WanderLoop(game, currentNPC, lastState,
                    currentNPC.getFirstLoopTarget(), currentNPC.getSecondLoopTarget());
            case "stand" -> currentAIState = new Stand(currentNPC, lastState);
            default -> System.out.println("Illegal value in AIManager: " + nextState);
        }

        lastState = nextState;
    }

    public void addTask(AITask taskToAdd) {
        tasks.add(taskToAdd);
    }
}
