package input.mouse;

import main.Game;
import main.state.State;

public interface MouseConsumer {

    void onClick(Game game);
    void onDrag(Game game);

}
