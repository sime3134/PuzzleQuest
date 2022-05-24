package ui.containers;

import core.Vector2D;
import main.Game;
import settings.Settings;
import ui.clickable.UIText;

import java.awt.*;

public class NotificationContainer extends HorizontalContainer{

    private int numberOfUpdatesWaiting;

    private final UIText notificationText;

    private int order;

    public void setNotificationText(String notificationString) {
        this.notificationText.setText(notificationString);
    }

    public void setOrder(int order) {
        this.order = order;
        setAbsolutePosition(new Vector2D(Settings.getScreenWidth(),
                getFixedHeight() * order));
    }

    public void setNumberOfUpdatesWaiting(int numberOfUpdatesWaiting) {
        this.numberOfUpdatesWaiting = numberOfUpdatesWaiting;
    }

    public NotificationContainer(){
        super();
        setFixedPosition(true);
        this.order = 0;
        setBackgroundColor(Color.DARK_GRAY);
        setFixedHeight(70);
        setFixedWidth(800);
        setCenterChildren(true);
        notificationText = new UIText("");
        notificationText.setFontSize(16);
        setAbsolutePosition(new Vector2D(Settings.getScreenWidth(),
                getFixedHeight() * order));
        addComponent(notificationText);
    }

    @Override
    public void update(Game game){
        super.update(game);
        setFixedWidth(notificationText.getWidth() + 20);
        setAbsolutePosition(new Vector2D(Settings.getScreenWidth() - getFixedWidth() - 10,
                getFixedHeight() * order + 10 * order + 10));
        numberOfUpdatesWaiting++;
    }

    public boolean finished(Game game){
        return numberOfUpdatesWaiting >= game.getTime().getUpdatesFromSeconds(5);
    }
}
