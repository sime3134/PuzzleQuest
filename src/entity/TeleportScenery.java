package entity;

import content.ContentManager;
import core.Value;
import core.Vector2D;

/**
 * @author Simon Jern
 * Implements a Scenery that stores information about a location that an entity should be moved to.
 */
public class TeleportScenery extends Scenery{
    private String mapToTeleportTo;
    private Vector2D positionToTeleportTo;
    private Value<Boolean> active;

    public String getMapToTeleportTo() {
        return mapToTeleportTo;
    }

    public void setMapToTeleportTo(String mapToTeleportTo) {
        this.mapToTeleportTo = mapToTeleportTo;
    }

    public Vector2D getPositionToTeleportTo() {
        return positionToTeleportTo;
    }

    public void setPositionToTeleportTo(Vector2D positionToTeleportTo) {
        this.positionToTeleportTo = positionToTeleportTo;
    }

    public Value<Boolean> getActive() {
        return active;
    }

    public void setActive(Value<Boolean> active) {
        this.active = active;
    }

    public TeleportScenery(){
        super();
        mapToTeleportTo = "";
        positionToTeleportTo = new Vector2D(0,0);
    }

    public TeleportScenery(String name, boolean walkable, ContentManager content) {
        super(name, walkable, content);
        mapToTeleportTo = "";
        positionToTeleportTo = new Vector2D(0,0);
        active = new Value<>(true);
    }

    @Override
    public TeleportScenery getCopy() {
        TeleportScenery copy = new TeleportScenery();

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
        copy.id = nextId;
        copy.mapToTeleportTo = mapToTeleportTo;
        copy.positionToTeleportTo = positionToTeleportTo;
        copy.active = active;
        nextId++;

        return copy;
    }

    @Override
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append(DELIMITER);
        sb.append(name);
        sb.append(DELIMITER);
        sb.append(id);
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
        sb.append(mapToTeleportTo);
        sb.append(DELIMITER);
        sb.append(positionToTeleportTo.serialize());
        sb.append(DELIMITER);
        sb.append(active.get());
        sb.append(DELIMITER);

        return sb.toString();
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split(DELIMITER);
        name = tokens[1];
        id = Long.parseLong(tokens[2]);
        position.applySerializedData(tokens[3]);
        width = Integer.parseInt(tokens[4]);
        height = Integer.parseInt(tokens[5]);
        renderOffset.applySerializedData(tokens[6]);
        renderOrderComparisonYOffset = Double.parseDouble(tokens[7]);
        collisionBoxOffset.applySerializedData(tokens[8]);
        collisionBoxWidth = Integer.parseInt(tokens[9]);
        collisionBoxHeight = Integer.parseInt(tokens[10]);
        walkable = Boolean.parseBoolean(tokens[11]);
        selectionCircleWidth = Integer.parseInt(tokens[12]);
        selectionCircleHeight = Integer.parseInt(tokens[13]);
        selectionCircleRenderXOffset = Integer.parseInt(tokens[14]);
        selectionCircleRenderYOffset = Integer.parseInt(tokens[15]);
        mapToTeleportTo = tokens[16];
        positionToTeleportTo.applySerializedData(tokens[17]);
        active = new Value<>(Boolean.valueOf(tokens[18]));

        nextId = Math.max(nextId, id + 1);
    }
}
