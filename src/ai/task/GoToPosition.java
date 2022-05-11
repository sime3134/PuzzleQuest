package ai.task;

import ai.PathFindingThread;
import controller.NPCController;
import core.Action;
import core.Vector2D;
import entity.NPC;
import main.Game;

import java.util.ArrayList;
import java.util.List;

public class GoToPosition extends AITask{

    private PathFindingThread thread;

    private final List<Vector2D> path;

    private final Vector2D target;

    private boolean cancel;

    public GoToPosition(NPC currentCharacter, Vector2D target, Action action) {
        super(currentCharacter, action);
        condition = (game, npc) -> reachedPosition();
        this.target = Vector2D.ofGridPosition(target.grid());
        path = new ArrayList<>();
    }

    private boolean reachedPosition() {
        return target != null && currentCharacter.getPosition().isInRangeOf(target) || cancel;
    }

    @Override
    public void update(Game game) {
        if(thread == null) {
            thread = new PathFindingThread(game, currentCharacter, path, target);
            thread.start();
        }

        if(!thread.getCouldFindPath()){
            cancel = true;
        }

        NPCController controller = (NPCController) currentCharacter.getController();

        if(reachedPosition()) {
            controller.stop();
        }

        if(!path.isEmpty() && currentCharacter.getPosition().isInRangeOf(path.get(0))){
            path.remove(0);
        }

        if(!path.isEmpty()){
            controller.moveToTarget(path.get(0), currentCharacter.getPosition());
        }

        System.out.println(target);
    }
}
