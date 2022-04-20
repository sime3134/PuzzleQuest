package entity;

import ai.AIManager;
import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;
import display.Camera;
import entity.humanoid.Humanoid;
import main.state.State;
import settings.Settings;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Simon Jern
 * Implements living entities that are not controlled by the player.
 */
public class NPC extends Humanoid {

    private final AIManager brain;
    private List<Vector2D> path;

    public void setPath(List<Vector2D> path) {
        this.path = path;
    }

    public AIManager getBrain() {
        return brain;
    }

    public NPC(EntityController entityController, SpriteSet spriteSet) {
        super(entityController, spriteSet);
        brain = new AIManager(this);
        path = new ArrayList<>();
    }

    @Override
    public void update(State state){
        super.update(state);
        brain.update(state);
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
    protected void executePlayerAction(State state) {
        brain.transitionTo("wander", this);
    }

    @Override
    protected void handleCollision(GameObject other) {
        if(other instanceof Player
                || other instanceof Scenery && !((Scenery)other).isWalkable()){
            velocity.reset(willCollideX(other.getCollisionBox()), willCollideY(other.getCollisionBox()));
        }
    }
}
