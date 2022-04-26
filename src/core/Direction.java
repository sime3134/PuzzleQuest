package core;

/**
 * @author Simon Jern
 * The direction an entity is facing. Can be used for choosing the right animation.
 */
public enum Direction {
    DOWN(0),
    LEFT(1),
    RIGHT(2),
    UP(3),

    NULL(4);

    private final int animationRow;

    Direction(int animationRow){
        this.animationRow = animationRow;
    }

    public static Direction fromMotion(Vector2D velocity, Direction lastDirection){
        double x = velocity.getX();
        double y = velocity.getY();

        if(x == 0 && y > 0) return DOWN;
        if(x < 0 && y == 0) return LEFT;
        if(x == 0 && y < 0) return UP;
        if(x > 0 && y == 0) return RIGHT;
        if(x < 0 && y > 0) return DOWN;
        if(x < 0 && y < 0) return UP;
        if(x > 0 && y < 0) return UP;
        if(x > 0 && y > 0) return DOWN;

        return lastDirection;
    }

    /**
     * @return the opposite of the given direction
     */
    public static Direction opposite(Direction direction) {
        return switch(direction){
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            case UP -> DOWN;
            case NULL -> NULL;
        };
    }

    public int getAnimationRow(){
        return this.animationRow;
    }

    public static Vector2D toVelocity(Direction direction){
        return switch(direction){
            case DOWN -> new Vector2D(1, 0);
            case LEFT -> new Vector2D(0, -1);
            case RIGHT -> new Vector2D(0, 1);
            case UP -> new Vector2D(-1, 0);
            case NULL -> null;
        };
    }
}
