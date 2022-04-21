package input.mouse;

import main.Game;

/**
 * @author Simon Jern
 * Interface for classes that should be able to take control of mouse input.
 */
public interface MouseConsumer {

    void onClick(Game game);
    void onDrag(Game game);
    void onRelease(Game game);

}
