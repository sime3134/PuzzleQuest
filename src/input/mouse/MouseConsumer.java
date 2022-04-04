package input.mouse;

import main.state.State;

public interface MouseConsumer {

    void onClick(State state);
    void onDrag(State state);

}
