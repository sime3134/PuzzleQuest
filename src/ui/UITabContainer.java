package ui;

import main.Game;
import main.state.State;
import ui.clickable.UIClickable;
import ui.clickable.UIHideButton;

import java.awt.*;

public class UITabContainer extends VerticalContainer{

    private final UIContainer tabContainer;
    private final UIContainer contentContainer;
    private UITab activeTab;

    public UITabContainer() {
        tabContainer = new HorizontalContainer();
        contentContainer = new VerticalContainer();

        setMargin(new Spacing(0));

        tabContainer.setPadding(new Spacing(0));
        tabContainer.setMargin(new Spacing(0));
        contentContainer.setMargin(new Spacing(0));
        contentContainer.setBackgroundColor(Color.GRAY);

        addComponent(tabContainer);
        addComponent(contentContainer);

        setupHideButton();
    }

    private void setupHideButton() {
        tabContainer.addComponent(new UIHideButton(this, contentContainer));
    }

    public void addTab(String labelText, UIContainer contents) {
        UITab uiTab = new UITab(this, labelText, contents);
        tabContainer.addComponent(uiTab);

        if(activeTab == null){
            activateTab(uiTab);
        }
    }

    private void activateTab(UITab uiTab) {
        activeTab = uiTab;
        contentContainer.clear();
        contentContainer.addComponent(uiTab.getContents());
    }

    public UITab getActiveTab() {
        return activeTab;
    }

    private static class UITab extends UIClickable {

        private final UITabContainer parent;
        private final UIContainer label;
        private final UIText labelText;
        private final UIContainer contents;

        public UITab(UITabContainer parent, String labelText, UIContainer contents) {
            this.parent = parent;
            this.contents = contents;

            contents.setMargin(new Spacing(0));
            contents.setPadding(new Spacing(0));

            this.label = new HorizontalContainer();
            this.labelText = new UIText(labelText);
            this.label.addComponent(this.labelText);
            this.label.setBackgroundColor(Color.DARK_GRAY);
        }

        @Override
        public void update(State state) {
            super.update(state);
            label.update(state);
            width = label.getWidth();
            height = label.getHeight();

            label.setBackgroundColor(Color.DARK_GRAY);

            if(hasFocus){
                label.setBackgroundColor(Color.LIGHT_GRAY);
            }

            if(parent.getActiveTab().equals(this)){
                label.setBackgroundColor(Color.GRAY);
            }
        }

        @Override
        public void onClick(Game game) {
            parent.activateTab(this);
        }

        @Override
        public void onDrag(Game game) {

        }

        @Override
        public void onRelease(Game game) {

        }

        @Override
        public Image getSprite() {
            return label.getSprite();
        }

        @Override
        public void draw(Graphics g) {
            g.drawImage(
                    getSprite(),
                    absolutePosition.intX(),
                    absolutePosition.intY(),
                    null
            );

            g.drawImage(
                    labelText.getSprite(),
                    absolutePosition.intX() + labelText.getRelativePosition().intX(),
                    absolutePosition.intY() + labelText.getRelativePosition().intY(),
                    null
            );
        }

        public UIComponent getContents() {
            return contents;
        }
    }
}
