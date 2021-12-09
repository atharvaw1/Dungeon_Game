package dungeonmodel;

import dungeonmodel.rng.IRandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * The main dungeon class that handles all the dungeon related activities. Implements the Dungeon
 * interface.
 */
public class DungeonImpl implements Dungeon {

  private final List<List<Room>> grid;
  private final int x;
  private final int y;
  private final int interconnectivity;
  private final int start;
  private final int end;
  private final boolean wrapping;
  private final IRandomNumberGenerator rng;
  private final int treasurePercent;
  private final int monsterCount;
  private Player player;
  private int playerLocation;
  private List<Boolean> visited;


  /**
   * Constructor for the Dungeon class. Creates a dungeon of given requirements, sets the start and
   * end node and adds treasure to the caves.
   *
   * @param interconnectivity interconnectivity of dungeon
   * @param wrapping          boolean for whether dungeon is wrapping or not
   * @param x                 the number of rows in the dungeon
   * @param y                 the number of columns in the dungeon
   * @param rng               the random number generator
   * @param treasurePercent   the percentage of caves with treasure
   */
  public DungeonImpl(int interconnectivity, boolean wrapping, int x, int y,
                     IRandomNumberGenerator rng, int treasurePercent, int monsterCount,
                     String playerId) {

    //Input validation
    if (rng == null) {
      throw new IllegalArgumentException("Random number generator cannot be null.");
    }

    if (treasurePercent < 0 || treasurePercent > 100) {
      throw new IllegalArgumentException("Treasure has to be a percentage value "
              + "between 0 and 100.");
    }

    if (x < 5 || y < 5) {
      throw new IllegalArgumentException("Dungeon size has to be greater than 5.");
    }

    //Assignment
    this.interconnectivity = interconnectivity;
    this.wrapping = wrapping;
    this.x = x;
    this.y = y;
    this.rng = rng;
    this.treasurePercent = treasurePercent;
    this.monsterCount = monsterCount;


    //Get edges from Kruskal
    List<List<Integer>> edgeList = generateDungeon();

    //Create grid of rooms
    this.grid = new ArrayList<List<Room>>();
    for (int i = 0; i < x; i++) {
      this.grid.add(new ArrayList<Room>());
      for (int j = 0; j < y; j++) {
        this.grid.get(i).add(new RoomImpl(i * this.y + j));
      }
    }

    //Add connections to grid
    addConnections(edgeList);

    //Set values of start and end
    int[] values = setStartEnd();
    this.start = values[0];
    this.end = values[1];

    //Add treasure to rooms given percentage of treasure to be added
    addTreasure();

    //Add weapons to rooms with same frequency as treasure
    addWeapons();

    //Add monster to caves
    addMonsters();

    //Add player
    this.player = new PlayerImpl(playerId);
    this.playerLocation = this.start;

    //Set visited nodes to false except start node
    this.visited = new ArrayList<Boolean>();
    for (int i = 0; i < this.x * this.y + 1; i++) {
      if (i == start) {
        visited.add(true);
      } else {
        visited.add(false);
      }
    }

  }

  /**
   * Helper method to create an adjacency list of the nodes and pass it to Kruskal for creating the
   * spanning tree. The Kruskal's then returns the final list of edges present in the graph. It
   * consists of a List or List of integer pairs that represent the start and end node of the edge.
   *
   * @return List of edges present in the final graph
   */
  private List<List<Integer>> generateDungeon() {
    int inf = Integer.MAX_VALUE;
    int v = this.x * this.y;
    int[][] adjmatrix = new int[v][v];

    //Initialize all connections to inf
    for (int i = 0; i < v; i++) {
      for (int j = 0; j < v; j++) {
        adjmatrix[i][j] = inf;
      }
    }

    if (!this.wrapping) {
      //Add edges for adjacent nodes non-wrapping
      for (int i = 0; i < v; i++) {
        if ((i + 1) % y != 0) {
          adjmatrix[i][i + 1] = 1;
        }
        if (i % y != 0) {
          adjmatrix[i][i - 1] = 1;
        }
        if (i - y >= 0) {
          adjmatrix[i][i - y] = 1;
        }
        if (i + y < v) {
          adjmatrix[i][i + y] = 1;
        }
      }
    } else {
      //Add edges for adjacent nodes wrapping
      for (int i = 0; i < v; i++) {
        if ((i + 1) % y != 0) {
          adjmatrix[i][(i + 1)] = 1;
        } else {
          adjmatrix[i][i / y] = 1;
        }
        if (i % y != 0) {
          adjmatrix[i][(i - 1)] = 1;
        } else {
          adjmatrix[i][(i - 1) + y] = 1;
        }
        if (i - y >= 0) {
          adjmatrix[i][i - y] = 1;
        } else {
          adjmatrix[i][i + (y * 2)] = 1;
        }
        if (i + y < v) {
          adjmatrix[i][i + y] = 1;
        } else {
          adjmatrix[i][(i + y) % x] = 1;
        }
      }
    }
    return GraphUtility.kruskalmst(adjmatrix, this.x, this.y, this.interconnectivity, rng);
  }

