package core;

import main.GameLoop;

public class Time {
    public static int getNumberOfUpdatesFromSeconds(int seconds) {
        return seconds * GameLoop.FPS_LOCK;
    }
}
