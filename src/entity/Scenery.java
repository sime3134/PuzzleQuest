package entity;

import IO.Persistable;
import content.ContentManager;
import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import main.state.State;

import java.awt.*;

public class Scenery extends GameObject implements Persistable {

    private Image sprite;
    private String name;
    private boolean walkable;

    public Scenery() {}

    public Scenery(String name,
                   int selectionCircleWidth, int selectionCircleHeight,
                   int selectionCircleRenderXOffset, int selectionCircleRenderYOffset,
                   int collisionBoxWidth, int collisionBoxHeight,
                   double renderOrderComparisonOffset,
                   Vector2D collisionBoxOffset,
                   boolean walkable,
                   ContentManager content) {

        this.name = name;
        this.collisionBoxWidth = collisionBoxWidth;
        this.collisionBoxHeight = collisionBoxHeight;
        this.renderOrderComparisonYOffset = renderOrderComparisonOffset;
        this.collisionBoxOffset = collisionBoxOffset;
        this.walkable = walkable;
        this.selectionCircleWidth = selectionCircleWidth;
        this.selectionCircleHeight = selectionCircleHeight;
        this.selectionCircleRenderXOffset = selectionCircleRenderXOffset;
        this.selectionCircleRenderYOffset = selectionCircleRenderYOffset;
        loadGraphics(content);

    }

    public void loadGraphics(ContentManager content) {
        sprite = content.getImage(name);
        this.width = sprite.getWidth(null);
        this.height = sprite.getHeight(null);
    }

    @Override
    public void update(State state) {

    }

    @Override
    public void draw(Graphics g, Camera camera) {
        g.drawImage(sprite,
                getRenderPosition(camera).intX(),
                getRenderPosition(camera).intY(),
                null);
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

    public CollisionBox getExtendedCollisionBox() {
        Vector2D position = getPosition().getCopy();

        return CollisionBox.of(
                new Vector2D(position.getX() + collisionBoxOffset.getX() - 5,
                        position.getY() + collisionBoxOffset.getY() - 5),
                collisionBoxWidth + 5,
                collisionBoxHeight + 5
        );
    }

    public Scenery getCopy(){
        Scenery copy = new Scenery();

        copy.name = name;
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

    public boolean isWalkable() {
        return walkable;
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
}
