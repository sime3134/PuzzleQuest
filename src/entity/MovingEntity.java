package entity;

import content.AnimationManager;
import content.SpriteSet;
import controller.EntityController;
import core.CollisionBox;
import core.Direction;
import core.Vector2D;
import display.Camera;
import main.Game;

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
    private boolean canMove;

    public EntityController getController() {
        return entityController;
    }

    @Override
    public Image getSprite() {
        return animationManager.getSprite();
    }

    protected MovingEntity(EntityController entityController){
        super();
        this.entityController = entityController;
        this.velocity = new Vector2D(0,0);
        this.directionVector = new Vector2D(0,0);
        this.canMove = true;
    }

    protected MovingEntity(EntityController entityController, SpriteSet spriteSet) {
        super();
        this.entityController = entityController;
        this.velocity = new Vector2D(0,0);
        this.speed = 4;
        this.direction = Direction.DOWN;
        this.directionVector = new Vector2D(0,0);
        animationManager = new AnimationManager(spriteSet, getWidth(), getHeight(), "stand");
        this.canMove = true;
    }

    @Override
    public void update(Game game){
        handleMovement();
        animationManager.update(direction);
        checkForCollisions(game);
        checkForTileCollisions(game);
        updateDirection();
        animationManager.playAnimation(decideAnimation());
        decideAnimation();

        position.add(velocity);
    }

    @Override
    public void draw(Graphics g, Camera camera){
        g.drawImage(animationManager.getSprite(),
                getRenderPosition(camera).intX(),
                getRenderPosition(camera).intY(),
                null);
    }

    private void checkForCollisions(Game game) {
        game.getCollidingBoxes(getCollisionBox()).forEach(gameObject -> handleCollision(game, gameObject));
    }

    private void checkForTileCollisions(Game game) {
        game.getCurrentMap().getCollidingUnwalkableTileBoxes(getCollisionBox())
                .forEach(tileCollisionBox -> velocity.reset(willCollideX(tileCollisionBox), willCollideY(tileCollisionBox)));
    }

    protected abstract void handleCollision(Game game, GameObject other);

    protected abstract String decideAnimation();

    /**
     * Updates the direction the entity is facing.
     */
    private void updateDirection() {
        direction = Direction.fromMotion(velocity, direction);
        if(isMoving()) {
            directionVector = velocity.getCopy();
        }
    }

    public boolean isMoving() {
        return Math.sqrt(velocity.getX() * velocity.getX() + velocity.getY() * velocity.getY()) > 0;
    }

    /**
     * Depending on requested movement, for example from a keyboard, moves the entity accordingly.
     */
    private void handleMovement() {
        if(canMove) {
            int velocityX = 0;
            int velocityY = 0;

            if (entityController.requestedUp())
                velocityY--;
            if (entityController.requestedDown())
                velocityY++;
            if (entityController.requestedLeft())
                velocityX--;
            if (entityController.requestedRight())
                velocityX++;

            velocity = new Vector2D(velocityX, velocityY);

            updateDirection();

            velocity.normalize();
            velocity.multiply(speed);
        }
    }

    @Override
    public CollisionBox getCollisionBox() {
        Vector2D positionWithVelocity = getPosition().getCopy();
        positionWithVelocity.add(velocity);

        return CollisionBox.of(
                new Vector2D(positionWithVelocity.getX() + collisionBoxOffset.getX(),
                positionWithVelocity.getY() + collisionBoxOffset.getY()),
                collisionBoxWidth,
                collisionBoxHeight
        );
    }

    public CollisionBox getStaticCollisionBox() {
        return CollisionBox.of(
                new Vector2D(getPosition().getX() + collisionBoxOffset.getX(),
                        getPosition().getY() + collisionBoxOffset.getY()),
                collisionBoxWidth,
                collisionBoxHeight
        );
    }

    public boolean willCollideX(CollisionBox otherBox) {
        Vector2D positionWithXApplied = getPosition().getCopy();

        positionWithXApplied.add(new Vector2D(velocity.getX(),0));
        return CollisionBox.of(
                new Vector2D(positionWithXApplied.intX() + collisionBoxOffset.getX(),
                        getPosition().intY() + collisionBoxOffset.getY()),
                collisionBoxWidth,
                collisionBoxHeight
        ).collidingWith(otherBox);
    }

    public boolean willCollideY(CollisionBox otherBox) {
        Vector2D positionWithYApplied = getPosition().getCopy();

        positionWithYApplied.add(new Vector2D(0, velocity.getY()));

        return CollisionBox.of(
                new Vector2D(getPosition().getX() + collisionBoxOffset.getX(),
                        positionWithYApplied.getY() + collisionBoxOffset.getY()),
                collisionBoxWidth,
                collisionBoxHeight
        ).collidingWith(otherBox);
    }
    public boolean isFacing(GameObject object){
        Direction objectDirectionInRelationToPlayer = getDirectionInRelationToGameObject(object);

        return objectDirectionInRelationToPlayer == Direction.opposite(direction);
    }

    public Vector2D getCollisionBoxGridPosition() {
        Vector2D grid = new Vector2D(getCollisionBox().getBounds().getX(), getCollisionBox().getBounds().getY()).grid();
        return Vector2D.ofGridPosition(grid);
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = Direction.valueOf(direction);
    }
}
