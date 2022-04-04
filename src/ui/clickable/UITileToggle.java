package ui.clickable;

import input.mouse.action.TilePlacer;
import main.state.State;
import map.Tile;
import ui.UIImage;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UITileToggle extends UIClickable{

    private final UIImage image;
    private BufferedImage activeSprite;
    private final TilePlacer tilePlacer;
    private boolean active;

    public UITileToggle(Tile tile) {
        image = new UIImage(tile.getSprite().getScaledInstance(16, 16, 0));
        tilePlacer = new TilePlacer(tile);
        width = image.getWidth();
        height = image.getHeight();
        generateActiveSprite();
    }

    private void generateActiveSprite() {
        activeSprite = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_OPAQUE);
        Graphics2D g = activeSprite.createGraphics();

        g.drawImage(image.getSprite(), 0, 0, null);

        g.setColor(new Color(255, 255, 255, 75));
        g.setComposite(AlphaComposite.SrcOver);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.drawRect(1, 1, width - 2, height - 2);

        g.dispose();
    }

    @Override
    public void update(State state) {
        super.update(state);
        if(state.getMouseHandler().getPrimaryButtonAction() != null){
            active = state.getMouseHandler().getPrimaryButtonAction().equals(tilePlacer);
        }
    }

    @Override
    public void onClick(State state) {
        state.getMouseHandler().setPrimaryButtonAction(tilePlacer);
    }

    @Override
    public void onDrag(State state) {

    }

    @Override
    public Image getSprite() {
        return active ? activeSprite : image.getSprite();
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
}
