package input;

import core.Vector2D;

import java.awt.event.*;

/**
 * @author Simon Jern
 * Handles input from mouse and keyboard.
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private static Input input;

    private final boolean[] currentlyPressed;
    private final boolean[] pressed;

    private Vector2D mousePosition;

    private boolean leftMouseClicked;
    private boolean leftMousePressed;
    private boolean leftMouseReleased;

    private boolean rightMouseClicked;
    private boolean rightMousePressed;
    private boolean rightMouseReleased;

    private boolean wheelMouseClicked;
    private boolean wheelMousePressed;
    private boolean wheelMouseReleased;

    public static Input getInstance() {
        if(input == null){
            input = new Input();
        }
        return input;
    }

    private Input(){
        currentlyPressed = new boolean[1000];
        pressed = new boolean[1000];
        mousePosition = new Vector2D(0, 0);
    }

    public boolean isKeyPressed(int keyCode) {
        if(!pressed[keyCode] && currentlyPressed[keyCode]) {
            pressed[keyCode] = true;
            return true;
        }

        return false;
    }

    public boolean isKeyCurrentlyPressed(int keyCode) {
        return currentlyPressed[keyCode];
    }

    public void cleanUpMouseEvents() {
        leftMouseClicked = false;
        rightMouseClicked = false;
        wheelMouseClicked = false;

        leftMouseReleased = false;
        rightMouseReleased = false;
        wheelMouseReleased = false;
    }

    public Vector2D getMousePosition() {
        return mousePosition;
    }

    public boolean isLeftMouseClicked() {
        return leftMouseClicked;
    }

    public boolean isLeftMousePressed() {
        return leftMousePressed;
    }

    public boolean isRightMouseClicked() {
        return rightMouseClicked;
    }

    public boolean isRightMousePressed() {
        return rightMousePressed;
    }

    public boolean isWheelMouseClicked() {
        return wheelMouseClicked;
    }

    public boolean isWheelMousePressed() {
        return wheelMousePressed;
    }

    public boolean isLeftMouseReleased() {
        return leftMouseReleased;
    }

    public boolean isRightMouseReleased() {
        return rightMouseReleased;
    }

    public boolean isWheelMouseReleased() {
        return wheelMouseReleased;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

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
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        leftMousePressed = e.getButton() == MouseEvent.BUTTON1;
        rightMousePressed = e.getButton() == MouseEvent.BUTTON3;
        wheelMousePressed = e.getButton() == MouseEvent.BUTTON2;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            leftMouseClicked = true;
            leftMousePressed = false;
            leftMouseReleased = true;
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            rightMouseClicked = true;
            rightMousePressed = false;
            rightMouseReleased = true;
        }
        if(e.getButton() == MouseEvent.BUTTON2) {
            wheelMouseClicked = true;
            wheelMousePressed = false;
            wheelMouseReleased = true;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = new Vector2D(e.getPoint().getX(), e.getPoint().getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new Vector2D(e.getPoint().getX(), e.getPoint().getY());
    }
}
