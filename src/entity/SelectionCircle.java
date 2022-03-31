package entity;

import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import main.state.State;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SelectionCircle extends GameObject{

    private final Color color;
    private final BufferedImage sprite;

    public SelectionCircle(int width, int height) {
        super(width, height, width, height);
        color = Color.ORANGE;
        sprite = createSprite();
        renderOffset = new Vector2D(0, height - 4f);
        renderOrder = 4;
    }

    private BufferedImage createSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D g = image.createGraphics();

        g.setColor(color);
        g.fillOval(0,0, width, height);

        g.dispose();
        return image;
    }

    @Override
    public void update(State state) {

    }

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
        return CollisionBox.of(renderedPosition, width, height);
    }
}
