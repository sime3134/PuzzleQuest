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

    protected MovingEntity(EntityController entityController, double speed, SpriteSet spriteSet,
                           int spriteWidth, int spriteHeight) {
        super(spriteWidth, spriteHeight, spriteWidth / 2, spriteHeight / 2);
        this.entityController = entityController;
        this.velocity = new Vector2D(0,0);
        this.speed = speed;
        this.direction = Direction.DOWN;
        this.directionVector = new Vector2D(0,0);
        animationManager = new AnimationManager(spriteSet, spriteWidth, spriteHeight);
    }

    @Override
    public void update(State state){
        handleMovement();
        checkForCollisions(state);
        updateDirection();
        animationManager.update(direction);
        setAnimation();

        position.add(velocity);
    }

    @Override
    public void draw(Graphics g, Camera camera){
        g.drawImage(animationManager.getSprite(),
                getRenderPosition(camera).intX(),
                getRenderPosition(camera).intY(),
                null);

    }

    private void checkForCollisions(State state) {
        state.getCollidingBoxes(getCollisionBox()).forEach(entity -> handleCollision(entity));
    }

    protected abstract void handleCollision(GameObject other);

    private void setAnimation() {

        if(isMoving()){
            animationManager.setAnimation("walk");
        }else{
            animationManager.setAnimation("stand");
        }
    }

    /**
     * Updates the direction the entity is facing.
     */
    private void updateDirection() {
        if(isMoving()){
            direction = Direction.fromMotion(velocity);
            directionVector = velocity.getCopy();
            directionVector.normalize();
        }
    }

    private boolean isMoving() {
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

        velocity.normalize();
        velocity.multiply(speed);
    }

    @Override
    public CollisionBox getCollisionBox() {
        Vector2D positionWithVelocity = getPosition().getCopy();
        positionWithVelocity.add(velocity);

        return CollisionBox.of(
                new Vector2D(positionWithVelocity.intX() + collisionBoxWidth / 2f,
                positionWithVelocity.intY() + collisionBoxHeight / 2f),
                collisionBoxWidth,
                collisionBoxHeight
        );
    }

    public boolean willCollideX(CollisionBox otherBox) {
        Vector2D positionWithXApplied = getPosition().getCopy();

        positionWithXApplied.add(new Vector2D(velocity.getX(),0));
        return CollisionBox.of(
                new Vector2D(positionWithXApplied.intX() + collisionBoxWidth / 2f,
                        getPosition().intY() + collisionBoxHeight / 2f),
                collisionBoxWidth,
                collisionBoxHeight
        ).collidingWith(otherBox);
    }

    public boolean willCollideY(CollisionBox otherBox) {
        Vector2D positionWithYApplied = getPosition().getCopy();

        positionWithYApplied.add(new Vector2D(0, velocity.getY()));

        return CollisionBox.of(
                new Vector2D(getPosition().intX() + collisionBoxWidth / 2f,
                        positionWithYApplied.intY() + collisionBoxHeight / 2f),
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
