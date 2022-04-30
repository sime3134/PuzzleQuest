package utilities;

import content.ContentManager;
import main.Game;
import settings.Settings;
import ui.*;
import content.ContentManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashScreen {
    private ContentManager content;
    private JFrame jFrame;
    //private ProgressBar progressBar = new ProgressBar();
    private JProgressBar progressBar = new JProgressBar();
    private JLabel message = new JLabel();

    public SplashScreen() {
        content = new ContentManager();
        setUp();
        //addText();
        addProgressBar();
        //progressMessage();
        runningProgressBar();
    }

    public void setUp() {
        jFrame = new JFrame();
        jFrame.setUndecorated(true); //Turns off title bar
        jFrame.setSize(Settings.getScreenWidth(),Settings.getScreenHeight());
        jFrame.setLocationRelativeTo(null);
        try {
            BufferedImage image = ImageIO.read(new File("images/test.png"));
            jFrame.setContentPane(new BackgroundImage(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jFrame.setVisible(true);
    }

    public void addText() {
        JLabel text = new JLabel("Puzzle Quest 2.0");
        text.setFont(new Font("Joystix Monospace", Font.BOLD, 28));
        text.setForeground(Color.WHITE);
        text.setBounds(300,100,600,40);
        jFrame.add(text);

    }

    public void addProgressBar() {
        progressBar.setBounds(250,300, 600, 40);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.white);
        progressBar.setForeground(Color.black);
        progressBar.setValue(0);
        jFrame.add(progressBar);
    }

    public void progressMessage() {
        message.setBounds(300, 200, 600, 40);
        message.setForeground(Color.white);
        message.setFont(new Font("Joystix Monospace", Font.BOLD, 28));
        jFrame.add(message);
    }

    public void runningProgressBar() {
        int i  = 0;

        while (i <= 100) {
            try {
                Thread.sleep(50);
                progressBar.setValue(i);
                message.setText("Loading " + Integer.toString(i) + "%");
                i++;
                if(i == 100) {
                    jFrame.dispose();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SplashScreen test = new SplashScreen();
    }


    private class BackgroundImage extends JComponent {
        private Image image;

        public BackgroundImage(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image,0,0,this);
        }
    }
}
