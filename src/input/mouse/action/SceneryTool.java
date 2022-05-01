package input.mouse.action;

import core.Vector2D;
import entity.Scenery;
import entity.SelectionCircle;
import input.Input;
import main.Game;
import main.state.State;
import ui.UIImage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Simon Jern
 * Tool to move and delete scenery on the game map.
 */
public class SceneryTool extends MouseAction{

    private Vector2D dragPosition;
    private final Set<Scenery> selectedScenery;

    public SceneryTool() {
        selectedScenery = new HashSet<>();
    }

    @Override
    public void onClick(Game game) {

    }

    @Override
    public void onDrag(Game game) {
        Input input = Input.getInstance();

        if(dragPosition == null){
            State state = game.getCurrentState();
            dragPosition = input.getMousePosition().getCopy();

            if(!input.isKeyCurrentlyPressed(KeyEvent.VK_SHIFT)) {
                cleanUp();
            }

            Vector2D mousePosition = input.getMousePosition().getCopy();
            mousePosition.add(game.getCamera().getPosition());

            game.getGameObjectsOfClass(Scenery.class).stream()
                    .filter(scenery -> scenery.getCollisionBox().getBounds().contains(mousePosition.intX(), mousePosition.intY()))
                    .forEach(collidingScenery -> select(collidingScenery));
        } else {
            dragPosition.subtract(input.getMousePosition());
            selectedScenery.forEach(scenery -> scenery.changePositionBy(new Vector2D(-dragPosition.getX(), -dragPosition.getY())));
            dragPosition = input.getMousePosition().getCopy();
        }
    }

    @Override
    public void onRelease(Game game) {
        dragPosition = null;
    }

    @Override
    public void update(Game game) {
        Input input = Input.getInstance();
        if(input.isKeyPressed(KeyEvent.VK_DELETE)){
            selectedScenery.forEach(scenery -> game.removeScenery(scenery));
        }

        if(!input.isLeftMousePressed()){
            dragPosition = null;
        }
    }

    private void select(Scenery scenery) {
        SelectionCircle selectionCircle = new SelectionCircle(scenery.getSelectionCircleWidth(), scenery.getSelectionCircleHeight());
        selectionCircle.setRenderOffset(new Vector2D(scenery.getSelectionCircleRenderXOffset(), scenery.getSelectionCircleRenderYOffset()));
        scenery.attach(selectionCircle);
        selectedScenery.add(scenery);
    }

    private void deselect(Scenery scenery) {
        scenery.clearAttachments();
        selectedScenery.remove(scenery);
    }

    @Override
    public void cleanUp() {
        List.copyOf(selectedScenery).forEach(scenery -> deselect(scenery));
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public UIImage getSprite() {
        return null;
    }
}
