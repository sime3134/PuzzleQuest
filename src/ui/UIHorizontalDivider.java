package ui;

import main.Game;
import utilities.ImgUtils;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements the UI dividing content.
 */
public class UIHorizontalDivider extends UIComponent{

    private Image sprite;

    public UIHorizontalDivider(int height) {
        width = 2;
        this.height = height;
        padding = new Spacing(0);
        margin = new Spacing(0);
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
    public void draw(Game game, Graphics g) {
        g.drawImage(sprite,
                getAbsolutePosition().intX(),
                getAbsolutePosition().intY(),
                null);
    }
}
