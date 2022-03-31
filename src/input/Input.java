package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input.
 */
public class Input implements KeyListener {

    private static Input input;
    private final boolean[] currentlyPressed;
    private final boolean[] pressed;

    public static Input getInstance() {
        if(input == null){
            input = new Input();
        }
        return input;
    }

    private Input(){
        currentlyPressed = new boolean[255];
        pressed = new boolean[255];
    }

    public boolean isPressed(int keyCode) {
        if(!pressed[keyCode] && currentlyPressed[keyCode]){
            pressed[keyCode] = true;
            return true;
        }

        return false;
    }

    public boolean isCurrentlyPressed(int keyCode) {
        return currentlyPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentlyPressed[e.getKeyCode()] = false;
        pressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //NOT NEEDED
    }
}
