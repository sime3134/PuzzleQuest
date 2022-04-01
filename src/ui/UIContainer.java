package ui;

import core.Vector2D;
import main.state.State;
import settings.GameSettings;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all UI containers.
 */
public abstract class UIContainer extends UIComponent{

    private final GameSettings settings = GameSettings.getInstance();

    protected Color backgroundColor;

    protected int fixedWidth;
    protected int fixedHeight;

    protected Alignment alignment;

    protected List<UIComponent> children;

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public void setFixedHeight(int fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    protected UIContainer(){
        super();
        alignment = new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP);
        backgroundColor = new Color(0, 0, 0, 0);
        margin = new Spacing(5);
        padding = new Spacing(5);
        children = new ArrayList<>();
        calculateSize();
        calculatePosition();
    }

    protected abstract int calculateContentWidth();
    protected abstract int calculateContentHeight();
    protected abstract void calculateContentPosition();

    private void calculateSize() {
        int calculatedWidth = calculateContentWidth();
        int calculatedHeight = calculateContentHeight();
        width = fixedWidth != 0
            ? fixedWidth : padding.getHorizontal() + calculatedWidth;
        height = fixedHeight != 0
            ? fixedHeight : padding.getVertical() + calculatedHeight;
    }

    protected void calculatePosition() {
        int x = padding.getLeft();
        if(alignment.getHorizontal().equals(Alignment.Horizontal.CENTER)){
            x = settings.getScreenWidth() / 2 - width / 2;
        }
        if(alignment.getHorizontal().equals(Alignment.Horizontal.RIGHT)){
            x = settings.getScreenWidth() - width - margin.getRight();
        }

        int y = padding.getTop();
        if(alignment.getVertical().equals(Alignment.Vertical.CENTER)){
            y = settings.getScreenHeight() / 2 - height / 2;
        }
        if(alignment.getVertical().equals(Alignment.Vertical.BOTTOM)){
            x = settings.getScreenHeight() - height - margin.getTop();
        }

        this.relativePosition = new Vector2D(x, y);
        this.absolutePosition = new Vector2D(x, y);
        calculateContentPosition();
    }

    @Override
    public Image getSprite() {
        return createUiSprite();
    }

    private Image createUiSprite() {
        BufferedImage image = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        graphics.dispose();
        return image;
    }

    public void addComponent(UIComponent uiComponent){
        children.add(uiComponent);
    }

    @Override
    public void update(State state) {
        children.forEach(component -> component.update(state));
        calculateSize();
        calculatePosition();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );

        for(UIComponent component : children){
            component.draw(g);
        }
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }
}
