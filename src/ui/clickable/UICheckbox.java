package ui.clickable;

import core.Action;
import main.Game;
import core.Value;
import ui.*;
import ui.containers.HorizontalContainer;
import ui.containers.UIContainer;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * Implements the UI for a checkbox.
 */
public class UICheckbox extends UIComponent {

    private final UIContainer container;
    private final UIText text;


    public UICheckbox(String label, Value<Boolean> value) {
        this.container = new HorizontalContainer();
        this.text = new UIText(label);
        container.addComponent(new Checkbox(value));
        container.addComponent(text);
    }
    public UICheckbox(String label, Value<Boolean> value, Action action) {
        this.container = new HorizontalContainer();
        this.text = new UIText(label);
        container.addComponent(new Checkbox(value, action));
        container.addComponent(text);
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void update(Game game) {
        container.update(game);
        width = container.getWidth();
        height = container.getHeight();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
        container.setFixedPosition(fixedPosition);
    }

    @Override
    public void draw(Graphics g) {
        container.draw(g);
    }

    public void setFontSize(int size) {
        text.setFontSize(size);
    }

    private class Checkbox extends UIClickable{

        private final Value<Boolean> checked;
        private Color color;
        private final Action action;


        private Checkbox(Value<Boolean> checked) {
            this(checked, null);
        }

        private Checkbox(Value<Boolean> checked, Action action) {
            this.checked = checked;
            this.action = action;
            width = 20;
            height = 20;
            color = Color.GRAY;
            margin = new Spacing(0, 5, 0, 0);
        }

        @Override
        public void update(Game game) {
            super.update(game);
            color = checked.get() ? Color.WHITE : Color.LIGHT_GRAY;

            if(hasFocus) {
                color = Color.DARK_GRAY;
            }
        }

        @Override
        public Image getSprite() {
            BufferedImage sprite = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);

            Graphics2D g = sprite.createGraphics();

            g.setColor(color);
            if(checked.get()){
                g.drawRect(0,0, width - 1, height - 1);
                g.fillRect(2,2,width - 4,height - 4);
            }else{
                g.drawRect(0,0, width - 1, height - 1);
            }

            g.dispose();
            return sprite;
        }

        @Override
        public void onClick(Game game) {
            if(hasFocus) {
                checked.set(!checked.get());
            }
            if(action != null){
                action.execute(game);
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
                    null);
        }
    }
}
