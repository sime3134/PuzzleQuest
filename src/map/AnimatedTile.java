package map;

import content.AnimationManager;
import content.ContentManager;
import settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedTile extends Tile{
    private AnimationManager animationManager;

    @Override
    public AnimatedTile getCopy() {
        return new AnimatedTile(tileSheet, tileIndex, tileSheetName);
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    public void setAnimationManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
        generateSprite();
    }

    public AnimatedTile(ContentManager content, String tileSheetName) {
        this.tileSheet = content.getAnimatedTileSheet(tileSheetName).get(tileSheetName);
        this.tileSheetName = tileSheetName;
        this.collisionBoxType = 0;
        animationManager = new AnimationManager(content.getAnimatedTileSheet(tileSheetName), Settings.getSpriteSize(),
                Settings.getSpriteSize(), tileSheetName);
        generateSprite();
    }

    public AnimatedTile(Image tileSheet, int tileIndex, String tileSheetName) {
        super(tileSheet, tileIndex, tileSheetName);
        animationManager = null;
    }

    public AnimatedTile(){
        super();
    }

    @Override
    protected void generateSprite() {
        sprite = ((BufferedImage)tileSheet).getSubimage(
                0,0,
                Settings.getSpriteSize(),
                Settings.getSpriteSize()
        );
    }

    @Override
    public void reloadGraphics(ContentManager content) {
        tileSheet = content.getAnimatedTileSheet(tileSheetName).get(tileSheetName);
        animationManager = new AnimationManager(content.getAnimatedTileSheet(tileSheetName), Settings.getSpriteSize(),
                Settings.getSpriteSize(), tileSheetName);
        generateSprite();
    }

    @Override
    public void update() {
        super.update();
        generateSprite();
        animationManager.update();
        animationManager.playAnimation(tileSheetName);
    }

    @Override
    public void draw(Graphics g, int posX, int posY) {
        g.drawImage(animationManager.getSprite(), posX, posY, null);
    }
}
