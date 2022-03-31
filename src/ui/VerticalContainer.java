package ui;

import core.Vector2D;

/**
 * A vertical UI container.
 */
public class VerticalContainer extends UIContainer{

    public VerticalContainer(int margin, int padding) {
        super(margin, padding);
    }

    public VerticalContainer(){
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
        int currentY = position.intY() + padding.getTop();

        for(UIComponent child : children){
            currentY += child.getMargin().getTop();
            child.setPosition(new Vector2D(position.intX() + padding.getLeft(), currentY));
            currentY += child.getHeight();
            currentY += child.getMargin().getBottom();
        }
    }
}
