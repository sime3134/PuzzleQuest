package main;

import display.GameFrame;
import settings.GameSettings;

/**
 * Thread running the game at 60 FPS.
 */
public class GameLoop implements Runnable {

    private final GameSettings settings = GameSettings.getInstance();
    private final Game game;
    private final GameFrame gameFrame;

    public static final int FPS_LOCK = 60;

    private static final double updateRate = 1.0d/FPS_LOCK;

    private long nextStatTime;
    private int fps;
    private int ups;


    public GameLoop(Game game, GameFrame gameFrame){
        this.game = game;
        this.gameFrame = gameFrame;
    }

    public void start(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        double accumulator = 0;
        long currentTime;
        long lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(!Thread.interrupted()) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            accumulator += lastRenderTimeInSeconds * settings.getGameSpeedMultiplier();
            lastUpdate = currentTime;

            if(accumulator >= updateRate) {
                while(accumulator >= updateRate) {
                    update();
                    accumulator -= updateRate;
                }
            }
            draw();
            printStats();
        }
    }

    private void printStats() {
        if(System.currentTimeMillis() > nextStatTime) {
            System.out.printf("FPS: %d, UPS: %d%n", fps, ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update() {
        game.update();
        ups++;
    }

    private void draw() {
        gameFrame.draw();
        fps++;
    }
}
