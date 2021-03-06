package ui;

import main.Game;
import ui.containers.NotificationContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Jern
 * Manages all notifications being showed on the screen for the player.
 */
public class NotificationManager {

    private final List<NotificationContainer> notifications;

    private final List<NotificationContainer> unusedNotification;
    private final List<NotificationContainer> finishedNotifications;

    public NotificationManager(){
        notifications = new ArrayList<>();
        finishedNotifications = new ArrayList<>();
        unusedNotification = new ArrayList<>();
        unusedNotification.add(new NotificationContainer());
        unusedNotification.add(new NotificationContainer());
        unusedNotification.add(new NotificationContainer());
        unusedNotification.add(new NotificationContainer());
        unusedNotification.add(new NotificationContainer());
        unusedNotification.add(new NotificationContainer());
    }

    public void displayNotification(String notification, boolean questLog){
        NotificationContainer container = unusedNotification.get(0);
        unusedNotification.remove(0);
        if(notifications.size() > 0) {
            container.setOriginPosition(notifications.size(), notifications.get(notifications.size() - 1));
        }else{
            container.setOriginPosition(0, null);
        }
        container.setNotificationText(notification);
        container.setNumberOfUpdatesWaiting(0);
        container.setSecondRow(questLog);
        notifications.add(container);
    }

    public void update(Game game){
        for(NotificationContainer container : notifications){
            container.update(game);
            if(container.finished(game)){
                finishedNotifications.add(container);
            }
        }

        for(NotificationContainer container : finishedNotifications){
            container.setSecondRow(false);
            notifications.remove(container);
            unusedNotification.add(container);
        }

        finishedNotifications.clear();
    }

    public void draw(Game game, Graphics g){
        for(NotificationContainer container : notifications){
            container.draw(game, g);
        }
    }
}