  /**
   * Helper method to add the edges received from Kruskal to the grid in the Dungeon class.
   *
   * @param edgeList List of edges to add in the graph
   */
  private void addConnections(List<List<Integer>> edgeList) {
    //Add connections to the rooms based on edgeList
    for (List<Integer> edge : edgeList) {
      //Add connections to room0 and reverse in room1
      Room room0 = this.grid.get(edge.get(0) / this.y).get(edge.get(0) % this.y);
      Room room1 = this.grid.get(edge.get(1) / this.y).get(edge.get(1) % this.y);
      RoomImpl room0impl = (RoomImpl) room0;
      RoomImpl room1impl = (RoomImpl) room1;
      if (edge.get(1) - edge.get(0) == 1 || (edge.get(0) > edge.get(1)
              && edge.get(0) - edge.get(1) == this.y - 1)) {
        room0impl.addConnection(Direction.EAST, room1);
        room1impl.addConnection(Direction.WEST, room0);
      } else if (edge.get(0) - edge.get(1) == 1 || (edge.get(0) < edge.get(1)
              && edge.get(1) - edge.get(0) == this.y - 1)) {
        room0impl.addConnection(Direction.WEST, room1);
        room1impl.addConnection(Direction.EAST, room0);
      } else if (edge.get(0) > edge.get(1) && (edge.get(0) - edge.get(1) == this.y
              || edge.get(1) - edge.get(0) == this.y * (this.x - 1))) {
        room0impl.addConnection(Direction.NORTH, room1);
        room1impl.addConnection(Direction.SOUTH, room0);
      } else if (edge.get(1) > edge.get(0) && (edge.get(1) - edge.get(0) == this.y
              || edge.get(0) - edge.get(1) == this.y * (this.x - 1))) {
        room0impl.addConnection(Direction.SOUTH, room1);
        room1impl.addConnection(Direction.NORTH, room0);
      }
    }
  }

  /**
   * Randomly assign start and end nodes to the dungeon provided they are at least 5 units away
   * and they are in a cave.
   *
   * @return an integer array with start and end values
   */
  private int[] setStartEnd() {
    //Set values of start and end randomly till its length is at least 5
    int a = this.rng.getRandomNumber(0, this.x * this.y - 1);
    int b = this.rng.getRandomNumber(0, this.x * this.y - 1);
    while (GraphUtility.getPathLength(a, b, grid) < 5
            || !grid.get(a / y).get(a % y).isCave()
            || !grid.get(b / y).get(b % y).isCave()) {
      a = this.rng.getRandomNumber(0, this.x * this.y - 1);
      b = this.rng.getRandomNumber(0, this.x * this.y - 1);
    }
    return new int[]{a, b};
  }

