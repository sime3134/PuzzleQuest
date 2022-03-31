package input;

import core.Vector2D;

import java.awt.event.*;

/**
 * Handles keyboard input.
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private static Input input;

    private final boolean[] currentlyPressed;
    private final boolean[] pressed;

    private Vector2D mousePosition;
    private boolean mouseClicked;
    private boolean mousePressed;

    public Vector2D getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

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

    //KEYBOARD

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

    public void clearMouseClick(){
        mouseClicked = false;
    }


    //MOUSE

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseClicked = true;
        mousePressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Vector2D(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Vector2D(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
