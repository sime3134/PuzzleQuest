package ui;

import main.state.State;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
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

    public UIText(String text) {
        super();
        setupText(text);
    }

    public UIText(int margin, int padding, String text) {
        super(margin, padding);
        setupText(text);
    }

    private void setupText(String text){
        this.text = text;
        this.fontSize = 24;
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
    public void update(State state) {
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
        g.drawImage(getSprite(),
                position.intX(),
                position.intY(),
                null);
    }
}
