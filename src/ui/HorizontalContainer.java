package ui;

import core.Vector2D;

/**
 * @author Simon Jern
 * A horizontal UI container.
 */
public class HorizontalContainer extends UIContainer{

    public HorizontalContainer(UIComponent... components) {
        super(components);
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
        int currentY = padding.getTop();

        for(UIComponent child : children){
            if(centerChildren){
                currentY = getHeight() / 2 - child.getHeight() / 2;
            }
            currentX += child.getMargin().getLeft();
            child.setRelativePosition(new Vector2D(currentX, currentY));
            child.setAbsolutePosition((new Vector2D(currentX + absolutePosition.getX(), currentY + absolutePosition.getY())));
            currentX += child.getWidth();
            currentX += child.getMargin().getRight();
        }
    }
}
