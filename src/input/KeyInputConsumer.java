package input;

import main.Game;

/**
 * @author Simon Jern
 * Interface for classes that should be able to take control of the keyboard input.
 */
public interface KeyInputConsumer {
    void onKeyPressed(Game game, int key);
}
