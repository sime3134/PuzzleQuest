package main;

/**
 * @author Simon Jern
 * The launcher for the game.
 */
public class Launcher {
    public static void main(String[] args) {
        Game game = new Game();
        GameLoop gameLoop = new GameLoop(game);
        gameLoop.start();
    }
}
