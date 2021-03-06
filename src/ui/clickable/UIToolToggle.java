package ui.clickable;

import input.mouse.action.MouseAction;
import main.Game;
import ui.UIImage;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * Implements the UI for a clickable component that will toggle the use of a tool.
 */
public class UIToolToggle extends UIClickable{

    private final UIImage image;
    private BufferedImage activeSprite;
    private final MouseAction mouseAction;
    private boolean active;

    public UIToolToggle(Image image, MouseAction mouseAction, int minimizedWidth, int minimizedHeight) {
        if(image.getWidth(null) != minimizedWidth && image.getHeight(null) != minimizedHeight) {
            this.image = new UIImage(image.getScaledInstance(minimizedWidth, minimizedHeight, 0));
        }else{
            this.image = new UIImage(image);
        }
        this.mouseAction = mouseAction;

        width = this.image.getWidth();
        height = this.image.getHeight();

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
    public void update(Game game) {
        super.update(game);
        if(game.getCurrentState().getMouseHandler().getLeftButtonAction() != null){
            active = game.getCurrentState().getMouseHandler().getLeftButtonAction().equals(mouseAction);
        }
    }

    @Override
    public void onClick(Game game) {
        if(game.getCurrentState().getMouseHandler().getLeftButtonAction() != mouseAction) {
            game.getCurrentState().getMouseHandler().switchLeftButtonAction(mouseAction);
        }else{
            game.getCurrentState().getMouseHandler().switchLeftButtonAction(
                    game.getCurrentState().getMouseHandler().getLastLeftButtonAction()
            );
            active = false;
        }
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public Image getSprite() {
        if(active){
            return activeSprite;
        }

        if(hasFocus){
            Image imageWithFocus = ImgUtils.createCompatibleImage(image.getWidth(), image.getHeight(), ImgUtils.ALPHA_OPAQUE);
            Graphics2D g = (Graphics2D) imageWithFocus.getGraphics();

            g.drawImage(image.getSprite(), 0, 0, null);

            g.setColor(new Color(255, 255, 255, 75));
            g.fillRect(0, 0, image.getWidth(), image.getHeight());

            g.dispose();

            return imageWithFocus;
        }

        return image.getSprite();
    }

    @Override
    public void draw(Game game, Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );
    }
}
