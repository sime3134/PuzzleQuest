package core;

/**
 * @author Simon Jern
 * The direction an entity is facing. Can be used for choosing the right animation.
 */
public enum Direction {
    DOWN(0),
    LEFT(1),
    RIGHT(2),
    UP(3);

    private final int animationRow;

    Direction(int animationRow){
        this.animationRow = animationRow;
    }

    public static Direction fromMotion(Vector2D velocity){
        double x = velocity.getX();
        double y = velocity.getY();

        if(x == 0 && y > 0) return DOWN;
        if(x < 0 && y == 0) return LEFT;
        if(x == 0 && y < 0) return UP;
        if(x > 0 && y == 0) return RIGHT;
        if(x < 0 && y > 0) return DOWN;
        if(x < 0 && y < 0) return UP;
        if(x > 0 && y < 0) return UP;

        return DOWN;
    }

    public int getAnimationRow(){
        return this.animationRow;
    }
}
