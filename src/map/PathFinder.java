package map;

import core.Vector2D;
import entity.GameObject;

import java.util.*;

/**
 * @author Simon Jern
 * Implements a* search algorithm to find the best walkable path to a target.
 */
public class PathFinder {

    private PathFinder(){}

    public static List<Vector2D> findPath(List<GameObject> gameObjects, Vector2D start, Vector2D target, GameMap map) {
        final PriorityQueue<Node> open = new PriorityQueue<>();
        final Set<Node> closed = new HashSet<>();
        final Node[][] nodeMap = new Node[map.getTiles().length][map.getTiles()[0].length];
        Node current;

        for(int x = 0; x < nodeMap.length; x++) {
            for(int y = 0; y < nodeMap[0].length; y++) {
                int heuristic = Math.abs(x - target.gridX() + Math.abs(y - target.gridY()));
                Node node = new Node(map.getTile(x, y).getMoveCost(), heuristic, x, y);

                if(!map.tileIsAvailable(gameObjects, x, y)){
                    closed.add(node);
                }

                nodeMap[x][y] = node;
            }
        }

        Node startNode = nodeMap[start.gridX()][start.gridY()];
        Node targetNode = nodeMap[target.gridX()][target.gridY()];

        open.add(startNode);

        do {
            current = open.poll();
            closed.add(current);

            if(current.equals(targetNode)){
                return extractPath(current);
            }

            for(int x = current.gridX - 1; x < current.gridX + 2; x++){
                for(int y = current.gridY- 1; y < current.gridY + 2; y++){
                    if(map.isWithinBounds(x, y)){
                        Node neighbor = nodeMap[x][y];

                        if(closed.contains(neighbor)){
                            continue;
                        }

                        int calculatedCost = neighbor.heuristic + neighbor.moveCost + current.totalCost;

                        if(calculatedCost < neighbor.totalCost || !open.contains(neighbor)){
                            neighbor.totalCost = calculatedCost;
                            neighbor.parent = current;

                            if(!open.contains(neighbor)){
                                open.add(neighbor);
                            }
                        }
                    }
                }
            }
        }while(!open.isEmpty());

        return List.of(start);
    }

    private static List<Vector2D> extractPath(Node current) {
        List<Vector2D> path = new ArrayList<>();

        while(current.parent != null){
            path.add(current.getPosition());
            current = current.parent;
        }

        Collections.reverse(path);

        return path;
    }

    private static class Node implements Comparable<Node> {
        private int moveCost;
        private int heuristic;
        private int totalCost;
        private Node parent;
        private int gridX;
        private int gridY;

        public Node(int moveCost, int heuristic, int gridX, int gridY) {
            this.moveCost = moveCost;
            this.heuristic = heuristic;
            this.gridX = gridX;
            this.gridY = gridY;
        }

        @Override
        public int compareTo(Node that) {
            return Integer.compare(this.totalCost, that.totalCost);
        }

        public Vector2D getPosition(){
            return Vector2D.ofGridPosition(gridX, gridY);
        }
    }
}
