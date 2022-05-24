package ui.clickable;

import main.Game;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern, Johan Salomonsson
 * Implements a UI component that displays text.
 */
public class UIText extends UIClickable {

    protected String text;
    private int fontSize;
    private int fontStyle;
    private String fontFamily;
    private Color fontColor;

    private boolean dropShadow;
    private int dropShadowOffset;
    private Color shadowColor;

    private Font font;

    private boolean underlined;

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public UIText(String text) {
        this(text, 16);
    }

    public UIText(String text, int fontSize) {
        super();
        setupText(text, fontSize);
    }

    private void setupText(String text, int fontSize){
        this.text = text;
        this.fontSize = fontSize;
        this.fontStyle = Font.BOLD;
        this.fontFamily = "Joystix Monospace";
        this.fontColor = Color.WHITE;

        this.dropShadow = true;
        this.dropShadowOffset = 2;
        this.shadowColor = new Color(140, 140, 140);
        createFont();
        calculateSize();
    }

    @Override
    public Image getSprite() {
        //TODO: Refactor to not create new image every draw
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);

        String[] lines = text.split("\n");
        for(int i = 0; i < lines.length; i++){

            int yPos = (lines.length > 1 && i != 0 ?
                    10 : 0) * i;

            if(dropShadow){
                graphics.setColor(shadowColor);
                graphics.drawString(lines[i], padding.getLeft() + dropShadowOffset,
                        (i + 1) * fontSize + padding.getTop() + dropShadowOffset + yPos);
            }

            graphics.setColor(fontColor);
            graphics.drawString(lines[i], padding.getLeft(),
                    (i + 1) * fontSize + padding.getTop() + yPos);
            if(underlined) {
                graphics.drawLine(padding.getLeft(),
                        (i + 1) * fontSize + padding.getTop() + (lines.length > 1 && i != 0 ? 10 : 0) + 2,
                        padding.getLeft() + width,
                        (i + 1) * fontSize + padding.getTop() + (lines.length > 1 && i != 0 ? 10 : 0) + 2);
            }
        }

        graphics.dispose();
        return image;
    }

    @Override
    public void update(Game game) {
        super.update(game);
        createFont();
        calculateSize();
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    private void calculateSize() {
        String[] lines = text.split("\n");
        FontMetrics metrics = new Canvas().getFontMetrics(font);
        int biggestWidth = 0;
        for(String line : lines) {
            biggestWidth = Math.max(biggestWidth,metrics.stringWidth(line));
        }
        width = Math.max(1, biggestWidth + padding.getHorizontal() + 10);
        height = Math.max(1, lines.length * metrics.getHeight() + padding.getVertical()
                + (lines.length > 1 ? 10 : 0) * lines.length);
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    @Override
    public void draw(Game game, Graphics g) {
        if(visible) {
            g.drawImage(getSprite(),
                    absolutePosition.intX(),
                    absolutePosition.intY(),
                    null);
        }
    }

    @Override
    public void onClick(Game game) {
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }
}
