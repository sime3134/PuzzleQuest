package ui.clickable;

import main.Game;
import main.state.State;
import settings.Setting;
import ui.*;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * Implements the UI for a checkbox.
 */
public class UICheckbox extends UIComponent {

    private final UIContainer container;

    public UICheckbox(String label, Setting<Boolean> setting) {
        this.container = new HorizontalContainer();
        container.addComponent(new Checkbox(setting));
        container.addComponent(new UIText(label));
    }

    @Override
    public Image getSprite() {
        return container.getSprite();
    }

    @Override
    public void update(State state) {
        container.update(state);
        width = container.getWidth();
        height = container.getHeight();
        container.setParent(parent);
        container.setAbsolutePosition(absolutePosition);
    }

    @Override
    public void draw(Graphics g) {
        container.draw(g);
    }

    private static class Checkbox extends UIClickable{

        private Setting<Boolean> setting;
        private Color color;

        private Checkbox(Setting<Boolean> setting) {
            this.setting = setting;
            width = 20;
            height = 20;
            color = Color.GRAY;
            margin = new Spacing(0, 5, 0, 0);
        }

        @Override
        public void update(State state) {
            super.update(state);
            color = setting.getValue() ? Color.WHITE : Color.LIGHT_GRAY;

            if(hasFocus) {
                color = Color.DARK_GRAY;
            }
        }

        @Override
        public Image getSprite() {
            BufferedImage sprite = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);

            Graphics2D g = sprite.createGraphics();

            g.setColor(color);
            if(setting.getValue()){
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
                setting.setValue(!setting.getValue());
            }
        }

        @Override
        public void onDrag(Game game) {
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
