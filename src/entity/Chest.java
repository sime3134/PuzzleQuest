package entity;

import content.ContentManager;
import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import main.Game;

import java.awt.*;

/**
 * @author Johan Salomonsson
 */
public class Chest extends Scenery {

    private Image sprite;
    private String name;
    private boolean chest1Open;
    private boolean chest2Open;
    private boolean chest3Open;

    public Chest() {
    }

    public Chest(String name,
                   int collisionBoxWidth, int collisionBoxHeight,
                   Vector2D collisionBoxOffset,
                   double renderOrderComparisonOffset,
                   boolean walkable,
                   ContentManager content) {

        this.name = name;
        this.collisionBoxWidth = collisionBoxWidth;
        this.collisionBoxHeight = collisionBoxHeight;
        this.renderOrderComparisonYOffset = renderOrderComparisonOffset;
        this.collisionBoxOffset = collisionBoxOffset;
        this.walkable = walkable;
        loadGraphics(content);
        calculateSelectionCircle();
    }

    public Chest(String name,
                   boolean walkable,
                   ContentManager content) {

        this.name = name;
        this.walkable = walkable;
        loadGraphics(content);
        calculateSelectionCircle();
        this.collisionBoxWidth = width;
        this.collisionBoxHeight = height - height / 3;
        this.collisionBoxOffset = new Vector2D(0, height / 3f);
        if(walkable) {
            this.renderOrderComparisonYOffset = -height;
        }else{
            this.renderOrderComparisonYOffset = height / 3f;
        }
    }

    private void calculateSelectionCircle() {
        double ratio = calculateWidthHeightRatio();
        this.selectionCircleWidth = width + 10;
        if(ratio > 1.2) {
            this.selectionCircleHeight = height / 2;
            this.selectionCircleRenderYOffset = this.selectionCircleHeight + 5;
        }else{
            this.selectionCircleHeight = height + 5;
        }
        this.selectionCircleRenderXOffset = -5;
    }

    private double calculateWidthHeightRatio() {
        return (double) height / width;
    }

    public void loadGraphics(ContentManager content) {
        sprite = content.getImage(name);
        this.width = sprite.getWidth(null);
        this.height = sprite.getHeight(null);
    }

    @Override
    public void update(Game state) {

    }

    @Override
    public void draw(Graphics g, Camera camera) {
        g.drawImage(sprite,
                getRenderPosition(camera).intX(),
                getRenderPosition(camera).intY(),
                null);
    }

    public String getName() {
        return name;
    }

    @Override
    public CollisionBox getCollisionBox() {
        Vector2D position = getPosition().getCopy();

        return CollisionBox.of(
                new Vector2D(position.getX() + collisionBoxOffset.getX(),
                        position.getY() + collisionBoxOffset.getY()),
                collisionBoxWidth,
                collisionBoxHeight
        );
    }

    @Override
    public CollisionBox getStaticCollisionBox() {
        return getCollisionBox();
    }

    public CollisionBox getExtendedCollisionBox() {
        Vector2D position = getPosition().getCopy();

        return CollisionBox.of(
                new Vector2D(position.getX() + collisionBoxOffset.getX() - 5,
                        position.getY() + collisionBoxOffset.getY() - 5),
                collisionBoxWidth + 5,
                collisionBoxHeight + 5
        );
    }

    public Chest getCopy(String mapName){
        Chest copy = new Chest();

        copy.name = this.name;
        copy.position = position.getCopy();
        copy.width = width;
        copy.height = height;
        copy.renderOffset = renderOffset.getCopy();
        copy.renderOrderComparisonYOffset = renderOrderComparisonYOffset;
        copy.collisionBoxWidth = collisionBoxWidth;
        copy.collisionBoxHeight = collisionBoxHeight;
        copy.collisionBoxOffset = collisionBoxOffset.getCopy();
        copy.selectionCircleWidth = selectionCircleWidth;
        copy.selectionCircleHeight = selectionCircleHeight;
        copy.selectionCircleRenderXOffset = selectionCircleRenderXOffset;
        copy.selectionCircleRenderYOffset = selectionCircleRenderYOffset;
        copy.walkable = walkable;
        copy.sprite = sprite;

        return copy;
    }

    public Image getSprite() {
        return sprite;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(DELIMITER);
        sb.append(name);
        sb.append(DELIMITER);
        sb.append(position.serialize());
        sb.append(DELIMITER);
        sb.append(width);
        sb.append(DELIMITER);
        sb.append(height);
        sb.append(DELIMITER);
        sb.append(renderOffset.serialize());
        sb.append(DELIMITER);
        sb.append(renderOrderComparisonYOffset);
        sb.append(DELIMITER);
        sb.append(collisionBoxOffset.serialize());
        sb.append(DELIMITER);
        sb.append(collisionBoxWidth);
        sb.append(DELIMITER);
        sb.append(collisionBoxHeight);
        sb.append(DELIMITER);
        sb.append(walkable);
        sb.append(DELIMITER);
        sb.append(selectionCircleWidth);
        sb.append(DELIMITER);
        sb.append(selectionCircleHeight);
        sb.append(DELIMITER);
        sb.append(selectionCircleRenderXOffset);
        sb.append(DELIMITER);
        sb.append(selectionCircleRenderYOffset);
        sb.append(DELIMITER);

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        name = tokens[1];
        position.applySerializedData(tokens[2]);
        width = Integer.parseInt(tokens[3]);
        height = Integer.parseInt(tokens[4]);
        renderOffset.applySerializedData(tokens[5]);
        renderOrderComparisonYOffset = Double.parseDouble(tokens[6]);
        collisionBoxOffset.applySerializedData(tokens[7]);
        collisionBoxWidth = Integer.parseInt(tokens[8]);
        collisionBoxHeight = Integer.parseInt(tokens[9]);
        walkable = Boolean.parseBoolean(tokens[10]);
        selectionCircleWidth = Integer.parseInt(tokens[11]);
        selectionCircleHeight = Integer.parseInt(tokens[12]);
        selectionCircleRenderXOffset = Integer.parseInt(tokens[13]);
        selectionCircleRenderYOffset = Integer.parseInt(tokens[14]);
    }

    public boolean isChest1Open() {
        return chest1Open;
    }

    public void setChest1Open(boolean chest1Open) {
        this.chest1Open = chest1Open;
    }

    public boolean isChest2Open() {
        return chest2Open;
    }

    public void setChest2Open(boolean chest2Open) {
        this.chest2Open = chest2Open;
    }

    public boolean isChest3Open() {
        return chest3Open;
    }

    public void setChest3Open(boolean chest3Open) {
        this.chest3Open = chest3Open;
    }
}
