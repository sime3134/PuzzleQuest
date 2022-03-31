package ui;

import core.Vector2D;
import main.state.State;

import java.awt.*;

/**
 * The base class for all UI components.
 */
public abstract class UIComponent {
    protected Vector2D position;
    protected int width;
    protected int height;
    protected Spacing margin;
    protected Spacing padding;

    protected UIComponent() {
        position = new Vector2D(0,0);
        this.width = 1;
        this.height = 1;
        this.margin = new Spacing(1);
        this.padding = new Spacing(1);
    }
    protected UIComponent(int margin, int padding) {
        position = new Vector2D(0,0);
        this.width = 1;
        this.height = 1;
        setMargin(margin);
        setPadding(padding);
    }

    public abstract Image getSprite();
    public abstract void update(State state);
    public abstract void draw(Graphics g);

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
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
}
