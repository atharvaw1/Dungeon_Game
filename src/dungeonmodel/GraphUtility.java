package dungeonmodel;

import dungeonmodel.rng.IRandomNumberGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;




/**
 * Package private helper class that has all the utility functions to create the maze like Kruskal
 * implementation and the BFS to check for path length.
 */
final class GraphUtility {


  /**
   * Function to find parent of node.
   */
  private static int find(int i, int[] parent) {
    while (parent[i] != i) {
      i = parent[i];
    }
    return i;
  }

  /**
   * Method to perform union of 2 nodes.
   */
  private static int[] union(int i, int j, int[] parent) {
    int a = find(i, parent);
    int b = find(j, parent);
    parent[a] = b;
    return parent;
  }


  /**
   * Find the edge already exists in the edgeList.
   *
   * @param i        first node
   * @param j        second node
   * @param edgeList list of edges already in edgeList
   * @return boolean for if edge exists in the edgeList
   */
  private static boolean inEdgeList(int i, int j, List<List<Integer>> edgeList) {
    for (List<Integer> edge : edgeList) {
      if ((edge.get(0) == i && edge.get(1) == j) || (edge.get(0) == j && edge.get(1) == i)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Implement kruskals algorithm for wrapping or non-wrapping dungeon based on parameter and
   * generate the list of edges that will remain in the final graph. It is package private to
   * prevent use outside the dungeon class.
   *
   * @param adjmatrix the adjacency matrix of the maze
   * @return list of the edges in final graph after kruskal
   */
  static List<List<Integer>> kruskalmst(int[][] adjmatrix, int x, int y, int interconnectivity,
                                        IRandomNumberGenerator rng) {
    List<List<Integer>> edgeList = new ArrayList<>();
    int v = x * y;
    int inf = Integer.MAX_VALUE;
    // Initialize sets of disjoint sets.
    int[] parent = new int[v];
    for (int i = 0; i < v; i++) {
      parent[i] = i;
    }

    // Include minimum weight edges one by one
    int edge_count = 0;
    while (edge_count < v - 1) {
      int min = inf;
      int a = 0;
      int b = 0;

      int i = rng.getRandomNumber(0, v - 1);
      int j = rng.getRandomNumber(0, v - 1);
      if (find(i, parent) != find(j, parent) && adjmatrix[i][j] < min) {
        min = adjmatrix[i][j];
        a = i;
        b = j;
        parent = union(a, b, parent);
        edge_count += 1;
        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(a);
        edge.add(b);
        edgeList.add(edge);
      }
    }
    //Add extra edges to match interconnectivity
    int tempInter = interconnectivity;
    while (tempInter > 0) {
      if (edgeList.size() == (x - 1) * y + x * (y - 1)) {
        throw new IllegalArgumentException("Interconnectivity is too high for graph.");
      }
      int a = 0;
      int b = 0;

      int i = rng.getRandomNumber(0, v - 1);
      int j = rng.getRandomNumber(0, v - 1);
      if (!inEdgeList(i, j, edgeList) && i != j && adjmatrix[i][j] < inf) {
        a = i;
        b = j;

        ArrayList<Integer> edge = new ArrayList<>();
        edge.add(a);
        edge.add(b);
        edgeList.add(edge);
        tempInter -= 1;
      }
    }


    return edgeList;
  }


  /**
   * Does a BFS traversal of the graph from the start node and gets the length of the
   * minimum path to reach the end node. It is package private to prevent use outside the
   * dungeon class.
   *
   * @param start the start node number
   * @param end   the end node number
   * @param grid  the 2D grid consisting of all the rooms in graph
   * @return the length of the shortest path from start node to end  node
   */
  static int getPathLength(int start, int end, List<List<Room>> grid) {

    int rows = grid.size();
    int columns = grid.get(0).size();
    // Mark all the vertices as not visited(By default
    // set as false)
    boolean[] visited = new boolean[rows * columns];
    int[] level = new int[rows * columns];
    // Create a queue for BFS
    LinkedList<Integer> queue = new LinkedList<Integer>();

    // Mark the current node as visited and enqueue it
    visited[start] = true;
    queue.add(start);
    level[start] = 0;


    while (queue.size() != 0) {
      // Dequeue a vertex from queue and print it
      int s = queue.poll();
      //System.out.print(s+" ");

      // Get all adjacent vertices of the dequeued vertex s
      // If a adjacent has not been visited, then mark it
      // visited and enqueue it
      Map<Direction, Room> connections = grid.get(s / columns).get(s % columns).getConnections();
      int neighbour;
      for (Direction d : connections.keySet()) {
        neighbour = connections.get(d).getNodeNumber();
        if (!visited[neighbour]) {
          visited[neighbour] = true;
          queue.add(neighbour);
          level[neighbour] = level[s] + 1;
        }
      }
    }

    return level[end];
  }


  /**
   * Use modified BFS to get the monsters within given distance from the source and return
   * the total count of monsters at that exact distance. Package private to prevent use outside
   * dungeon class.
   *
   * @param start    the start node number
   * @param distance the distance from start node to check
   * @param grid     the dungeon grid
   * @return count of monsters at given distance
   */
  static int getMonstersInArea(int start, int distance, List<List<Room>> grid) {

    int rows = grid.size();
    int columns = grid.get(0).size();
    // Mark all the vertices as not visited(By default
    // set as false)
    boolean[] visited = new boolean[rows * columns];
    int[] level = new int[rows * columns];
    // Create a queue for BFS
    LinkedList<Integer> queue = new LinkedList<Integer>();

    // Mark the current node as visited and enqueue it
    visited[start] = true;
    queue.add(start);
    level[start] = 0;
    int monsterCount = 0;


    while (queue.size() != 0) {
      // Dequeue a vertex from queue and print it
      int s = queue.poll();
      //System.out.print(s+" ");

      // Get all adjacent vertices of the dequeued vertex s
      // If an adjacent has not been visited, then mark it
      // visited and enqueue it
      Map<Direction, Room> connections = grid.get(s / columns).get(s % columns).getConnections();


      int neighbour;
      for (Direction d : connections.keySet()) {
        neighbour = connections.get(d).getNodeNumber();
        //Visit neighbour if distance less than given distance and not already visited
        if (!visited[neighbour]) {
          visited[neighbour] = true;
          queue.add(neighbour);
          level[neighbour] = level[s] + 1;
        }
      }
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        //If monster exists in room and is alive
        if (grid.get(i).get(j).getMonster() != null
                && Objects.equals(grid.get(i).get(j).getMonster().toString(), "Otyugh")
                && grid.get(i).get(j).getMonster().getState() != MonsterState.DEAD
                && level[i * columns + j] == distance) {
          monsterCount += 1;
        }
      }
    }

    return monsterCount;
  }

}
