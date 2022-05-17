package entity;

import ai.AIManager;
import content.AnimationManager;
import content.ContentManager;
import content.SpriteSet;
import controller.EntityController;
import controller.NPCController;
import core.Direction;
import core.Vector2D;
import dialog.Dialog;
import dialog.DialogManager;
import display.Camera;
import main.Game;
import main.state.EditorState;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern, Erik Larsson
 * Implements living entities that are not controlled by the player.
 */
public class NPC extends Humanoid {

    private AIManager brain;

    private final DialogManager dialogManager;
    private List<Vector2D> path;
    private Vector2D firstLoopTarget;
    private Vector2D secondLoopTarget;
    private String activity;

    private String spriteSetName;

    //region Getters & Setters (click to view)

    public void setPath(List<Vector2D> path) {
        this.path = path;
    }

    public AIManager getBrain() {
        return brain;
    }

    public NPC getCopy(String mapName) {
        NPC copy = new NPC(new NPCController(), animationManager.getSpriteSet(), "default", mapName);

        copy.position = position;
        copy.id = nextId;
        nextId++;

        return copy;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Vector2D getFirstLoopTarget() {
        return firstLoopTarget;
    }

    public Vector2D getSecondLoopTarget() {
        return secondLoopTarget;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    //endregion

    public NPC(){
        super(new NPCController());
        path = new ArrayList<>();
        dialogManager = new DialogManager(this);
    }

    public NPC(EntityController entityController, SpriteSet spriteSet, String name, String mapName) {
        super(entityController, spriteSet, name, mapName);
        activity = "stand";
        spriteSetName = spriteSet.getName();
        brain = new AIManager(this);
        dialogManager = new DialogManager(this);
        path = new ArrayList<>();
        firstLoopTarget = new Vector2D(0, 0);
        secondLoopTarget = new Vector2D(100, 100);
    }

    @Override
    public void update(Game game){
        super.update(game);
        if(!(game.getCurrentState() instanceof EditorState)) {
            brain.update(game);
        }
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        super.draw(g, camera);
        if(Settings.getRenderPaths().get()) {
            int i = 0;
            for(Vector2D v : path){
                i++;
                g.setColor(Color.BLUE);
                g.fillRect(v.intX() - camera.getPosition().intX(), v.intY() - camera.getPosition().intY(), 48, 48);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(i), v.intX() - camera.getPosition().intX() + 8, v.intY() - camera.getPosition().intY() + 5);
            }
        }
    }

    @Override
    public void executePlayerAction(Game game) {
        if(dialogManager.hasDialog() && !game.getGameState().getNonNPCDialogActive()) {
            canMove = false;
            direction = Direction.opposite(getDirectionInRelationToGameObject(game.getGameState().getPlayer()));
            dialogManager.handleDialog(game);
        }
    }

    @Override
    protected void handleCollision(Game game, GameObject other) {
        if (other instanceof Player) {
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }else if(other instanceof Scenery){
            if(!other.isWalkable()){
                velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
            }
        }
    }

    public String getActivity() {
        return activity;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName())
                .append(DELIMITER)
                .append(name)
                .append(DELIMITER)
                .append(id)
                .append(DELIMITER)
                .append(speed)
                .append(DELIMITER)
                .append(currentMapName)
                .append(DELIMITER)
                .append(position.serialize())
                .append(DELIMITER)
                .append(animationManager.getSpriteSet().getName())
                .append(DELIMITER)
                .append(activity)
                .append(DELIMITER)
                .append(direction)
                .append(DELIMITER)
                .append(dialogManager.serialize())
                .append(DELIMITER);

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        name = tokens[1];
        id = Long.parseLong(tokens[2]);
        speed = Double.parseDouble(tokens[3]);
        currentMapName = tokens[4];
        position.applySerializedData(tokens[5]);
        spriteSetName = tokens[6];
        activity = tokens[7];
        direction = Direction.valueOf(tokens[8]);
        if(tokens.length > 9) {
            dialogManager.applySerializedData(tokens[9]);
        }
        brain = new AIManager(this);

        nextId = Math.max(nextId, id + 1);
    }

    public void applyGraphics(ContentManager content) {
        animationManager = new AnimationManager(content.getSpriteSet(spriteSetName), Settings.getTileSize(),
                Settings.getTileSize(), "stand");
    }

    public void addDialog(Dialog dialog) {
        dialogManager.addDialog(dialog);
    }
}
