package entity;

import ai.AIManager;
import content.SpriteSet;
import controller.EntityController;
import core.Vector2D;
import display.Camera;
import entity.humanoid.Humanoid;
import main.Game;
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
    public void update(Game game){
        super.update(game);
        brain.update(game);
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
}
