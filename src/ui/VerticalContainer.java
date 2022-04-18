package ui;

import core.Vector2D;

/**
 * @author Simon Jern
 * A vertical UI container.
 */
public class VerticalContainer extends UIContainer{

    public VerticalContainer(UIComponent... components) {
        super(components);
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
        int currentX = padding.getLeft();

        for(UIComponent child : children){
            if(centerChildren){
                currentX = getWidth() / 2 - child.getWidth() / 2;
            }
            currentY += child.getMargin().getTop();
            child.setRelativePosition(new Vector2D(currentX, currentY));
            child.setAbsolutePosition(new Vector2D(currentX + absolutePosition.getX(), currentY + absolutePosition.getY()));
            currentY += child.getHeight();
            currentY += child.getMargin().getBottom();
        }
    }
}
