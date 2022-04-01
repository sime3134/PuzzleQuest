package ui;

import core.Vector2D;

/**
 * A horizontal UI container.
 */
public class HorizontalContainer extends UIContainer{

    public HorizontalContainer() {
        super();
    }

    @Override
    protected int calculateContentWidth() {
        int combinedWidth = 0;

        for(UIComponent child : children){
            combinedWidth += child.getWidth() + child.getMargin().getHorizontal();
        }
        return combinedWidth;
    }

    @Override
    protected int calculateContentHeight() {
        int tallestChildHeight = 0;
        for(UIComponent child : children){
            if(child.getHeight() > tallestChildHeight){
                tallestChildHeight = child.getHeight();
            }
        }
        return tallestChildHeight;
    }

    @Override
    protected void calculateContentPosition() {
        int currentX = padding.getLeft();

        for(UIComponent child : children){
            currentX += child.getMargin().getLeft();
            child.setRelativePosition(new Vector2D(currentX, padding.getTop()));
            child.setAbsolutePosition((new Vector2D(currentX + absolutePosition.getX(), padding.getTop() + absolutePosition.getY())));
            currentX += child.getWidth();
            currentX += child.getMargin().getRight();
        }
    }
}
