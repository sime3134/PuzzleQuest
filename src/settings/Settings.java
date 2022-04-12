package settings;

/**
 * @author Simon Jern
 * Stores settings for the game.
 */
public abstract class Settings {

    private Settings(){}

    private static final int SPRITE_SIZE = 48;

    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;

    private static final int PROXIMITY_RANGE = 5;
    private static final int RENDER_MARGIN = 2 * SPRITE_SIZE;

    private static boolean DEBUG_MODE = false;

    private static double GAME_SPEED_MULTIPLIER = 1;

    private static final Setting<Boolean> SHOULD_RENDER_GRID = new Setting<>(false);

    private static final Setting<Boolean> SHOULD_RENDER_COLLISION_BOX = new Setting<>(false);

    private static final Setting<Boolean> SHOULD_RENDER_TILE_WALKABILITY = new Setting<>(false);

    //region Getters and Setters (click to view)

    public static int getSpriteSize() {
        return SPRITE_SIZE;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getProximityRange() {
        return PROXIMITY_RANGE;
    }

    public static int getRenderMargin() {
        return RENDER_MARGIN;
    }

    public static boolean isDebugMode() {
        return DEBUG_MODE;
    }

    public static void toggleDebugMode() {
        DEBUG_MODE = !DEBUG_MODE;
    }

    public static double getGameSpeedMultiplier() {
        return GAME_SPEED_MULTIPLIER;
    }

    public static void increaseGameSpeedMultiplier() {
        if(GAME_SPEED_MULTIPLIER < 5)
            GAME_SPEED_MULTIPLIER += 0.1;
    }

    public static void decreaseGameSpeedMultiplier() {
        if(GAME_SPEED_MULTIPLIER > 0.5)
            GAME_SPEED_MULTIPLIER -= 0.1;
    }

    public static void resetGameSpeedMultiplier() {
        GAME_SPEED_MULTIPLIER = 1;
    }

    public static Setting<Boolean> getShouldRenderGrid() {
        return SHOULD_RENDER_GRID;
    }

    public static Setting<Boolean> getShouldRenderCollisionBox() { return SHOULD_RENDER_COLLISION_BOX; }

    public static Setting<Boolean> getShouldRenderTileWalkability() { return SHOULD_RENDER_TILE_WALKABILITY; }

    public static void reset() {
        SHOULD_RENDER_GRID.setValue(false);
        SHOULD_RENDER_COLLISION_BOX.setValue(false);
        SHOULD_RENDER_TILE_WALKABILITY.setValue(false);

    }

    //endregion
}