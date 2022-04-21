package controller;

import core.Vector2D;
import settings.Settings;

/**
 * @author Simon Jern
 * Implements controller for NPCs.
 */
public class NPCController implements EntityController {
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    @Override
    public boolean requestedUp() {
        return up;
    }

    @Override
    public boolean requestedDown() {
        return down;
    }

    @Override
    public boolean requestedLeft() {
        return left;
    }

    @Override
    public boolean requestedRight() {
        return right;
    }

    @Override
    public boolean requestedAction() {
        return false;
    }

    public void moveToTarget(Vector2D target, Vector2D start) {
        double deltaX = target.getX() - start.getX();
        double deltaY = target.getY() - start.getY();

        up = deltaY < 0 && Math.abs(deltaY) > Settings.getProximityRange();
        right = deltaX > 0 && Math.abs(deltaX) > Settings.getProximityRange();
        down = deltaY > 0 && Math.abs(deltaY) > Settings.getProximityRange();
        left = deltaX < 0 && Math.abs(deltaX) > Settings.getProximityRange();
    }

    public void stop() {
        up = false;
        down = false;
        left = false;
        right = false;
    }
}
