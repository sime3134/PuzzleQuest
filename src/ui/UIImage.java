package ui;

import main.Game;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements a UI component that can display a picture with a position.
 */
public class UIImage extends UIComponent {

    private final Image image;

    public UIImage(Image image) {
        this.image = image;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    @Override
    public Image getSprite() {
        return image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );
    }
    @Override
    public void update(Game game) {}

}
