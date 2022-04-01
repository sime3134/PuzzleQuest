package ui;

import core.Vector2D;

/**
 * A vertical UI container.
 */
public class VerticalContainer extends UIContainer{

    public VerticalContainer() {
        super();
    }

    @Override
    protected int calculateContentWidth() {
        int widestChildWidth = 0;

        for(UIComponent child : children){
            if(child.getWidth() > widestChildWidth){
                widestChildWidth = child.getWidth();
            }
        }
        return widestChildWidth;
    }

    @Override
    protected int calculateContentHeight() {
        int combinedHeight = 0;

        for(UIComponent child : children){
            combinedHeight += child.getHeight() + child.getMargin().getVertical();
        }
        return combinedHeight;
    }

    @Override
    protected void calculateContentPosition() {
        int currentY = padding.getTop();

        for(UIComponent child : children){
            currentY += child.getMargin().getTop();
            child.setRelativePosition(new Vector2D(padding.getLeft(), currentY));
            child.setAbsolutePosition(new Vector2D(padding.getLeft() + absolutePosition.getX(), currentY + absolutePosition.getY()));
            currentY += child.getHeight();
            currentY += child.getMargin().getBottom();
        }
    }
}
