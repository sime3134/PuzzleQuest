package input.mouse;

import input.Input;
import input.mouse.action.MouseAction;
import main.state.State;

import java.awt.*;

/**
 * @author Simon Jern
 * Delegates what components and classes can use mouse input at the moment.
 */
public class MouseHandler {

    private MouseAction primaryButtonAction;
    private MouseConsumer currentConsumer;

    public void update(State state){
        final Input input = Input.getInstance();

        handlePrimaryButton(state);
        handleCurrentConsumer(state, input);

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

    private void handleCurrentConsumer(State state, Input input) {
        if(currentConsumer != null){
            if(input.isMouseClicked()) {
                currentConsumer.onClick(state);
            } else if(input.isMousePressed()){
                currentConsumer.onDrag(state);
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
