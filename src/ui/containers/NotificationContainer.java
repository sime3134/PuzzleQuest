package ui.containers;

import core.Vector2D;
import main.Game;
import settings.Settings;
import ui.clickable.UIText;

import java.awt.*;

/**
 * @author Simon Jern
 * A container displaying a notification to display to the player.
 */
public class NotificationContainer extends VerticalContainer{

    private int numberOfUpdatesWaiting;

    private final UIText notificationText;

    private final UIText secondRow;

    private int order;

    private NotificationContainer notificationBefore;

    public void setNotificationText(String notificationString) {
        this.notificationText.setText(notificationString);
    }

    public void setOriginPosition(int order, NotificationContainer notificationContainer) {
        this.order = order;
        setAbsolutePosition(new Vector2D(Settings.getScreenWidth(),
                getFixedHeight() * order));
        this.notificationBefore = notificationContainer;
    }

    public void setNumberOfUpdatesWaiting(int numberOfUpdatesWaiting) {
        this.numberOfUpdatesWaiting = numberOfUpdatesWaiting;
    }

    public NotificationContainer(){
        super();
        setFixedPosition(true);
        setBackgroundColor(Color.DARK_GRAY);
        setFixedWidth(800);
        setCenterChildren(true);
        notificationText = new UIText("");
        notificationText.setFontSize(16);
        secondRow = new UIText("[Q] to view");
        secondRow.setFontSize(16);
        setAbsolutePosition(new Vector2D(Settings.getScreenWidth(),
                getFixedHeight() * order));
        addComponent(notificationText);
    }

    @Override
    public void update(Game game){
        super.update(game);
        setFixedWidth(notificationText.getWidth() + 20);
        if(notificationBefore != null){
            setAbsolutePosition(new Vector2D(Settings.getScreenWidth() - getFixedWidth() - 10,
                    notificationBefore.getAbsolutePosition().getY() + notificationBefore.getHeight() + 10));
        }else {
            setAbsolutePosition(new Vector2D(Settings.getScreenWidth() - getFixedWidth() - 10,
                    10));
        }
        numberOfUpdatesWaiting++;
    }

    public boolean finished(Game game){
        return numberOfUpdatesWaiting >= game.getTime().getUpdatesFromSeconds(5);
    }

    public void setSecondRow(boolean questLog) {
        if(questLog){
            addComponent(secondRow);
        }else{
            removeComponent(secondRow);
        }
    }
}
