package input.mouse;

import input.Input;
import input.mouse.action.MouseAction;
import main.Game;
import main.state.State;

import java.awt.*;

/**
 * @author Simon Jern
 * Delegates what components and classes can use mouse input at the moment.
 */
public class MouseHandler {

    private MouseAction primaryButtonAction;
    private MouseConsumer currentConsumer;

    public void update(Game game){
        final Input input = Input.getInstance();

        handlePrimaryButton(game.getState());
        handleCurrentConsumer(game, input);

        cleanUp(input);
    }

    private void handlePrimaryButton(State state) {
        if(primaryButtonAction != null){
            setCurrentConsumer(primaryButtonAction);
            primaryButtonAction.update(state);
        }
    }

    private void cleanUp(Input input) {
        if(!input.isMousePressed()) {
            currentConsumer = null;
        }

        input.clearMouseClick();
    }

    private void handleCurrentConsumer(Game game, Input input) {
        if(currentConsumer != null){
            if(input.isMouseClicked()) {
                currentConsumer.onClick(game);
            } else if(input.isMousePressed()){
                currentConsumer.onDrag(game);
            }
        }
    }

    public void setCurrentConsumer(MouseConsumer mouseConsumer) {
        if(currentConsumer == null) {
            this.currentConsumer = mouseConsumer;
        }
    }

    public void setPrimaryButtonAction(MouseAction primaryButtonAction) {
        this.primaryButtonAction = primaryButtonAction;
    }

    public void draw(Graphics g) {
        if(primaryButtonAction != null) {
            primaryButtonAction.draw(g);
        }
    }

    public MouseAction getPrimaryButtonAction() {
        return primaryButtonAction;
    }
}
