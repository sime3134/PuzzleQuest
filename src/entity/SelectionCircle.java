package entity;

import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import main.state.State;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * A graphic circle to display who the player currently interacts with.
 */
public class SelectionCircle extends GameObject{

    private final Color color;
    private final BufferedImage sprite;

    public SelectionCircle() {
        super();
        width = 38;
        height = 22;
        collisionBoxWidth = 1;
        collisionBoxHeight = 1;
        color = Color.ORANGE;
        sprite = createSprite();
        renderOffset = new Vector2D(5, height + 9f);
        renderOrder = 4;
    }

    private BufferedImage createSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D g = image.createGraphics();

        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawOval(0,0, width, height);

        g.dispose();
        return image;
    }

    @Override
    public void update(State state) {}

    @Override
    public void draw(Graphics g, Camera camera) {
        g.drawImage(parent != null ? sprite : null,
                getRenderPosition(camera).intX(),
                getRenderPosition(camera).intY(),
                null);
    }

    @Override
    public CollisionBox getCollisionBox() {
        Vector2D renderedPosition = getPosition().getCopy();
        renderedPosition.add(renderOffset);
        return CollisionBox.of(renderedPosition, collisionBoxWidth, collisionBoxHeight);
    }
}
