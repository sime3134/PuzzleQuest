package ui.clickable;

import core.Vector2D;
import display.Camera;
import entity.GameObject;
import entity.humanoid.Humanoid;
import input.Input;
import main.Game;
import map.GameMap;
import settings.Settings;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simon Jern
 * Implements a mini map of the current game world.
 */
public class UIMinimap extends UIClickable{

    private double minimapRatio;
    private int spriteSizeOnMinimap;
    private Vector2D pixelOffset;
    private Rectangle cameraViewBounds;
    private BufferedImage mapImage;
    private Color color;

    private final Map<Image, Image> cachedScaledImages;

    public UIMinimap(GameMap gameMap) {
        width = 128;
        height = 128;
        cameraViewBounds = new Rectangle(0, 0, width, height);
        color = Color.GRAY;

        cachedScaledImages = new HashMap<>();

        calculateRatio(gameMap);
        generateMap(gameMap, List.of());
    }

    private void generateMap(GameMap gameMap, List<GameObject> gameObjects) {
        mapImage = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D g = mapImage.createGraphics();

        for(int x = 0; x < gameMap.getTiles().length; x++){
            for(int y = 0; y < gameMap.getTiles()[0].length; y++){
                g.drawImage(
                        getScaledSprite(gameMap.getTiles()[x][y].getSprite()),
                        x * spriteSizeOnMinimap + (getWidth() - gameMap.getTiles().length * spriteSizeOnMinimap) / 2,
                        y * spriteSizeOnMinimap + (getHeight() - gameMap.getTiles()[0].length * spriteSizeOnMinimap) / 2,
                        null
                );
            }
        }

        gameObjects.stream().filter(gameObject -> !(gameObject instanceof Humanoid)).forEach(gameObject -> {
            Vector2D positionWithOffset = gameObject.getPosition().getCopy();
            positionWithOffset.subtract(gameObject.getRenderOffset());
            positionWithOffset.add(pixelOffset);

            g.drawImage(
                    getScaledSprite(gameObject.getSprite()),
                    (int) Math.round(positionWithOffset.getX() / Settings.getTileSize() * spriteSizeOnMinimap + pixelOffset.getX()),
                    (int) Math.round(positionWithOffset.getY() / Settings.getTileSize() * spriteSizeOnMinimap + pixelOffset.getY()),
                    null);
        });

        g.dispose();
    }

    private Image getScaledSprite(Image sprite){
        if(cachedScaledImages.containsKey(sprite)){
            return cachedScaledImages.get(sprite);
        }

        int scaledWidth = (int)Math.round(sprite.getWidth(null) * minimapRatio);
        int scaledHeight = (int)Math.round(sprite.getHeight(null) * minimapRatio);

        Image scaledSprite = sprite.getScaledInstance(scaledWidth, scaledHeight, 0);
        cachedScaledImages.put(sprite, scaledSprite);

        return scaledSprite;
    }

    private void calculateRatio(GameMap gameMap) {
        minimapRatio = Math.min(
                width / (double) gameMap.getWidth(),
                height / (double) gameMap.getHeight()
        );

        spriteSizeOnMinimap = (int) Math.round(Settings.getTileSize() * minimapRatio);

        pixelOffset = new Vector2D(
                (getWidth() - gameMap.getTiles().length * spriteSizeOnMinimap) / 2f,
                (getHeight() - gameMap.getTiles()[0].length * spriteSizeOnMinimap) / 2f
        );
    }

    @Override
    public Image getSprite() {
        BufferedImage sprite = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_OPAQUE);
        Graphics2D g = sprite.createGraphics();

        g.drawImage(mapImage, 0,0, null);
        g.setColor(color);
        g.drawRect(0,0, getWidth() - 1, getHeight() - 1);

        g.draw(cameraViewBounds);

        g.dispose();

        return sprite;
    }

    @Override
    public void onClick(Game game) {}

    @Override
    public void onDrag(Game game) {
        Vector2D mousePosition = Input.getInstance().getMousePosition().getCopy();
        mousePosition.subtract(absolutePosition);
        mousePosition.subtract(pixelOffset);

        game.getCurrentState().getCamera().setPosition(
                new Vector2D(
                        mousePosition.getX() / minimapRatio - cameraViewBounds.getWidth() / minimapRatio / 2,
                        mousePosition.getY() / minimapRatio - cameraViewBounds.getHeight() / minimapRatio / 2
                )
        );

        game.getCurrentState().getCamera().removeFocus();
    }

    @Override
    public void onRelease(Game game) {
        game.getCurrentState().getCamera().resetLastFocus();
    }

    @Override
    public void update(Game game) {
        super.update(game);

        if(game.getTime().secondsDividableBy(0.25)){
            calculateRatio(game.getCurrentState().getCurrentMap());
            generateMap(game.getCurrentState().getCurrentMap(), game.getCurrentState().getGameObjects());
        }
        Camera camera = game.getCurrentState().getCamera();
        cameraViewBounds = new Rectangle(
                (int) (camera.getPosition().getX() * minimapRatio + pixelOffset.getX()),
                (int) (camera.getPosition().getY() * minimapRatio + pixelOffset.getY()),
                (int) (Settings.getScreenWidth() * minimapRatio),
                (int) (Settings.getScreenHeight() * minimapRatio)
        );

        color = Color.LIGHT_GRAY;

        if(hasFocus){
            color = Color.DARK_GRAY;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(
                getSprite(),
                absolutePosition.intX(),
                absolutePosition.intY(),
                null
        );
    }
}
