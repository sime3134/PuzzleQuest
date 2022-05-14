package ai;

import core.Vector2D;
import entity.NPC;
import main.Game;
import map.PathFinder;

import java.util.List;

/**
 * @author Simon Jern
 * Ïmplements a thread to find a path with path finding. Read more about pathfinding in the
 * PathFinder class.
 */
public class PathFindingThread extends Thread{
    private final Game game;
    private final NPC currentNPC;
    private final List<Vector2D> path;
    private Vector2D target;

    private boolean couldFindPath;

    public boolean getCouldFindPath() {
        return couldFindPath;
    }

    public PathFindingThread(Game game, NPC currentNPC, List<Vector2D> path, Vector2D target){
        this.game = game;
        this.currentNPC = currentNPC;
        this.path = path;
        this.target = target;
        couldFindPath = true;
    }

    @Override
    public void run() {
        List<Vector2D> foundPath = PathFinder.findPath(currentNPC.getCollisionBoxGridPosition(),
                target,
                game.getCurrentMap());
        if(!foundPath.isEmpty()){
            target = foundPath.get(foundPath.size() - 1);
            path.addAll(foundPath);
        }else{
            couldFindPath = false;
        }
    }
}