  /**
   * Add treasure to some random caves based on the treasure amount specified by the user.
   */
  private void addTreasure() {
    int cavesCount = 0;
    for (int i = 0; i < this.x; i++) {
      for (int j = 0; j < this.y; j++) {
        boolean isCave = this.grid.get(i).get(j).isCave();
        if (isCave) {
          cavesCount += 1;
        }
      }
    }
    int treasureCount = (int) Math.ceil(cavesCount * (treasurePercent / 100.0));
    List<Integer> visited = new ArrayList<Integer>();
    while (treasureCount > 0) {
      int i = rng.getRandomNumber(0, x - 1);
      int j = rng.getRandomNumber(0, y - 1);
      Room r = this.grid.get(i).get(j);
      if (r.isCave()) {
        int random = rng.getRandomNumber(0, Treasure.values().length - 1);
        Treasure t = Treasure.values()[random];
        if (r instanceof RoomImpl) {
          RoomImpl rimpl = (RoomImpl) r;
          rimpl.addTreasure(t);
          if (!visited.contains(i * y + j)) {
            visited.add(i * y + j);
            treasureCount--;
          }
        } else {
          throw new IllegalArgumentException("Room must be instance of RoomImpl.");
        }
      }
    }

  }


  /**
   * Add weapons to some random locations based on the treasure amount specified by the user.
   */
  private void addWeapons() {

    int weaponCount = (int) Math.ceil((this.x * this.y) * (treasurePercent / 100.0));

    List<Integer> visited = new ArrayList<Integer>();
    while (weaponCount > 0) {
      //Get random location
      int i = rng.getRandomNumber(0, x - 1);
      int j = rng.getRandomNumber(0, y - 1);
      Room r = this.grid.get(i).get(j);

      Weapon w = Weapon.CROOKEDARROW;
      //Add weapon to room using package private method
      if (r instanceof RoomImpl) {
        RoomImpl rimpl = (RoomImpl) r;
        rimpl.addWeapon(w);
        if (!visited.contains(i * y + j)) {
          visited.add(i * y + j);
          weaponCount--;
        }
      } else {
        throw new IllegalArgumentException("Room must be instance of RoomImpl.");
      }
    }

  }

  /**
   * Add monster to some random caves based on the amount specified by the user.
   */
  private void addMonsters() {


    //Get count of caves
    int cavesCount = 0;
    for (int i = 0; i < this.x; i++) {
      for (int j = 0; j < this.y; j++) {
        boolean isCave = this.grid.get(i).get(j).isCave();
        if (isCave) {
          cavesCount += 1;
        }
      }
    }
    //Check if caves less than monsters.
    if (cavesCount < monsterCount) {
      throw new IllegalArgumentException("Monster count is more than caves count.");
    }

    //Add monster to end cave
    int tempCount = monsterCount;
    List<Integer> visited = new ArrayList<Integer>();

    Room r = this.grid.get(this.end / y).get(this.end % y);
    if (r instanceof RoomImpl) {
      RoomImpl rimpl = (RoomImpl) r;
      rimpl.addMonster(new Otyugh());
      visited.add(end);
      tempCount--;
    }

    //Add remaining Otyughs to random caves
    while (tempCount > 0) {
      int i = rng.getRandomNumber(0, x - 1);
      int j = rng.getRandomNumber(0, y - 1);
      r = this.grid.get(i).get(j);
      if (r.isCave()) {
        int random = rng.getRandomNumber(0, Treasure.values().length - 1);
        if (r instanceof RoomImpl) {
          if (!visited.contains(i * y + j) && (i * y + j) != start) {
            RoomImpl rimpl = (RoomImpl) r;
            rimpl.addMonster(new Otyugh());
            visited.add(i * y + j);
            tempCount--;
          }
        } else {
          throw new IllegalArgumentException("Room must be instance of RoomImpl.");
        }
      }
    }

    //Add Thiefs to random caves
    tempCount = monsterCount / 2;
    visited = new ArrayList<Integer>();
    //Add Thiefs to random caves
    while (tempCount > 0) {
      int i = rng.getRandomNumber(0, x - 1);
      int j = rng.getRandomNumber(0, y - 1);
      r = this.grid.get(i).get(j);

      int random = rng.getRandomNumber(0, Treasure.values().length - 1);
      if (r instanceof RoomImpl) {
        if (!visited.contains(i * y + j) && (i * y + j) != start && (i * y + j) != end) {
          RoomImpl rimpl = (RoomImpl) r;
          if (rimpl.getMonster() == null) {
            rimpl.addMonster(new Thief());
            visited.add(i * y + j);
            tempCount--;
          }
        }
      } else {
        throw new IllegalArgumentException("Room must be instance of RoomImpl.");
      }

    }

  }

