package core;

import display.Camera;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements collision boxes that are used to check for collision and similar between objects.
 */
public class CollisionBox {
    private final Rectangle bounds;

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2D getCenter() { return new Vector2D(bounds.getX() + bounds.getWidth() / 2,
            bounds.getY() + bounds.getHeight() / 2); }

    public CollisionBox(Rectangle bounds) {
        this.bounds = bounds;
    }

    public boolean collidingWith(CollisionBox other) {
        return bounds.intersects(other.getBounds());
    }

    public void draw(Graphics graphics, Camera camera) {
        graphics.setColor(Color.RED);
        graphics.drawRect(
                bounds.x - camera.getPosition().intX(),
                bounds.y - camera.getPosition().intY(),
                bounds.width,
                bounds.height);
        graphics.drawRect(
                getCenter().intX() - camera.getPosition().intX(),
                getCenter().intY() - camera.getPosition().intY(),
                2,
                2);
    }

    /**
     * @param position position of the top left corner of the rectangle to create
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @return a rectangle with the proportions above
     */
    public static CollisionBox of(Vector2D position, int width, int height){
        return new CollisionBox(
                new Rectangle(
                        position.intX(),
                        position.intY(),
                        width,
                        height
                )
        );
    }
}
