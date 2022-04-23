package display;

import input.Input;
import main.Game;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

/**
 * @author Simon Jern
 * The window frame that the game is displayed in.
 */
public class GameFrame extends JFrame {

    private final Game game;
    private final Canvas canvas;

    public GameFrame(Game game) {
        this.game = game;
        setTitle("PuzzleQuest 2.0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addListener();

        canvas = new Canvas();
        setPreferredSize(new Dimension(Settings.getScreenWidth(), Settings.getScreenHeight()));
        canvas.setPreferredSize(getContentPane().getPreferredSize());
        canvas.setFocusable(false);
        canvas.addMouseListener(Input.getInstance());
        canvas.addMouseMotionListener(Input.getInstance());
        add(canvas);
        addKeyListener(Input.getInstance());
        pack();

        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPreferredSize(e.getComponent().getSize());
                pack();
                Settings.setScreenSize(getContentPane().getWidth(), getContentPane().getHeight());
                canvas.setPreferredSize(getContentPane().getPreferredSize());
            }
        });
    }

    public void draw(){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        game.draw(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public void toggleFullScreen() {
        if(Settings.getFullScreenSetting().getValue()){
            setSize(Toolkit.getDefaultToolkit().getScreenSize());
            dispose();
            setUndecorated(true);
        }else{
            setSize(new Dimension(1100, 700));
            dispose();
            setUndecorated(false);
        }
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        Settings.setScreenSize(getContentPane().getWidth(), getContentPane().getHeight());
        canvas.setPreferredSize(getContentPane().getPreferredSize());
    }
}
