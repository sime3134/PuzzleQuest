package entity;

import IO.Persistable;
import content.ContentManager;
import core.Action;
import core.CollisionBox;
import core.Vector2D;
import display.Camera;
import main.Game;

import java.awt.*;

/**
 * @author Simon Jern, Johan Salomonsson
 * Implements world objects that are not tiles and will be displayed on top of the tiles.
 */
public class Scenery extends GameObject implements Persistable {

    protected Image sprite;
    protected String name;
    protected int nextQuest = 1;
    protected Action actionWhenInteractedWith;

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scenery() {
    }

    public Scenery(String name,
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

    public Scenery(String name,
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

    @Override
    protected void executePlayerAction(Game game) {
        if(actionWhenInteractedWith != null){
            actionWhenInteractedWith.execute(game);
        }
        if(sprite.equals(game.getContent().getImage("chest"))) {
            sprite = game.getContent().getImage("chest_open");
            if(id == 19424) {
                game.getGameState().updateMedallionCount();
                game.setMazeMedallionFound(true);
            }
        }
        if(sprite.equals(game.getContent().getImage("chest2"))) {
            sprite = game.getContent().getImage("chest2_open");
            if(id == 10061) {
                game.getGameState().updateMedallionCount();
            }
        }
        if(sprite.equals(game.getContent().getImage("chest3"))) {
            sprite = game.getContent().getImage("chest3_open");
            if(id == 12840) {
                game.getGameState().updateMedallionCount();
            }
            if(id == 10656) {
                game.getGameState().updateMedallionCount();
            }
        }

        if(sprite.equals(game.getContent().getImage("statue2")) && game.getCurrentMap().getName().equals("maze")
        && game.isMazeMedallionFound()) {
            game.setShouldChangeToMap("map21");
            game.setShouldChangeToPosition(new Vector2D(2000,2100));
        }

    }

    public Scenery getCopy(){
        Scenery copy = new Scenery();

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
        nextId++;

        return copy;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setActionWhenInteractedWith(Action actionWhenInteractedWith){
        this.actionWhenInteractedWith = actionWhenInteractedWith;
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

        nextId = Math.max(nextId, id + 1);
    }
}