  @Override
  public int[] getStart() {
    return new int[]{this.start / y, this.start % y};
  }

  @Override
  public int[] getEnd() {
    return new int[]{this.end / y, this.end % y};
  }

  @Override
  public int[] getPlayerLocation() {
    return new int[]{this.playerLocation / y, this.playerLocation % y};
  }

  @Override
  public void movePlayer(Direction d) {
    if (d == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }
    Map<Direction, Room> connections = grid.get(playerLocation / y).get(playerLocation % y)
            .getConnections();
    if (!connections.containsKey(d)) {
      throw new IllegalArgumentException("Room does not have exit in the given direction.");
    } else {
      playerLocation = connections.get(d).getNodeNumber();
      visited.set(connections.get(d).getNodeNumber(), true);

      if (!(player instanceof PlayerImpl)) {
        throw new IllegalArgumentException("Player must of type PlayerImpl.");
      }

      if (connections.get(d).getMonster() != null
              && connections.get(d).getMonster().getState() != MonsterState.DEAD) {
        connections.get(d).getMonster().effectPlayer((PlayerImpl) player, rng);
      }


    }

  }

  @Override
  public Monster getMonsterInLocation() {

    return grid.get(playerLocation / y).get(playerLocation % y).getMonster();
  }

  @Override
  public List getSettings() {
    List settings = new ArrayList();
    settings.add(x);
    settings.add(y);
    settings.add(interconnectivity);
    settings.add(wrapping);
    settings.add(treasurePercent);
    settings.add(monsterCount);
    return settings;
  }

  @Override
  public List<Boolean> getVisited() {
    return new ArrayList<>(this.visited);
  }

  @Override
  public void pickupTreasure() {
    List<Treasure> treasures = this.grid.get(playerLocation / y).get(playerLocation % y)
            .getTreasure();
    if (player instanceof PlayerImpl
            && this.grid.get(playerLocation / y).get(playerLocation % y) instanceof RoomImpl) {
      ((PlayerImpl) player).addTreasure(treasures);
      ((RoomImpl) this.grid.get(playerLocation / y).get(playerLocation % y)).removeTreasure();
    } else {
      throw new IllegalArgumentException("Player must be of type PlayerImpl.");
    }
  }

  @Override
  public List<List> describePlayerLocation() {
    Room r = this.grid.get(playerLocation / y).get(playerLocation % y);
    List locationDes = new ArrayList();
    locationDes.add(r.getConnections().keySet());
    locationDes.add(r.getTreasure());
    return locationDes;
  }

  @Override
  public List describePlayer() {
    return this.player.describePlayer();
  }

  @Override
  public List<Weapon> describeWeaponsAtLocation() {
    Room r = this.grid.get(playerLocation / y).get(playerLocation % y);
    return new ArrayList<Weapon>(r.getWeapons());
  }

  @Override
  public List<Treasure> describeTreasureAtLocation() {
    Room r = this.grid.get(playerLocation / y).get(playerLocation % y);
    return new ArrayList<Treasure>(r.getTreasure());
  }

  @Override
  public List<Direction> describeConnectionsAtLocation() {
    Room r = this.grid.get(playerLocation / y).get(playerLocation % y);

    return new ArrayList<>(r.getConnections().keySet());
  }

  @Override
  public Smell describeSmellAtLocation() {
    int monstersAt1Unit = GraphUtility.getMonstersInArea(playerLocation, 1, grid);
    int monstersAt2Units = GraphUtility.getMonstersInArea(playerLocation, 2, grid);

    if (monstersAt1Unit >= 1 || monstersAt2Units > 1) {
      return Smell.STRONG;
    } else if (monstersAt2Units == 1) {
      return Smell.WEAK;
    } else {
      return Smell.NOSMELL;
    }

  }

  @Override
  public boolean playerInCave() {
    return grid.get(playerLocation / y).get(playerLocation % y).isCave();
  }

