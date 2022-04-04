package main;

import display.GameFrame;

/**
 * @author Simon Jern
 * The launcher for the game.
 */
public class Launcher {
    public static void main(String[] args) {
        Game game = new Game();
        GameFrame gameFrame = new GameFrame(game);
        GameLoop gameLoop = new GameLoop(game, gameFrame);
        gameLoop.start();
    }
}
