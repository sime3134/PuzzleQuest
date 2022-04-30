package content;

import core.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Simon Jern
 * This class implements animations for sprites. For example every MovingEntity has an instance of this class
 * that it uses to draw the correct sprite to the screen.
 */
public class AnimationManager {

    private final SpriteSet spriteSet;
    private String currentAnimationName;
    private BufferedImage currentAnimationSheet;
    private int currentFrameTime;
    private final int updatesPerFrame;
    private int frameIndex;
    private int directionIndex;
    private final int spriteWidth;
    private final int spriteHeight;

    /**
     * @return the Image that should currently be drawn to the game. It cuts out a part of the bigger spritesheet
     * depending on where in the animation we currently are.
     */
    public Image getSprite() {
        return currentAnimationSheet.getSubimage(
                frameIndex * spriteWidth,
                directionIndex * spriteHeight,
                spriteWidth,
                spriteHeight
        );
    }

    public SpriteSet getSpriteSet() {
        return spriteSet;
    }

    public AnimationManager(SpriteSet spriteSet, int spriteWidth, int spriteHeight, String startingSheet) {
        this.spriteSet = spriteSet;
        this.updatesPerFrame = 15;
        this.frameIndex = 0;
        this.currentFrameTime = 0;
        this.directionIndex = 0;
        this.currentAnimationName = "";
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        playAnimation(startingSheet);
    }

    /**
     * Updates the animation depending on the direction that the entity is facing.
     * @param direction the direction that the entity is currently facing.
     */
    public void update(Direction direction) {
        currentFrameTime++;
        directionIndex = direction.getAnimationRow();
        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / spriteWidth) {
                frameIndex = 0;
            }
        }
    }

    public void update() {
        currentFrameTime++;
        directionIndex = 0;
        if(currentFrameTime >= updatesPerFrame) {
            currentFrameTime = 0;
            frameIndex++;

            if(frameIndex >= currentAnimationSheet.getWidth() / spriteWidth) {
                frameIndex = 0;
            }
        }
    }

    /**
     * Changes which animation we want to use. For example walking animation or standing animation.
     * @param name Can be "stand" or "walk" for different animations.
     */
    public void playAnimation(String name){
        if(!name.equals(currentAnimationName)) {
            this.currentAnimationSheet = (BufferedImage) spriteSet.get(name);
            currentAnimationName = name;
            frameIndex = 0;
        }
    }

    public AnimationManager getCopy(String startingSheet) {
        return new AnimationManager(spriteSet, spriteWidth, spriteHeight, startingSheet);
    }
}
