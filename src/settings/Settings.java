package settings;

/**
 * @author Simon Jern
 * Stores settings for the game.
 */
public abstract class Settings {

    private Settings() {}

    private static final int TILE_SIZE = 48;

    private static int SCREEN_WIDTH = 1100;
    private static int SCREEN_HEIGHT = 700;

    private static final int PROXIMITY_RANGE = 2;
    private static final int RENDER_MARGIN = 2 * TILE_SIZE;

    private static boolean DEBUG_MODE = false;

    private static double GAME_SPEED_MULTIPLIER = 1;

    private static final Setting<Boolean> RENDER_GRID = new Setting<>(false);

    private static final Setting<Boolean> RENDER_COLLISION_BOX = new Setting<>(false);

    private static final Setting<Boolean> RENDER_TILE_WALKABLE = new Setting<>(false);

    private static final Setting<Boolean> RENDER_TILE_PATHABLE = new Setting<>(false);

    private static final Setting<Boolean> RENDER_PATHS = new Setting<>(false);

    private static final Setting<Boolean> AUDIO_ON = new Setting<>(true);

    private static final Setting<Float> VOLUME = new Setting<>(0.8f);

    private static final Setting<Boolean> FULLSCREEN = new Setting<>(false);

    //region Getters and Setters (click to view)

    public static int getTileSize() {
        return TILE_SIZE;
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

    public static void setDebugMode(boolean mode) {
        DEBUG_MODE = mode;
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

    public static Setting<Boolean> getRenderGrid() {
        return RENDER_GRID;
    }

    public static Setting<Boolean> getRenderCollisionBox() { return RENDER_COLLISION_BOX; }

    public static Setting<Boolean> getRenderTileWalkable() { return RENDER_TILE_WALKABLE; }

    public static Setting<Boolean> isPathable() { return RENDER_TILE_PATHABLE; }

    public static Setting<Boolean> getRenderPaths() { return RENDER_PATHS; }

    public static Setting<Boolean> getAudioOn() { return AUDIO_ON; }

    public static Setting<Float> getVolume() {
        return VOLUME;
    }

    public static Setting<Boolean> getFullScreenSetting() { return FULLSCREEN; }

    public static void increaseVolume() {
        if(VOLUME.getValue() < 1f)
            VOLUME.setValue(VOLUME.getValue() + 0.05f);
    }

    public static void decreaseVolume() {
        if(VOLUME.getValue() > 0.1f)
            VOLUME.setValue(VOLUME.getValue() - 0.05f);
    }

    //endregion

    public static void reset() {
        RENDER_GRID.setValue(false);
        RENDER_COLLISION_BOX.setValue(false);
        RENDER_TILE_WALKABLE.setValue(false);
        RENDER_TILE_PATHABLE.setValue(false);
        RENDER_PATHS.setValue(false);
        DEBUG_MODE = false;
        resetGameSpeedMultiplier();
    }

    public static void setScreenSize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }

}
