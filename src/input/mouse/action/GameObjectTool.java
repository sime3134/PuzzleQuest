package input.mouse.action;

import core.Vector2D;
import entity.GameObject;
import entity.NPC;
import entity.SelectionCircle;
import input.Input;
import main.Game;
import ui.UIContainer;
import ui.UIImage;
import ui.clickable.UICheckbox;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Simon Jern
 * Tool to move and delete scenery on the game map.
 */
public class GameObjectTool extends MouseAction{

    private UIContainer options;
    private Vector2D dragPosition;
    private GameObject selectedGameObject;

    @Override
    public void onClick(Game game){
        Input input = Input.getInstance();

        Vector2D mousePosition = input.getMousePosition().getCopy();
        mousePosition.add(game.getCamera().getPosition());

        game.getGameObjectsOfClass(NPC.class).stream()
            .filter(npc -> npc.getCollisionBox().getBounds().contains(mousePosition.intX(),
                    mousePosition.intY()))
            .forEach(collidingNPCs -> showNPCMenu(collidingNPCs, game));
    }

    private void showNPCMenu(NPC collidingNPC, Game game) {
        options = game.getEditorState().getOptions();
        options.setVisible(false);
        Vector2D position = collidingNPC.getRenderPosition(game.getCamera());
        position.subtract(new Vector2D(0, 50));
        options.setAbsolutePosition(position);
        options.clear();
        options.addComponent(new UICheckbox("Random action", collidingNPC.getDoRandomAction()));
        options.setVisible(true);
    }

    @Override
    public void onDrag(Game game) {
        Input input = Input.getInstance();

        if(dragPosition == null){
            dragPosition = input.getMousePosition().getCopy();

            if(!input.isKeyCurrentlyPressed(KeyEvent.VK_SHIFT)) {
                cleanUp();
            }

            Vector2D mousePosition = input.getMousePosition().getCopy();
            mousePosition.add(game.getCamera().getPosition());

            game.getGameObjectsOfClass(GameObject.class).stream()
                    .filter(gameObject -> gameObject.getCollisionBox().getBounds().contains(mousePosition.intX(), mousePosition.intY()))
                    .forEach(collidingGameObjects -> select(collidingGameObjects));
        } else {
            dragPosition.subtract(input.getMousePosition());
            if(selectedGameObject != null) {
                selectedGameObject.changePositionBy(new Vector2D(-dragPosition.getX(), -dragPosition.getY()));
            }
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
            game.removeGameObject(selectedGameObject);
        }

        if(!input.isLeftMousePressed()){
            dragPosition = null;
        }

        if(options != null && selectedGameObject != null){
            Vector2D position = selectedGameObject.getRenderPosition(game.getCamera());
            position.subtract(new Vector2D(0, 50));
            options.setAbsolutePosition(position);
        }
        else if(options != null){
            options.setVisible(false);
        }
    }

    @Override
    public void draw(Graphics g) {

    }

    private void select(GameObject gameObject) {
        if(selectedGameObject != null) {
            selectedGameObject.clearAttachments();
        }
        SelectionCircle selectionCircle = new SelectionCircle(gameObject.getSelectionCircleWidth(), gameObject.getSelectionCircleHeight());
        selectionCircle.setRenderOffset(new Vector2D(gameObject.getSelectionCircleRenderXOffset(), gameObject.getSelectionCircleRenderYOffset()));
        gameObject.attach(selectionCircle);
        selectedGameObject = gameObject;
    }

    private void deselect(GameObject gameObject) {
        if(gameObject != null) {
            gameObject.clearAttachments();
            selectedGameObject = null;
            if(options != null) {
                options.setVisible(false);
            }
        }
    }

    @Override
    public void cleanUp() {
        deselect(selectedGameObject);
    }

    @Override
    public UIImage getSprite() {
        return null;
    }
}