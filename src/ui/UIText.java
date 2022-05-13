package ui;

import main.Game;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern, Johan Salomonsson
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

    public String getText() {
        return text;
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
        //TODO: Refactor to not create new image every draw
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        String[] lines = text.split("\n");
        for(int i = 0; i < lines.length; i++){
            if(dropShadow){
                graphics.setColor(shadowColor);
                graphics.drawString(lines[i], padding.getLeft() + dropShadowOffset,
                        (i + 1) * fontSize + padding.getTop() + dropShadowOffset + (lines.length > 1 && i != 0 ?
                                10 : 0));
            }

            graphics.setColor(fontColor);
            graphics.drawString(lines[i], padding.getLeft(),
                    (i + 1) * fontSize + padding.getTop() + (lines.length > 1 && i != 0 ? 10 : 0));
        }

        graphics.dispose();
        return image;
    }

    @Override
    public void update(Game game) {
        createFont();
        calculateSize();
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    private void calculateSize() {
        String[] lines = text.split("\n");
        FontMetrics metrics = new Canvas().getFontMetrics(font);
        width = Math.max(1, metrics.stringWidth(text) + padding.getHorizontal());
        height = Math.max(1, lines.length * metrics.getHeight() + padding.getVertical()
                + (lines.length > 1 ? 10 : 0));
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
    public void toggleVisible() {
        visible = !visible;
    }
}
