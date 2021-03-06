package input.mouse.action;

import core.Vector2D;
import entity.Scenery;
import input.Input;
import main.Game;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Tool to place scenery on a game map.
 */
public class SceneryPlacer extends MouseAction{

    private final Scenery scenery;
    private final UIImage preview;

    public SceneryPlacer(Scenery scenery) {
        this.scenery = scenery;
        preview = new UIImage(scenery.getSprite());
    }

    @Override
    public void onClick(Game game) {
        game.addScenery(scenery.getCopy());
    }

    @Override
    public void onDrag(Game game) {

    }

    @Override
    public void onRelease(Game game) {

    }

    @Override
    public void update(Game game) {
        Vector2D position = Input.getInstance().getMousePosition().getCopy();
        position.add(game.getCamera().getPosition());

        position.subtract(new Vector2D(scenery.getWidth() / 2f, scenery.getHeight() / 2f));
        scenery.setPosition(position);
        preview.setAbsolutePosition(scenery.getRenderPosition(game.getCamera()));
    }

    @Override
    public void draw(Game game, Graphics g) {
        preview.draw(game, g);
    }

    @Override
    public UIImage getSprite() {
        return preview;
    }

    @Override
    public void cleanUp() {

    }
}
