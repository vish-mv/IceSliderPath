/*Author : Visal Vitharana
  UoW ID : w1954048
  IIT ID : 20221302*/

import java.util.*;

public class ShortestPath {
    private static final int[][] DIRS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public List<int[]> findShortestPath(char[][] map, int startX, int startY, int finishX, int finishY) {
        int rows = map.length;
        int cols = map[0].length;

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        boolean[][] closedSet = new boolean[rows][cols];
        Node[][] cameFrom = new Node[rows][cols];

        Node startNode = new Node(startX, startY);
        startNode.g = 0;
        startNode.h = heuristic(startX, startY, finishX, finishY);
        startNode.f = startNode.g + startNode.h;
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            closedSet[current.x][current.y] = true;

            for (int[] dir : DIRS) {
                int newX = current.x;
                int newY = current.y;

                while (isValid(newX + dir[0], newY + dir[1], map)) {
                    newX += dir[0];
                    newY += dir[1];
                    if (map[newX][newY] == 'F') {
                        List<int[]> path = reconstructPath(cameFrom, current, startX, startY);
                        path.add(new int[]{finishX, finishY}); // Add the endpoint to the path
                        return path;
                    }
                }

                if (closedSet[newX][newY]) {
                    continue;
                }

                Node neighbor = cameFrom[newX][newY];
                int tentativeGScore = current.g + 1;

                if (neighbor == null || tentativeGScore < neighbor.g) {
                    neighbor = new Node(newX, newY);
                    neighbor.g = tentativeGScore;
                    neighbor.h = heuristic(newX, newY, finishX, finishY);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    openSet.add(neighbor);
                    cameFrom[newX][newY] = current;
                }
            }
        }

        return new ArrayList<>();
    }


    private boolean isValid(int x, int y, char[][] map) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != '0';
    }

    private int heuristic(int x, int y, int fx, int fy) {
        return Math.abs(x - fx) + Math.abs(y - fy);
    }

    private List<int[]> reconstructPath(Node[][] cameFrom, Node current, int startX, int startY) {
        List<int[]> path = new ArrayList<>();
        while (current.parent != null) {
            path.add(new int[]{current.x, current.y});
            current = cameFrom[current.x][current.y];
        }
        // Add the starting point
        path.add(new int[]{startX, startY});
        // Reverse the path
        Collections.reverse(path);
        return path;
    }

    private static class Node implements Comparable<Node> {
        int x, y;
        int f, g, h;
        Node parent;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }
}