  @Override
  public boolean playerIsAlive() {
    return player.isAlive();
  }


  /**
   * Helper method to shoot the weapon. Uses recursion with distance to shoot the arrow.
   *
   * @param currentRoom current room of arrow
   * @param d           direction arrow is going
   * @param distance    distance left for arrow to travel
   * @return true if arrow hits monster false if it misses
   */
  private boolean shootHelper(Room currentRoom, Direction d, int distance) {

    Monster m = currentRoom.getMonster();
    if (distance == 0 && m != null && m.getState() != MonsterState.DEAD) {
      //If monster exists in room and distance is reached
      if (m instanceof Otyugh) {
        Otyugh o = (Otyugh) m;
        o.strikeOtyugh();
        return true;
      } else {
        throw new IllegalArgumentException("Monster has to be of type Otyugh.");
      }

    } else if (distance == 0 && currentRoom.isCave()) {
      //If distance is reached but no monster in this room.
      return false;
    } else {
      //Arrow still need to travel further
      if (currentRoom.isCave()) {

        if (currentRoom.getConnections().containsKey(d)) {
          // If it is a cave and has exit in given direction reduce distance and continue
          return shootHelper(currentRoom.getConnections().get(d), d, distance - 1);
        } else {
          // If it is a cave and does not have exit in given direction arrow stops
          return false;
        }
      } else {
        //If it is a tunnel arrow will travel in the exit direction

        List<Direction> newDirections = new ArrayList<Direction>(
                currentRoom.getConnections().keySet());

        //Remove exit that arrow came from
        if (d == Direction.NORTH) {
          newDirections.remove(Direction.SOUTH);
        } else if (d == Direction.SOUTH) {
          newDirections.remove(Direction.NORTH);
        } else if (d == Direction.EAST) {
          newDirections.remove(Direction.WEST);
        } else if (d == Direction.WEST) {
          newDirections.remove(Direction.EAST);
        }

        Direction newDirection = newDirections.get(0);
        Room nextRoom = currentRoom.getConnections().get(newDirection);
        return shootHelper(nextRoom, newDirection, distance);
      }
    }

  }

  @Override
  public boolean shootWeapon(Direction d, int distance) {

    if (distance <= 0) {
      throw new IllegalArgumentException("Distance must be a positive non-zero integer.");
    }

    if (player.getWeapons().size() == 0) {
      throw new IllegalStateException("Player does not have any weapons left to shoot.");
    }
    if (player instanceof PlayerImpl) {
      ((PlayerImpl) player).removeWeapon(Weapon.CROOKEDARROW);
    }

    Room currentRoom = this.grid.get(playerLocation / y).get(playerLocation % y);
    if (currentRoom.getConnections().containsKey(d)) {
      // If it is a cave and has exit in given direction reduce distance and continue
      return shootHelper(currentRoom.getConnections().get(d), d, distance - 1);
    } else {
      return false;
    }


  }

  @Override
  public void pickupWeapons() {
    List<Weapon> weapons = this.grid.get(playerLocation / y).get(playerLocation % y)
            .getWeapons();
    if (player instanceof PlayerImpl
            && this.grid.get(playerLocation / y).get(playerLocation % y) instanceof RoomImpl) {
      ((PlayerImpl) player).addWeapons(weapons);
      ((RoomImpl) this.grid.get(playerLocation / y).get(playerLocation % y)).removeWeapons();
    } else {
      throw new IllegalArgumentException("Player must be of type PlayerImpl and room of "
              + "type RoomImpl.");
    }
  }

  @Override
  public int getMaxRows() {
    return x;
  }

  @Override
  public int getMaxCols() {
    return y;
  }

  @Override
  public String toString() {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < x; i++) {
      res.append("\n");
      for (int j = 0; j < y; j++) {
        res.append(i).append(",").append(j).append(this.grid.get(i).get(j).getTreasure())
                .append(" ").append(this.grid.get(i).get(j).getWeapons()).append(" ")
                .append(this.grid.get(i).get(j).getMonster()).append(" ");
      }
    }
    res.append("\n");
    return res.toString();
  }

}
