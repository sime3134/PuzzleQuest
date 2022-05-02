package entity;

import ai.AIManager;
import content.SpriteSet;
import controller.EntityController;
import controller.NPCController;
import core.Vector2D;
import display.Camera;
import main.Game;
import main.state.GameState;
import settings.Setting;
import settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern, Erik Larsson
 * Implements living entities that are not controlled by the player.
 */
public class NPC extends Humanoid {

    private final AIManager brain;
    private List<Vector2D> path;
    private Setting<Boolean> doRandomAction;

    private Vector2D firstLoopTarget;
    private Vector2D secondLoopTarget;
    private String activity;

    public void setPath(List<Vector2D> path) {
        this.path = path;
    }

    public AIManager getBrain() {
        return brain;
    }

    public NPC getCopy() {
        NPC copy = new NPC(new NPCController(), animationManager.getSpriteSet(), "default");

        copy.position = position;

        return copy;
    }

    public Setting<Boolean> getDoRandomAction() {
        return doRandomAction;
    }

    public Vector2D getFirstLoopTarget() {
        return firstLoopTarget;
    }

    public Vector2D getSecondLoopTarget() {
        return secondLoopTarget;
    }

    public NPC(EntityController entityController, SpriteSet spriteSet, String name) {
        super(entityController, spriteSet, name);
        activity = "random_action";
        doRandomAction = new Setting<>(true);
        brain = new AIManager(this);
        path = new ArrayList<>();
        firstLoopTarget = new Vector2D(0, 0);
        secondLoopTarget = new Vector2D(100, 100);
    }

    @Override
    public void update(Game game){
        super.update(game);
        if(game.getCurrentState() instanceof GameState) {
            brain.update(game);
        }
    }

    @Override
    public void draw(Graphics g, Camera camera) {
        super.draw(g, camera);
        if(Settings.getRenderPaths().getValue()) {
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
    protected void executePlayerAction(Game game) {
        startAvailableQuest();
    }

    private void startAvailableQuest() {
        //Should filter through and find quests possible to do
        if(!quests.isEmpty()){
            quests.get(0).initialize();
        }
    }

    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof Player
                || other instanceof Scenery && !((Scenery)other).isWalkable()){
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }

    public String getActivity() {
        return activity;
    }
}
