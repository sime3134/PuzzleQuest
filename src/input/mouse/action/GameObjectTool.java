package input.mouse.action;

import core.Vector2D;
import entity.GameObject;
import entity.NPC;
import entity.SelectionCircle;
import entity.TeleportScenery;
import input.Input;
import main.Game;
import ui.containers.UIContainer;
import ui.UIImage;
import ui.clickable.UIText;
import ui.clickable.UIButton;
import ui.clickable.UICheckbox;
import ui.input.UITextInput;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Simon Jern
 * Tool to move and delete a game object on the game map.
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

        System.out.println(mousePosition);

        game.getGameObjectsOfClass(NPC.class).stream()
            .filter(npc -> npc.getCollisionBox().getBounds().contains(mousePosition.intX(),
                    mousePosition.intY()))
            .forEach(collidingNPCs -> showNPCMenu(collidingNPCs, game));

        game.getGameObjectsOfClass(TeleportScenery.class).stream()
                .filter(tScenery -> tScenery.getCollisionBox().getBounds().contains(mousePosition.intX(),
                        mousePosition.intY()))
                .forEach(collidingTScenery -> showTeleportSceneryMenu(collidingTScenery, game));
    }

    private void showTeleportSceneryMenu(TeleportScenery collidingTScenery, Game game) {
        UITextInput mapToTeleportTo = new UITextInput(1, 13, collidingTScenery.getMapToTeleportTo());
        UITextInput xPositionToTeleportTo = new UITextInput(1,13,
                String.valueOf(collidingTScenery.getPositionToTeleportTo().getX()));
        UITextInput yPositionToTeleportTo = new UITextInput(1,13,
                String.valueOf(collidingTScenery.getPositionToTeleportTo().getY()));
        UICheckbox active = new UICheckbox("Active", collidingTScenery.getActive());

        options = game.getEditorState().getOptions();
        options.setVisible(false);
        Vector2D position = collidingTScenery.getRenderPosition(game.getCamera());
        position.subtract(new Vector2D(0, 50));
        options.setAbsolutePosition(position);
        options.clear();
        options.addComponent(new UIText("Teleport to map:"));
        options.addComponent(mapToTeleportTo);
        options.addComponent(new UIText("x:"));
        options.addComponent(xPositionToTeleportTo);
        options.addComponent(new UIText("y:"));
        options.addComponent(yPositionToTeleportTo);
        options.addComponent(active);
        options.addComponent(new UIButton("Save", game1 -> saveTeleportSceneryInfo(collidingTScenery,
                mapToTeleportTo, xPositionToTeleportTo, yPositionToTeleportTo)));
        options.setVisible(true);
    }

    private void saveTeleportSceneryInfo(TeleportScenery collidingTScenery, UITextInput mapToTeleportTo,
                                         UITextInput xPositionToTeleportTo, UITextInput yPositionToTeleportTo) {
        collidingTScenery.setMapToTeleportTo(mapToTeleportTo.getText().toLowerCase());
        collidingTScenery.setPositionToTeleportTo(new Vector2D(Double.parseDouble(xPositionToTeleportTo.getText()),
                Double.parseDouble(yPositionToTeleportTo.getText())));
    }

    private void showNPCMenu(NPC collidingNPC, Game game) {
        UITextInput nameInput = new UITextInput(1, 13, collidingNPC.getName());
        UITextInput activityInput = new UITextInput(1, 13, collidingNPC.getActivity());
        UITextInput directionInput = new UITextInput(1, 13, collidingNPC.getDirection().toString());

        options = game.getEditorState().getOptions();
        options.setVisible(false);
        Vector2D position = collidingNPC.getRenderPosition(game.getCamera());
        position.subtract(new Vector2D(0, 50));
        options.setAbsolutePosition(position);
        options.clear();
        options.addComponent(new UIText("Name:"));
        options.addComponent(nameInput);
        options.addComponent(new UIText("AIState:"));
        options.addComponent(activityInput);
        options.addComponent(new UIText("Direction"));
        options.addComponent(directionInput);
        options.addComponent(new UIButton("Save", game1 -> saveNPCInfo(collidingNPC, nameInput, activityInput,
                directionInput)));
        options.setVisible(true);
    }

    private void saveNPCInfo(NPC npc, UITextInput nameInput, UITextInput activityInput, UITextInput directionInput) {
        npc.setName(nameInput.getText());
        npc.setActivity(activityInput.getText().toLowerCase());
        npc.setDirection(directionInput.getText().toUpperCase());
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
    public void draw(Game game, Graphics g) {

    }

    private void select(GameObject gameObject) {
        System.out.println(gameObject.getId());
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
