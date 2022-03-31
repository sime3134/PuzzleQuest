package core;

import main.GameLoop;

/**
 * Helper class for calculating in-game time.
 */
public class Time {
    public static int getNumberOfUpdatesFromSeconds(int seconds) {
        return seconds * GameLoop.FPS_LOCK;
    }
}
