package ui.containers;

import core.Vector2D;
import main.Game;
import settings.Settings;
import ui.clickable.UIText;

import java.awt.*;

public class NotificationContainer extends HorizontalContainer{

    private int numberOfUpdatesWaiting;

    private final UIText notificationText;

    private final int order;

    public NotificationContainer(int order, String notificationString){
        super();
        setFixedPosition(true);
        this.order = order;
        setBackgroundColor(Color.DARK_GRAY);
        setFixedHeight(40);
        setFixedWidth(500);
        setCenterChildren(true);
        setAbsolutePosition(new Vector2D(Settings.getScreenWidth() - getFixedWidth(),
                getFixedHeight() * order));
        notificationText = new UIText(notificationString);
        notificationText.setFontSize(14);
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
        return numberOfUpdatesWaiting >= game.getTime().getUpdatesFromSeconds(3);
    }
}
