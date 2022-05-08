package input;

import main.Game;

public interface KeyInputConsumer {
    void onKeyPressed(Game game, int key);
}
