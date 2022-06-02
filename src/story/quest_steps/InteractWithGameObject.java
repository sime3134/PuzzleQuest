package story.quest_steps;

import entity.GameObject;
import main.Game;
import story.QuestStepTransition;

/**
 * @author Simon Jern, Johan Salomonsson
 * This class implements the logic that a quest step is finished when the player interacts with
 * a specifik item or person.
 */
public class InteractWithGameObject extends QuestStep {

    private final GameObject gameObjectToInteractWith;
    private GameObject gameObjectPlayerLastInteractedWith;

    public InteractWithGameObject(String name, String description, GameObject gameObjectToInteractWith) {
        super(name, description);
        this.gameObjectToInteractWith = gameObjectToInteractWith;
    }


    @Override
    protected QuestStepTransition initializeTransition() {
        return new QuestStepTransition(game -> gameObjectToInteractWith.getId() == gameObjectPlayerLastInteractedWith.getId());
    }

    @Override
    public void update(Game game) {
        gameObjectPlayerLastInteractedWith = game.getGameState().getPlayer().getLastInteractedWith();
    }
}
