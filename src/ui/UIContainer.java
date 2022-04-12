package ui;

import core.Vector2D;
import main.state.State;
import settings.Settings;
import utilities.ImgUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Base class for all UI containers.
 */
public abstract class UIContainer extends UIComponent {

    protected boolean centerChildren;

    protected Color backgroundColor;

    protected int fixedWidth;
    protected int fixedHeight;

    protected Alignment alignment;

    protected List<UIComponent> children;

    protected Image sprite;

    //region Getters and Setters (click to view)
    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public void setFixedHeight(int fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    public void setCenterChildren(boolean centerChildren) {
        this.centerChildren = centerChildren;
    }

    //endregion

    protected UIContainer(){
        super();
        alignment = new Alignment(Alignment.Horizontal.LEFT, Alignment.Vertical.TOP);
        centerChildren = false;
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
            if (alignment.getHorizontal().equals(Alignment.Horizontal.CENTER)) {
                x = Settings.getScreenWidth() / 2 - width / 2;
            }
            if (alignment.getHorizontal().equals(Alignment.Horizontal.RIGHT)) {
                x = Settings.getScreenWidth() - width - margin.getRight();
            }

            int y = padding.getTop();
            if (alignment.getVertical().equals(Alignment.Vertical.CENTER)) {
                y = Settings.getScreenHeight() / 2 - height / 2;
            }
            if (alignment.getVertical().equals(Alignment.Vertical.BOTTOM)) {
                y = Settings.getScreenHeight() - height - margin.getTop();
            }

            this.relativePosition = new Vector2D(x, y);
            if (parent == null) {
                this.absolutePosition = new Vector2D(x, y);
            }
        calculateContentPosition();
    }

    private void createUiSprite() {
        sprite = ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        graphics.dispose();
    }

    public void addComponent(UIComponent uiComponent){
        children.add(uiComponent);
        uiComponent.setParent(this);
    }

    @Override
    public void update(State state) {
        calculateSize();
        calculatePosition();
        children.forEach(component -> component.update(state));

        if(state.getTime().secondsDividableBy(0.1)){
            createUiSprite();
        }
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

    protected void clear() {
        children.clear();
    }

    public boolean hasComponent(UIComponent component) {
        return children.contains(component);
    }

    public void removeComponent(UIComponent component) {
        children.remove(component);
    }
}
