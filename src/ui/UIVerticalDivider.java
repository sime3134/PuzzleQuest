package ui;

import main.Game;
import utilities.ImgUtils;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements the UI dividing content.
 */
public class UIVerticalDivider extends UIComponent{

    private Image sprite;

    public UIVerticalDivider(int width) {
        this.width = width;
        height = 2;
        padding = new Spacing(0);
        margin = new Spacing(0, 16);
        generateSprite();
    }

    private void generateSprite() {
        sprite = ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_OPAQUE);
        Graphics2D g = (Graphics2D) sprite.getGraphics();

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);

        g.dispose();
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    @Override
    public void update(Game game) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite,
                getAbsolutePosition().intX(),
                getAbsolutePosition().intY(),
                null);
    }
}
