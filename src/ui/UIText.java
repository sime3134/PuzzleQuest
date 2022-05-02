package ui;

import main.Game;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * Implements a UI component that displays text.
 */
public class UIText extends UIComponent{

    protected String text;
    private int fontSize;
    private int fontStyle;
    private String fontFamily;
    private Color fontColor;

    private boolean dropShadow;
    private int dropShadowOffset;
    private Color shadowColor;

    private Font font;

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UIText(String text) {
        super();
        setupText(text);
    }

    private void setupText(String text){
        this.text = text;
        this.fontSize = 16;
        this.fontStyle = Font.BOLD;
        this.fontFamily = "Joystix Monospace";
        this.fontColor = Color.WHITE;

        this.dropShadow = true;
        this.dropShadowOffset = 2;
        this.shadowColor = new Color(140, 140, 140);
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        if(dropShadow){
            graphics.setColor(shadowColor);
            graphics.drawString(text, padding.getLeft() + dropShadowOffset, fontSize + padding.getTop() + dropShadowOffset);
        }

        graphics.setColor(fontColor);
        graphics.drawString(text, padding.getLeft(), fontSize + padding.getTop());

        graphics.dispose();
        return image;
    }

    @Override
    public void update(Game game) {
        createFont();
        calculateSize();
    }

    private void calculateSize() {
        FontMetrics metrics = new Canvas().getFontMetrics(font);
        width = metrics.stringWidth(text) + padding.getHorizontal();
        height = metrics.getHeight() + padding.getVertical();
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    @Override
    public void draw(Graphics g) {
        if(visible) {
            g.drawImage(getSprite(),
                    absolutePosition.intX(),
                    absolutePosition.intY(),
                    null);
        }
    }
}
