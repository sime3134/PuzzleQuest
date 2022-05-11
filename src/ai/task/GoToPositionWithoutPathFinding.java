package ai.task;

import controller.NPCController;
import core.Action;
import core.Vector2D;
import entity.NPC;
import main.Game;

public class GoToPositionWithoutPathFinding extends AITask {

    private final Vector2D target;
    public GoToPositionWithoutPathFinding(NPC currentCharacter, Vector2D target, Action action) {
        super(currentCharacter, action);
        condition = (game, npc1) -> reachedPosition();
        this.target = target;
    }

    private boolean reachedPosition() {
        return target != null && currentCharacter.getPosition().isInRangeOf(target);
    }

    @Override
    public void update(Game game) {
        NPCController controller = (NPCController) currentCharacter.getController();

        if(reachedPosition()) {
            controller.stop();
        }else{
            controller.moveToTarget(target, currentCharacter.getPosition());
        }
    }
}
