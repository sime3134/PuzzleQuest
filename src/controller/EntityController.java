package controller;

/**
 * Interface for controllers that control entities.
 */
public interface EntityController {
    boolean requestedUp();
    boolean requestedDown();
    boolean requestedLeft();
    boolean requestedRight();
    boolean requestedAction();
}
