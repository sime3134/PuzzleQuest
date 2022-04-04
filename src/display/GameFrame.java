package display;

import input.Input;
import main.Game;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
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
        setTitle("PuzzleQuest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(Settings.getScreenWidth(), Settings.getScreenHeight()));
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

    public void draw(){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        game.draw(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }
}
