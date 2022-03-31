package ui;

import core.Vector2D;

public class HorizontalContainer extends UIContainer{

    public HorizontalContainer(int margin, int padding) {
        super(margin, padding);
    }

    public HorizontalContainer(){
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
        int currentX = position.intX() + padding.getLeft();

        for(UIComponent child : children){
            currentX += child.getMargin().getLeft();
            child.setPosition(new Vector2D(currentX, position.intY() + padding.getTop()));
            currentX += child.getWidth();
            currentX += child.getMargin().getRight();
        }
    }
}
