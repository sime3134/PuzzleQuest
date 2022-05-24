package ui;

import core.Vector2D;
import main.Game;
import ui.containers.UIContainer;

import java.awt.*;

/**
 * @author Simon Jern
 * The base class for all UI components.
 */
public abstract class UIComponent {
    protected Vector2D relativePosition;
    protected Vector2D absolutePosition;
    protected int width;
    protected int height;
    protected Spacing margin;
    protected Spacing padding;

    protected UIContainer parent;

    protected boolean fixedPosition;

    protected boolean visible;

    //region Getters and Setters (click to open)

    public abstract Image getSprite();

    public Vector2D getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(Vector2D relativePosition) {
        this.relativePosition = relativePosition;
    }

    public Vector2D getAbsolutePosition() {
        return absolutePosition;
    }

    public void setAbsolutePosition(Vector2D absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Spacing getMargin() {
        return margin;
    }

    public void setMargin(Spacing margin) {
        this.margin = margin;
    }

    public void setMargin(int margin){
        if(margin > 0){
            this.margin = new Spacing(margin);
        }else{
            this.margin = new Spacing(1);
        }
    }

    public Spacing getPadding() {
        return padding;
    }

    public void setPadding(Spacing padding) {
        this.padding = padding;
    }

    public void setPadding(int padding){
        if(padding > 0){
            this.padding = new Spacing(padding);
        }else{
            this.padding = new Spacing(1);
        }
    }

    public void setParent(UIContainer parent) {
        this.parent = parent;
    }

    public void setFixedPosition(boolean fixedPosition) {
        this.fixedPosition = fixedPosition;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    //endregion

    protected UIComponent() {
        relativePosition = new Vector2D(0,0);
        absolutePosition = new Vector2D(0,0);
        this.width = 1;
        this.height = 1;
        this.margin = new Spacing(0);
        this.padding = new Spacing(0);
        this.visible = true;
    }

    public abstract void update(Game game);
    public abstract void draw(Game game, Graphics g);
}
