package input.mouse;

import input.Input;
import input.mouse.action.MouseAction;
import main.Game;

import java.awt.*;

/**
 * @author Simon Jern
 * Delegates what components and classes can use mouse input at the moment.
 */
public class MouseHandler {

    private MouseAction lastLeftButtonAction;
    private MouseAction leftButtonAction;
    private MouseAction rightButtonAction;
    private MouseAction wheelButtonAction;
    private MouseConsumer currentConsumer;

    public void update(Game game){
        final Input input = Input.getInstance();

        handleLeftButton(game);
        handleRightButton(game);
        handleWheelButton(game);
        
        handleCurrentConsumer(game, input);

        cleanUp(input);
    }

    private void handleWheelButton(Game game) {
        if(wheelButtonAction != null){
            wheelButtonAction.update(game);
            Input input = Input.getInstance();

            if(input.isWheelMouseClicked()){
                wheelButtonAction.onClick(game);
            }

            if(input.isWheelMousePressed()){
                wheelButtonAction.onDrag(game);
            }

            if(input.isWheelMouseReleased()){
                wheelButtonAction.onRelease(game);
            }
        }
    }

    private void handleRightButton(Game game) {
        if(rightButtonAction != null){
            rightButtonAction.update(game);
            Input input = Input.getInstance();

            if(input.isRightMouseClicked()){
                rightButtonAction.onClick(game);
            }

            if(input.isRightMousePressed()){
                rightButtonAction.onDrag(game);
            }

            if(input.isRightMouseReleased()){
                rightButtonAction.onRelease(game);
            }
        }
        
    }

    private void handleLeftButton(Game game) {
        if(leftButtonAction != null){
            setCurrentConsumer(leftButtonAction);
            leftButtonAction.update(game);
        }
    }

    private void cleanUp(Input input) {
        if(!input.isLeftMousePressed()) {
            currentConsumer = null;
        }

        input.cleanUp();
    }

    private void handleCurrentConsumer(Game game, Input input) {
        if(currentConsumer != null){
            if(input.isLeftMouseClicked()) {
                currentConsumer.onClick(game);
            }

            if(input.isLeftMousePressed()){
                currentConsumer.onDrag(game);
            }

            if(input.isLeftMouseReleased()){
                currentConsumer.onRelease(game);
            }
        }
    }

    public void setCurrentConsumer(MouseConsumer mouseConsumer) {
        if(currentConsumer == null) {
            this.currentConsumer = mouseConsumer;
        }
    }

    public void switchLeftButtonAction(MouseAction newMouseAction) {
        if(leftButtonAction != null){
            leftButtonAction.cleanUp();
        }

        lastLeftButtonAction = this.leftButtonAction;
        this.leftButtonAction = newMouseAction;
    }


    public MouseAction getLeftButtonAction() {
        return leftButtonAction;
    }

    public void setRightButtonAction(MouseAction rightButtonAction) {
        this.rightButtonAction = rightButtonAction;
    }

    public void setWheelButtonAction(MouseAction wheelButtonAction) {
        this.wheelButtonAction = wheelButtonAction;
    }

    public MouseAction getLastLeftButtonAction() {
        return lastLeftButtonAction;
    }

    public void draw(Graphics g) {
        if(leftButtonAction != null) {
            leftButtonAction.draw(g);
        }
    }
}
