package ui.clickable;

import core.Vector2D;
import display.Camera;
import input.Input;
import main.Game;
import main.state.State;
import map.GameMap;
import settings.Settings;
import utilities.ImgUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public UIMinimap(GameMap gameMap) {
        width = 128;
        height = 128;
        cameraViewBounds = new Rectangle(0, 0, width, height);
        color = Color.GRAY;

        calculateRatio(gameMap);
        generateMap(gameMap);
    }

    private void generateMap(GameMap gameMap) {
        mapImage = (BufferedImage) ImgUtils.createCompatibleImage(width, height, ImgUtils.ALPHA_BIT_MASKED);
        Graphics2D g = mapImage.createGraphics();

        for(int x = 0; x < gameMap.getTiles().length; x++){
            for(int y = 0; y < gameMap.getTiles()[0].length; y++){
                g.drawImage(
                        gameMap.getTiles()[x][y].getSprite().getScaledInstance(spriteSizeOnMinimap, spriteSizeOnMinimap, 0),
                        x * spriteSizeOnMinimap + (getWidth() - gameMap.getTiles().length * spriteSizeOnMinimap) / 2,
                        y * spriteSizeOnMinimap + (getHeight() - gameMap.getTiles()[0].length * spriteSizeOnMinimap) / 2,
                        null
                );
            }
        }
    }

    private void calculateRatio(GameMap gameMap) {
        minimapRatio = Math.min(
                width / (double) gameMap.getWidth(),
                height / (double) gameMap.getHeight()
        );

        spriteSizeOnMinimap = (int) Math.round(Settings.getSpriteSize() * minimapRatio);

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

        game.getState().getCamera().setPosition(
                new Vector2D(
                        mousePosition.getX() / minimapRatio - cameraViewBounds.getWidth() / minimapRatio / 2,
                        mousePosition.getY() / minimapRatio - cameraViewBounds.getHeight() / minimapRatio / 2
                )
        );
    }

    @Override
    public void update(State state) {
        super.update(state);

        if(state.getTime().secondsDividableBy(0.25)){
            generateMap(state.getCurrentMap());
        }
        Camera camera = state.getCamera();
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
