package input.mouse;

import main.Game;

public interface MouseConsumer {

    void onClick(Game game);
    void onDrag(Game game);
    void onRelease(Game game);

}
