package entity;

import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import main.state.State;
import settings.Settings;

import java.awt.*;

/**
 * @author Simon Jern
 * The core class for all entities in the game.
 */
public abstract class GameObject {

    protected GameObject parent;

    protected Vector2D position;
    protected Vector2D renderOffset;
    protected int width;
    protected int height;

    protected int collisionBoxWidth;
    protected int collisionBoxHeight;

    protected int renderOrder;

    public Vector2D getPosition(){
        Vector2D finalPosition = position.getCopy();

        if(parent != null){
            finalPosition.add(parent.getPosition());
        }
        return finalPosition;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getRenderPosition(Camera camera) {
        return new Vector2D(
                getPosition().getX() - camera.getPosition().getX() + renderOffset.getX(),
                getPosition().getY() - camera.getPosition().getY() + renderOffset.getY());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public int getRenderOrder() {
        return renderOrder;
    }

    protected GameObject(){
        this.position = new Vector2D(0,0);
        this.renderOffset = new Vector2D(0,0);
        this.width = Settings.getSpriteSize();
        this.height = Settings.getSpriteSize();
        this.collisionBoxWidth = 0;
        this.collisionBoxHeight = 0;
        this.renderOrder = 5;
    }

    public abstract void update(State state);

    public abstract void draw(Graphics g, Camera camera);

    public abstract CollisionBox getCollisionBox();

    public boolean collidingWith(CollisionBox box){
        return getCollisionBox().collidingWith(box);
    }

    protected void clearParent(){
        parent = null;
    }
}
