package entity;

import core.CollisionBox;
import core.Direction;
import core.Vector2D;
import display.Camera;
import main.Game;
import settings.Settings;
import story.Quest;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern, Erik Larsson
 * The core class for all entities in the game.
 */
public abstract class GameObject {

    protected GameObject parent;

    protected Vector2D position;
    protected Vector2D renderOffset;

    protected int width;
    protected int height;

    protected boolean walkable;

    protected int collisionBoxWidth;
    protected int collisionBoxHeight;

    protected Vector2D collisionBoxOffset;

    protected int renderOrder;

    protected double renderOrderComparisonYOffset;

    protected List<GameObject> attachments;

    protected int selectionCircleWidth;
    protected int selectionCircleHeight;
    protected int selectionCircleRenderXOffset;
    protected int selectionCircleRenderYOffset;

    protected List<Quest> quests;

    //region Getters & Setters (click to view)

    public void setRenderOffset(Vector2D renderOffset) {
        this.renderOffset = renderOffset;
    }

    public Vector2D getRenderOffset() {
        return renderOffset;
    }

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

    public boolean isWalkable() {
        return walkable;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRenderOrder() {
        return renderOrder;
    }

    public double getRenderOrderComparisonYPosition() {
        return position.getY() + renderOrderComparisonYOffset;
    }

    public int getSelectionCircleWidth() {
        return selectionCircleWidth;
    }

    public int getSelectionCircleHeight() {
        return selectionCircleHeight;
    }

    public int getSelectionCircleRenderXOffset() {
        return selectionCircleRenderXOffset;
    }

    public int getSelectionCircleRenderYOffset() {
        return selectionCircleRenderYOffset;
    }

    //endregion

    protected GameObject(){
        this.position = new Vector2D(0,0);
        this.renderOffset = new Vector2D(0,0);
        this.width = Settings.getTileSize();
        this.height = Settings.getTileSize();
        this.collisionBoxWidth = 0;
        this.collisionBoxHeight = 0;
        this.collisionBoxOffset = new Vector2D(0,0);
        this.renderOrder = 5;
        this.renderOrderComparisonYOffset = 0;
        this.selectionCircleWidth = 0;
        this.selectionCircleHeight = 0;
        this.selectionCircleRenderXOffset = 0;
        this.selectionCircleRenderYOffset = 0;
        attachments = new ArrayList<>();
        quests = new ArrayList<>();
    }

    public abstract void update(Game state);

    public abstract void draw(Graphics g, Camera camera);

    public abstract Image getSprite();

    public abstract CollisionBox getCollisionBox();

    public abstract CollisionBox getStaticCollisionBox();

    public boolean collidingWith(CollisionBox box){
        return getCollisionBox().collidingWith(box);
    }

    public void attach(GameObject gameObject){
        gameObject.setPosition(position);
        attachments.add(gameObject);
    }

    public void detach(GameObject gameObject){
        attachments.remove(gameObject);
    }

    public List<GameObject> getAttachments() {
        return attachments;
    }

    public void clearAttachments(){
        attachments.clear();
    }

    public void changePositionBy(Vector2D position){
        this.position.add(position);
    }

    public Direction getDirectionInRelationToGameObject(GameObject otherObject) {

        if(getStaticCollisionBox().getBounds().getX() > otherObject.getStaticCollisionBox().getBounds().getX()
                + otherObject.collisionBoxWidth - 2
                && getStaticCollisionBox().getCenter().getY() > otherObject.getStaticCollisionBox().getBounds().getY()
                && getStaticCollisionBox().getCenter().getY() < otherObject.getStaticCollisionBox().getBounds().getY()
                + otherObject.collisionBoxHeight){

            return Direction.RIGHT;

        }else if(getStaticCollisionBox().getBounds().getX() + collisionBoxWidth < otherObject.getStaticCollisionBox().getBounds().getX() + 2
                && getStaticCollisionBox().getCenter().getY() > otherObject.getStaticCollisionBox().getBounds().getY()
                && getStaticCollisionBox().getCenter().getY() < otherObject.getStaticCollisionBox().getBounds().getY()
                + otherObject.collisionBoxHeight){

            return Direction.LEFT;

        }else if(getStaticCollisionBox().getBounds().getY() + collisionBoxHeight < otherObject.getStaticCollisionBox().getBounds().getY() + 2
                && getStaticCollisionBox().getCenter().getX() > otherObject.getStaticCollisionBox().getBounds().getX()
                && getStaticCollisionBox().getCenter().getX() < otherObject.getStaticCollisionBox().getBounds().getX()
                + otherObject.collisionBoxWidth){

            return Direction.UP;

        }else if(getStaticCollisionBox().getBounds().getY() > otherObject.getStaticCollisionBox().getBounds().getY()
                + otherObject.collisionBoxHeight - 2
                && getStaticCollisionBox().getCenter().getX() > otherObject.getStaticCollisionBox().getBounds().getX()
                && getStaticCollisionBox().getCenter().getX() < otherObject.getStaticCollisionBox().getBounds().getX()
                + otherObject.collisionBoxWidth){

            return Direction.DOWN;
        }

        return Direction.NULL;
    }

    /**
     * Implements what should happen when the player interacts with this object.
     */
    protected abstract void executePlayerAction(Game game);

    public void addQuest(Quest quest) {
        quests.add(quest);
    }
}
