package settings;

import core.Value;

/**
 * @author Simon Jern
 * Stores settings for the game.
 */
public abstract class Settings {

    private Settings() {}

    private static final int TILE_SIZE = 48;

    private static int SCREEN_WIDTH = 1100;
    private static int SCREEN_HEIGHT = 700;

    private static final int PROXIMITY_RANGE = 5;

    private static final int PROXIMITY_RANGE_QUEST = 48 * 2;
    private static final int RENDER_MARGIN = 2 * TILE_SIZE;

    private static boolean DEBUG_MODE = false;

    private static double GAME_SPEED_MULTIPLIER = 1;

    private static final Value<Boolean> RENDER_GRID = new Value<>(false);

    private static final Value<Boolean> RENDER_COLLISION_BOX = new Value<>(false);

    private static final Value<Boolean> RENDER_TILE_WALKABLE = new Value<>(false);

    private static final Value<Boolean> RENDER_TILE_PATHABLE = new Value<>(false);

    private static final Value<Boolean> RENDER_PATHS = new Value<>(false);

    private static final Value<Boolean> AUDIO_ON = new Value<>(true);

    private static final Value<Float> VOLUME = new Value<>(0.8f);

    private static final Value<Boolean> FULLSCREEN = new Value<>(false);

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

    public static double getProximityRangeQuest() { return PROXIMITY_RANGE_QUEST; }

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

    public static Value<Boolean> getRenderGrid() {
        return RENDER_GRID;
    }

    public static Value<Boolean> getRenderCollisionBox() { return RENDER_COLLISION_BOX; }

    public static Value<Boolean> getRenderTileWalkable() { return RENDER_TILE_WALKABLE; }

    public static Value<Boolean> isPathable() { return RENDER_TILE_PATHABLE; }

    public static Value<Boolean> getRenderPaths() { return RENDER_PATHS; }

    public static Value<Boolean> getAudioOn() { return AUDIO_ON; }

    public static Value<Float> getVolume() {
        return VOLUME;
    }

    public static Value<Boolean> getFullScreenSetting() { return FULLSCREEN; }

    public static void increaseVolume() {
        if(VOLUME.get() < 1f)
            VOLUME.set(VOLUME.get() + 0.05f);
    }

    public static void decreaseVolume() {
        if(VOLUME.get() > 0.1f)
            VOLUME.set(VOLUME.get() - 0.05f);
    }

    //endregion

    public static void reset() {
        RENDER_GRID.set(false);
        RENDER_COLLISION_BOX.set(false);
        RENDER_TILE_WALKABLE.set(false);
        RENDER_TILE_PATHABLE.set(false);
        RENDER_PATHS.set(false);
        DEBUG_MODE = false;
        resetGameSpeedMultiplier();
    }

    public static void setScreenSize(int width, int height) {
        SCREEN_WIDTH = width;
        SCREEN_HEIGHT = height;
    }
}
