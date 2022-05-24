package input.mouse.action;

import core.Vector2D;
import entity.NPC;
import input.Input;
import main.Game;
import ui.UIImage;

import java.awt.*;

/**
 * @author Simon Jern
 * Tool to place npc on a game map.
 */
public class NPCPlacer extends MouseAction{

    private final NPC npc;
    private final UIImage preview;

    public NPCPlacer(NPC npc) {
        this.npc = npc;
        preview = new UIImage(npc.getSprite());
    }

    @Override
    public void onClick(Game game) {
        game.addNPC(npc.getCopy(game.getCurrentMap().getName()));
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

        position.subtract(new Vector2D(npc.getWidth() / 2f, npc.getHeight() / 2f));
        npc.setPosition(position);
        preview.setAbsolutePosition(npc.getRenderPosition(game.getCamera()));
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
