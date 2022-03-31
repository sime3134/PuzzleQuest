package settings;

/**
 * Stores settings for the game.
 */
public class GameSettings {

    private static GameSettings settings;

    private final int spriteSize;

    private final int screenWidth;
    private final int screenHeight;

    private final int proximityRange;
    private final int renderMargin;

    private boolean debugMode;

    private double gameSpeedMultiplier;

    private GameSettings(){
        spriteSize = 48;
        screenWidth = 768;
        screenHeight = 576;
        proximityRange = 5;
        renderMargin = 2 * spriteSize;
        debugMode = false;
        gameSpeedMultiplier = 1;
    }

    public static GameSettings getInstance(){
        if(settings == null){
            settings = new GameSettings();
        }
        return settings;
    }

    public int getSpriteSize() {
        return spriteSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getProximityRange() {
        return proximityRange;
    }

    public int getRenderMargin() {
        return renderMargin;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void toggleDebugMode() {
        debugMode = !debugMode;
    }

    public double getGameSpeedMultiplier() {
        return gameSpeedMultiplier;
    }

    public void increaseGameSpeedMultiplier() {
        if(gameSpeedMultiplier < 5)
            gameSpeedMultiplier += 0.1;
    }

    public void decreaseGameSpeedMultiplier() {
        if(gameSpeedMultiplier > 0.5)
            gameSpeedMultiplier -= 0.1;
    }

    public void resetGameSpeedMultiplier() {
        gameSpeedMultiplier = 1;
    }

}
