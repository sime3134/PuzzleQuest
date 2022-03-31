package core;

import display.Camera;

import java.awt.*;

/**
 * Implements collision boxes that are used to check for collision and similar between objects.
 */
public class CollisionBox {
    private Rectangle bounds;

    public Rectangle getBounds() {
        return bounds;
    }

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
    }

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
