package entity;

import content.AnimationManager;
import content.SpriteSet;
import controller.EntityController;
import core.CollisionBox;
import core.Direction;
import core.Vector2D;
import display.Camera;
import main.state.State;

import java.awt.*;

/**
 * @author Simon Jern
 * The core class for all objects that has some kind of movement in the game.
 */
public abstract class MovingEntity extends GameObject {

    private final EntityController entityController;
    protected AnimationManager animationManager;
    protected Direction direction;
    protected Vector2D velocity;
    protected double speed;

    protected Vector2D directionVector;

    public EntityController getController() {
        return entityController;
    }

    protected MovingEntity(EntityController entityController, SpriteSet spriteSet) {
        super();
        this.entityController = entityController;
        this.velocity = new Vector2D(0,0);
        this.speed = 4;
        this.direction = Direction.DOWN;
        this.directionVector = new Vector2D(0,0);
        animationManager = new AnimationManager(spriteSet, getWidth(), getHeight());
    }

    @Override
    public void update(State state){
        handleMovement();
        animationManager.update(direction);
        checkForCollisions(state);
        checkForTileCollisions(state);
        updateDirection();
        animationManager.playAnimation(decideAnimation());
        decideAnimation();

        position.add(velocity);
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    @Override
    public void draw(Graphics g, Camera camera){
        g.drawImage(animationManager.getSprite(),
                getRenderPosition(camera).intX(),
                getRenderPosition(camera).intY(),
                null);
    }

    private void checkForCollisions(State state) {
        state.getCollidingBoxes(getCollisionBox()).forEach(this::handleCollision);
    }

    private void checkForTileCollisions(State state) {
        state.getCurrentMap().getCollidingUnwalkableTileBoxes(getCollisionBox())
                .forEach(tileCollisionBox -> velocity.reset(willCollideX(tileCollisionBox), willCollideY(tileCollisionBox)));
    }

    protected abstract void handleCollision(GameObject other);

    protected abstract String decideAnimation();

    /**
     * Updates the direction the entity is facing.
     */
    private void updateDirection() {
        direction = Direction.fromMotion(velocity, direction);
        if(isMoving()) {
            directionVector = velocity.getCopy();
            directionVector.normalize();
        }
    }

    public boolean isMoving() {
        return Math.sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY()) > 0;
    }

    /**
     * Depending on requested movement, for example from a keyboard, moves the entity accordingly.
     */
    private void handleMovement() {
        int velocityX = 0;
        int velocityY = 0;

        if(entityController.requestedUp())
            velocityY--;
        if(entityController.requestedDown())
            velocityY++;
        if(entityController.requestedLeft())
            velocityX--;
        if(entityController.requestedRight())
            velocityX++;

        velocity = new Vector2D(velocityX, velocityY);

        updateDirection();

        velocity.normalize();
        velocity.multiply(speed);
    }

    @Override
    public CollisionBox getCollisionBox() {
        Vector2D positionWithVelocity = getPosition().getCopy();
        positionWithVelocity.add(velocity);

        return CollisionBox.of(
                new Vector2D(positionWithVelocity.intX() + collisionBoxWidth / 2f,
                positionWithVelocity.intY() + collisionBoxHeight / 2f + 11f),
                collisionBoxWidth,
                collisionBoxHeight
        );
    }

    public boolean willCollideX(CollisionBox otherBox) {
        Vector2D positionWithXApplied = getPosition().getCopy();

        positionWithXApplied.add(new Vector2D(velocity.getX(),0));
        return CollisionBox.of(
                new Vector2D(positionWithXApplied.intX() + collisionBoxWidth / 2f,
                        getPosition().intY() + collisionBoxHeight / 2f + 11f),
                collisionBoxWidth,
                collisionBoxHeight
        ).collidingWith(otherBox);
    }

    public boolean willCollideY(CollisionBox otherBox) {
        Vector2D positionWithYApplied = getPosition().getCopy();

        positionWithYApplied.add(new Vector2D(0, velocity.getY()));

        return CollisionBox.of(
                new Vector2D(getPosition().intX() + collisionBoxWidth / 2f,
                        positionWithYApplied.intY() + collisionBoxHeight / 2f + 11f),
                collisionBoxWidth,
                collisionBoxHeight
        ).collidingWith(otherBox);
    }
    public boolean isFacing(Vector2D position){
        Vector2D otherEntityDirection = position.directionBetweenPositions(getPosition());
        double dotProduct = Vector2D.dotProduct(otherEntityDirection, directionVector);

        return dotProduct > 0;
    }
}
