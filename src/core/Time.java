package core;

import main.GameLoop;

/**
 * @author Simon Jern
 * Implements counts and methods to handle in-game and real world time.
 */
public class Time {

    private int updatesSinceStart;

    public Time() {
        this.updatesSinceStart = 0;
    }

    public int getUpdatesFromSeconds(double seconds) {
        return (int) Math.round(seconds * GameLoop.FPS_LOCK);
    }

    public void update() {
        updatesSinceStart++;
    }

    public String getFormattedTime() {
        StringBuilder stringBuilder = new StringBuilder();
        int minutes = updatesSinceStart / GameLoop.FPS_LOCK / 60;
        int seconds = updatesSinceStart / GameLoop.FPS_LOCK % 60;

        if(minutes < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(minutes);
        stringBuilder.append(":");

        if(seconds < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(seconds);
        return stringBuilder.toString();
    }

    public boolean secondsDividableBy(double seconds){
        return updatesSinceStart % getUpdatesFromSeconds(seconds) == 0;
    }
}
