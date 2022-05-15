package ui;

import main.Game;
import ui.containers.NotificationContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {

    private final List<NotificationContainer> notifications;
    private final List<NotificationContainer> finishedNotifications;

    public NotificationManager(){
        notifications = new ArrayList<>();
        finishedNotifications = new ArrayList<>();
    }

    public void displayNotification(String notification){
        notifications.add(new NotificationContainer(notifications.size(), notification));
    }

    public void update(Game game){
        for(NotificationContainer container : notifications){
            container.update(game);
            if(container.finished(game)){
                finishedNotifications.add(container);
            }
        }

        for(NotificationContainer container : finishedNotifications){
            notifications.remove(container);
        }
    }

    public void draw(Graphics g){
        for(NotificationContainer container : notifications){
            container.draw(g);
        }
    }
}
