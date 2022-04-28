package ui.clickable;

import main.Game;
import ui.Spacing;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;

import java.awt.*;

/**
 * @author Simon Jern
 * Implements the UI for a clickable button.
 */
public class UIButton extends UIClickable{

    protected final UIContainer buttonContainer;
    protected final UIText buttonText;

    protected final ClickAction clickAction;

    private Color backgroundColor;
    private Color hoverColor;
    private Color clickColor;

    private Color borderColor;

    public UIButton(String buttonText, ClickAction clickAction) {
        this.buttonText = new UIText(buttonText);
        this.clickAction = clickAction;

        setMargin(new Spacing(5, 0, 0, 0));
        backgroundColor = Color.GRAY;
        hoverColor = Color.LIGHT_GRAY;
        clickColor = Color.DARK_GRAY;
        borderColor = Color.BLACK;

        buttonContainer = new VerticalContainer();
        buttonContainer.setCenterChildren(true);
        buttonContainer.addComponent(this.buttonText);
        buttonContainer.setFixedWidth(300);
    }

    @Override
    public void update(Game game) {
        super.update(game);
        buttonContainer.update(game);
        width = buttonContainer.getWidth();
        height = buttonContainer.getHeight();

        buttonContainer.setBorderColor(borderColor);
        buttonContainer.setBackgroundColor(backgroundColor);

        if(hasFocus){
            buttonContainer.setBackgroundColor(hoverColor);
        }

        if(isPressed){
            buttonContainer.setBackgroundColor(clickColor);
        }
    }

    @Override
    public void onClick(Game game) {
        if(hasFocus) {
            clickAction.execute();
        }
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );

        g.drawImage(buttonText.getSprite(),
                absolutePosition.intX() + buttonText.getRelativePosition().intX(),
                absolutePosition.intY() + buttonText.getRelativePosition().intY(),
                null
        );
    }

    @Override
    public Image getSprite() {
        return buttonContainer.getSprite();
    }

    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setClickColor(Color clickColor) {
        this.clickColor = clickColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public void setPadding(int padding) {
        buttonContainer.setPadding(padding);
    }

    @Override
    public void setMargin(int margin) {
        buttonContainer.setMargin(margin);
    }

    public void setFontSize(int size) {
        buttonText.setFontSize(size);
    }

    @Override
    public void setWidth(int width) {
        buttonContainer.setFixedWidth(width);
    }

    @Override
    public void setHeight(int height) {
        buttonContainer.setFixedHeight(height);
    }
}
